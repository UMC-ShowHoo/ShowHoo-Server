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

    public LoginResponseDTO(Long id, String name, AuthTokens token) {
        this.id = id;
        this.name = name;
        this.token = token;
    }

}
