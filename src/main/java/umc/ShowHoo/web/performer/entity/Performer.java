package umc.ShowHoo.web.performer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import umc.ShowHoo.web.member.entity.Member;
import umc.ShowHoo.web.space.entity.Space;

@Entity
@Getter
@Setter
public class Performer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
