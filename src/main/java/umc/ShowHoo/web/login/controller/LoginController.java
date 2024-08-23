package umc.ShowHoo.web.login.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import umc.ShowHoo.apiPayload.ApiResponse;
import umc.ShowHoo.jwt.AuthTokens;
import umc.ShowHoo.web.login.dto.KakaoUnlinkDTO;
import umc.ShowHoo.web.login.dto.LoginResponseDTO;
import umc.ShowHoo.web.kakao.KakaoService;
import umc.ShowHoo.web.login.dto.MemberResponseDTO;
import umc.ShowHoo.web.member.Service.MemberQueryService;
import umc.ShowHoo.web.member.entity.Member;

@RestController
public class LoginController {
    private final KakaoService kakaoService;
    private final MemberQueryService memberQueryService;

    @Autowired
    public LoginController(KakaoService kakaoService, MemberQueryService memberQueryService) {
        this.kakaoService = kakaoService;
        this.memberQueryService = memberQueryService;
    };


    @GetMapping("/login")
    public String login() {
        return "Hello World";
    }

    @GetMapping("/logout")
    public String logout(){
        return "로그아웃";
    }

    @Value("https://66c82eac6cbdbb00089ce5c5--showhoo.netlify.app/login/oauth2/code/kakao")
    private String redirectUri;

    @Value("https://66c82eac6cbdbb00089ce5c5--showhoo.netlify.app")
    private String logout_redirectUri;

    @Value("${kakao.client.id}")
    private String kakaoClientId;

    @GetMapping("/member_info/{uid}")
    public ApiResponse<MemberResponseDTO> getMemberInfo(@PathVariable(name = "uid") Long uid){
        Member member = memberQueryService.getMemberByUid(uid);
        return ApiResponse.onSuccess(MemberResponseDTO.builder()
                .memberId(member.getId())
                .performerId(member.getPerformer().getId())
                .spaceUserId(member.getSpaceUser().getId())
                .audienceId(member.getAudience().getId())
                .name(member.getName())
                .profile_url(member.getProfileimage())
                .build());
    }

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
    public String kakaoUnlink(@RequestBody KakaoUnlinkDTO request) {
        try {
            kakaoService.KaKaoUnlink(kakaoService.getAccessToken(request.getUid()));
        } catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
        return "redirect:/";
    }

}
