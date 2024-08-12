package umc.ShowHoo.web.login.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import umc.ShowHoo.jwt.AuthTokens;
import umc.ShowHoo.web.login.dto.LoginResponseDTO;
import umc.ShowHoo.web.kakao.KakaoService;

@RestController
public class LoginController {
    private final KakaoService kakaoService;

    @Autowired
    public LoginController(KakaoService kakaoService) {
        this.kakaoService = kakaoService;
    };


    @GetMapping("/login")
    public String login() {
        return "Hello World";
    }

    @GetMapping("/logout")
    public String logout(){
        return "로그아웃";
    }

    @Value("${security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    @Value("http://localhost:5173")
    private String logout_redirectUri;

    @Value("${kakao.client.id}")
    private String kakaoClientId;

    //멤버 정보 가져오는 API 만들기
    @GetMapping("/login/oauth2/code/kakao")
    public LoginResponseDTO kakao(@RequestParam("code") String code, HttpSession session) {
        return kakaoService.kakaoLogin(code, redirectUri);
    }

    @GetMapping("/kakao")
    public String kakaoLogin() {
        return "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + kakaoClientId + "&redirect_uri=" + redirectUri + "&response_type=code";
    }

    @GetMapping("/kakao/logout/withAccount")
    public String kakaoLogoutWithAccount() {
        return "https://kauth.kakao.com/oauth/logout?client_id=" + kakaoClientId + "&logout_redirect_uri=" + logout_redirectUri;

    }

    //카카오 일반 로그아웃(by accessToken)
    @PostMapping("/kakao/logout")
    public String kakaoLogout(@RequestBody Long uid) {
        try {
            kakaoService.kakaoDisconnect(kakaoService.getAccessToken(uid));
        } catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
        return "redirect:/";
    }

    //카카오 연결 끊기(by accessToken)
    @PostMapping("/kakao/delete")
    public String kakaoUnlink(@RequestBody Long uid) {
        try {
            kakaoService.KaKaoUnlink(kakaoService.getAccessToken(uid));
        } catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
        return "redirect:/";
    }

}
