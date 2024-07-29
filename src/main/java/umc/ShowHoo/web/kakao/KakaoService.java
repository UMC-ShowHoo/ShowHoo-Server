package umc.ShowHoo.web.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import umc.ShowHoo.jwt.AuthTokens;
import umc.ShowHoo.jwt.AuthTokensGenerator;
import umc.ShowHoo.web.login.dto.LoginResponseDTO;
import umc.ShowHoo.web.member.entity.Member;
import umc.ShowHoo.web.member.repository.MemberRepository;
import umc.ShowHoo.web.spaceUser.entity.SpaceUser;
import umc.ShowHoo.web.spaceUser.repository.SpaceUserRepository;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class KakaoService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final SpaceUserRepository spaceUserRepository;

    @Value("${kakao.client.id}")
    private String clientId;

    @Value("${kakao.client.secret}")
    private String clientSecret;

    public LoginResponseDTO kakaoLogin(String code, String redirectUri) {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getAccessToken(code, redirectUri);

        // 2. 토큰으로 카카오 API 호출
        HashMap<String, Object> userInfo = getKakaoUserInfo(accessToken);

        // 3. 카카오ID로 회원가입 & 로그인 처리
        LoginResponseDTO kakaoUserResponse = kakaoUserLogin(userInfo, accessToken);

        return kakaoUserResponse;
    }

    // 1. "인가 코드"로 "액세스 토큰" 요청
    private String getAccessToken(String code, String redirectUri) {
        logger.info("Requesting access token with code: {} and redirectUri: {}", code, redirectUri);

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);
        body.add("client_secret", clientSecret);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response;
        try {
            response = rt.exchange(
                    "https://kauth.kakao.com/oauth/token",
                    HttpMethod.POST,
                    kakaoTokenRequest,
                    String.class
            );
            logger.info("Received response: {}", response.getBody());
        } catch (HttpClientErrorException e) {
            logger.error("HTTP error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw e;
        } catch (Exception e) {
            logger.error("Error occurred while requesting access token: " + e.getMessage());
            throw new RuntimeException(e);
        }

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(responseBody);
        } catch (JsonProcessingException e) {
            logger.error("Error parsing JSON response: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return jsonNode.get("access_token").asText();
    }

    // 2. 토큰으로 카카오 API 호출
    private HashMap<String, Object> getKakaoUserInfo(String accessToken) {
        HashMap<String, Object> userInfo = new HashMap<>();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(responseBody);
        } catch (JsonProcessingException e) {
            logger.error("Error parsing JSON response: " + e.getMessage());
            throw new RuntimeException(e);
        }

        Long id = jsonNode.get("id").asLong();
//        String email = jsonNode.get("kakao_account").get("email").asText();
        String nickname = jsonNode.get("properties").get("nickname").asText();
        String profileImageUrl = jsonNode.get("kakao_account")
                .get("profile")
                .get("thumbnail_image_url").asText();
        System.out.println(id);
//        System.out.println(email);
        System.out.println(nickname);
        System.out.println(profileImageUrl);

        userInfo.put("id", id);
//        userInfo.put("email", email);
        userInfo.put("nickname", nickname);
        userInfo.put("profileImageUrl", profileImageUrl);

        return userInfo;
    }

    // 3. 카카오ID로 회원가입 & 로그인 처리
    @Transactional
    public LoginResponseDTO kakaoUserLogin(HashMap<String, Object> userInfo, String accessToken) {
        Long uid = Long.valueOf(userInfo.get("id").toString());
        String name = userInfo.get("nickname").toString();
//        String email = userInfo.get("email").toString();
        URL profileImageUrl;

        try {
            profileImageUrl = new URL(userInfo.get("profileImageUrl").toString());
        } catch (MalformedURLException e) {
            logger.error("Invalid URL format for profile image: " + e.getMessage());
            throw new RuntimeException(e);
        }

        Member kakaoUser = memberRepository.findById(uid).orElse(null);

        if (kakaoUser == null) {
            kakaoUser = new Member();
            kakaoUser.setId(uid);
            kakaoUser.setName(name);
//            kakaoUser.setEmail(email);
            kakaoUser.setProfileimage(profileImageUrl);
            kakaoUser.setAccessToken(accessToken);

            Member savedMember = memberRepository.save(kakaoUser);

            // member 엔티티 만드는 동시에 spaceUser 엔티티도 생성
            SpaceUser spaceUser = new SpaceUser();
            spaceUser.setStatus(false); // 초기 상태 설정
            spaceUser.setMember(savedMember);
            kakaoUser.setSpaceUser(spaceUser);

            spaceUserRepository.save(spaceUser);
        }else {
            // 사용자 정보가 이미 존재하면 액세스 토큰 업데이트
            kakaoUser.setAccessToken(accessToken);
            memberRepository.save(kakaoUser);
        }

        AuthTokens token = authTokensGenerator.generate(uid.toString());
        return new LoginResponseDTO(uid, name, token, accessToken);
    }

    //4. 로그아웃
    public void kakaoDisconnect(String accessToken) throws JsonProcessingException {
        // HTTP 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        // HTTP 요청 보내기
        HttpEntity<String> kakaoLogoutRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();

        try {
            ResponseEntity<String> response = rt.exchange(
                    "https://kapi.kakao.com/v1/user/logout",
                    HttpMethod.POST,
                    kakaoLogoutRequest,
                    String.class
            );

            // responseBody에 있는 정보를 꺼냄
            String res = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(res);

            Long id = jsonNode.get("id").asLong();
            System.out.println("반환된 id : " + id);

        } catch (HttpClientErrorException e) {
            // 401 Unauthorized 에러 처리
            if (e.getStatusCode().value() == 401) {
                System.out.println("Unauthorized: Invalid or expired token.");
                System.out.println("Response body: " + e.getResponseBodyAsString());
            } else {
                System.out.println("Error: " + e.getStatusCode().value() + " " + e.getStatusText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
