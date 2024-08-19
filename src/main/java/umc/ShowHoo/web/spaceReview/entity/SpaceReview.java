package umc.ShowHoo.web.spaceReview.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.ShowHoo.common.BaseEntity;
import umc.ShowHoo.web.performer.entity.Performer;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.spaceReviewAnswer.entity.SpaceReviewAnswer;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SpaceReview extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double grade; // 리뷰 평점
    private String content; // 리뷰 내용
    private String memberUrl; // 리뷰 작성한 유저 프로필 이미지
    private String memberName; // 리뷰 작성한 유저 이름


    @ManyToOne
    @JoinColumn(name = "space_id")
    private Space space;

    @ManyToOne
    @JoinColumn(name = "performer_id")
    private Performer performer;

    @OneToMany(mappedBy = "spaceReview", cascade = CascadeType.ALL)
    private List<SpaceReviewAnswer> spaceReviewAnswers;

    @OneToMany(mappedBy = "spaceReview", cascade = CascadeType.ALL)
    private List<SpaceReviewImage> spaceReviewImages;
}
