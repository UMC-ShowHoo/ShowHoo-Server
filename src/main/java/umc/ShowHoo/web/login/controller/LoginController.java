package umc.ShowHoo.web.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
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


    @Value("http://localhost:8080/login/oauth2/code/kakao")
    private String redirectUri;

    @Value("${kakao.client.id}")
    private String kakaoClientId;

    @GetMapping("/login/oauth2/code/kakao")
    public LoginResponseDTO kakao(@RequestParam("code") String code) {
        return kakaoService.kakaoLogin(code, redirectUri);
    }

    @GetMapping("/kakao")
    public RedirectView kakaoLogin() {
        String kakaoLoginUrl = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + kakaoClientId + "&redirect_uri=" + redirectUri;
        return new RedirectView(kakaoLoginUrl);
    }
}
