package umc.ShowHoo.web.login.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
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


    @Value("http://ec2-3-34-248-63.ap-northeast-2.compute.amazonaws.com:8081/login/oauth2/code/kakao")
    private String redirectUri;

    @Value("${kakao.client.id}")
    private String kakaoClientId;

    @GetMapping("/login/oauth2/code/kakao")
    public LoginResponseDTO kakao(@RequestParam("code") String code, HttpSession session) {
        LoginResponseDTO member = kakaoService.kakaoLogin(code, redirectUri);
        if (member != null){
            session.setAttribute("loginMember", member);
            session.setMaxInactiveInterval(60 * 30);
            session.setAttribute("kakaoToken", member.getAccessToken());
        }
        return member;
    }

    @GetMapping("/kakao")
    public RedirectView kakaoLogin() {
        String kakaoLoginUrl = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + kakaoClientId + "&redirect_uri=" + redirectUri;
        return new RedirectView(kakaoLoginUrl);
    }

    //카카오 일반 로그아웃(by accessToken)
    @GetMapping("/kakao/logout")
    public String kakaoLogout(HttpSession session) {
        String accessToken = (String) session.getAttribute("kakaoToken");
        System.out.println(accessToken);

        if(accessToken != null && !"".equals(accessToken)){
            try {
                kakaoService.kakaoDisconnect(accessToken);
            } catch (JsonProcessingException e){
                throw new RuntimeException(e);
            }
            session.removeAttribute("kakaoToken");
            session.removeAttribute("loginMember");
        }else {
            System.out.println("accessToken is null");
        }

        return "redirect:/";
    }
}
