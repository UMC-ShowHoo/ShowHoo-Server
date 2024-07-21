package umc.ShowHoo.web.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.spacePhoto.entity.SpacePhoto;

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

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Space> spaces;


}
