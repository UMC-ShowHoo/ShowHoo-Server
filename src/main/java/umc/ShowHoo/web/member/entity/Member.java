package umc.ShowHoo.web.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import umc.ShowHoo.web.audience.entity.Audience;
import umc.ShowHoo.web.notification.entity.Notification;
import umc.ShowHoo.web.performer.entity.Performer;

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

    @Column(unique = true)
    private Long uid;

    private String name;

    private URL profileimage;

    private String email;

    @Column(unique = true)
    private String accessToken;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private SpaceUser spaceUser;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Audience audience;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Performer performer;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Notification> notifications;
}
