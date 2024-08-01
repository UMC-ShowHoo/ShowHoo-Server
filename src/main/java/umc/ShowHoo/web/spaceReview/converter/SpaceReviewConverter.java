package umc.ShowHoo.web.spaceReview.converter;

import org.springframework.stereotype.Component;
import umc.ShowHoo.web.performer.entity.Performer;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.spaceReview.dto.SpaceReviewRequestDTO;
import umc.ShowHoo.web.spaceReview.entity.SpaceReview;
import umc.ShowHoo.web.spaceReview.entity.SpaceReviewImage;

@Component
public class SpaceReviewConverter {

    public SpaceReview toCreateSpaceReview(SpaceReviewRequestDTO.ReviewRegisterDTO reviewRegisterDTO, Space space, Performer performer) {
        return SpaceReview.builder()
                .grade(reviewRegisterDTO.getGrade())
                .content(reviewRegisterDTO.getContent())
                .space(space)
                .performer(performer)
                .build();
    }


    public SpaceReviewImage toSpaceReviewImage(String imageUrl, SpaceReview spaceReview) {
        return SpaceReviewImage.builder()
                .spaceReview(spaceReview)
                .imageUrl(imageUrl)
                .build();
    }
}
