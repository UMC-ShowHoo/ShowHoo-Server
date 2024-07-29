package umc.ShowHoo.web.login.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.ShowHoo.jwt.AuthTokens;

@Getter
@NoArgsConstructor
public class LoginResponseDTO {
    private Long id;
    private String name;
    private AuthTokens token;
    private String accessToken;

    public LoginResponseDTO(Long id, String name, AuthTokens token, String accessToken) {
        this.id = id;
        this.name = name;
        this.token = token;
        this.accessToken = accessToken;
    }

}
