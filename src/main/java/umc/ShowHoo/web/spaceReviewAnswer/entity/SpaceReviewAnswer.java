package umc.ShowHoo.web.spaceReviewAnswer.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.ShowHoo.web.spaceReview.entity.SpaceReview;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SpaceReviewAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content; //리뷰 답글 내용

    @ManyToOne
    @JoinColumn(name = "space_review_id")
    private SpaceReview spaceReview;
}
