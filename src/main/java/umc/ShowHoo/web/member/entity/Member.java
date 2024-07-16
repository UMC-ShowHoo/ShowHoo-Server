package umc.ShowHoo.web.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;

@Entity
@Setter
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

//    private String kakaoAccessToken;

    private URL profileimage;

    private String email;
}
