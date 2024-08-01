package umc.ShowHoo.web.performer.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.ShowHoo.web.Shows.entity.Shows;
import umc.ShowHoo.web.member.entity.Member;
import umc.ShowHoo.web.performerProfile.entity.PerformerProfile;
import umc.ShowHoo.web.spaceApply.entity.SpaceApply;
import umc.ShowHoo.web.spacePrefer.entity.SpacePrefer;
import umc.ShowHoo.web.spaceReview.entity.SpaceReview;

import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Performer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "performer",cascade = CascadeType.ALL)
    private List<PerformerProfile> profiles;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "performer", cascade = CascadeType.ALL)
    private List<SpacePrefer> spacePrefers;

    @OneToMany(mappedBy = "performer", cascade = CascadeType.ALL)
    private List<Shows> showsList;

    @OneToMany(mappedBy = "performer", cascade = CascadeType.ALL)
    private List<SpaceApply> spaceApplies;

    @OneToMany(mappedBy = "performer", cascade = CascadeType.ALL)
    private List<SpaceReview> spaceReviews;
}
