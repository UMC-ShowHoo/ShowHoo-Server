package umc.ShowHoo.web.spaceUser.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import umc.ShowHoo.web.member.entity.Member;
import umc.ShowHoo.web.space.entity.Space;

import java.util.List;

@Entity
@Getter
@Setter
public class SpaceUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean status; //공연장 인증 상태, 인증이 되어야 대관글 올릴 수 있음

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "spaceUser", cascade = CascadeType.ALL)
    private List<Space> spaces;

}
