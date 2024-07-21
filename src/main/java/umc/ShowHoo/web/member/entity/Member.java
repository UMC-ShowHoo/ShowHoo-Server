package umc.ShowHoo.web.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.spacePhoto.entity.SpacePhoto;
import umc.ShowHoo.web.spaceUser.entity.SpaceUser;

import java.net.URL;
import java.util.List;

@Entity
@Setter
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private URL profileimage;

    private String email;

    @Column(unique = true)
    private String accessToken;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private SpaceUser spaceUser;


}
