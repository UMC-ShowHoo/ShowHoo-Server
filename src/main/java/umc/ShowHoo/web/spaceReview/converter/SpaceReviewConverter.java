package umc.ShowHoo.web.spaceReview.converter;

import org.springframework.stereotype.Component;
import umc.ShowHoo.web.performer.entity.Performer;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.spaceReview.dto.SpaceReviewRequestDTO;
import umc.ShowHoo.web.spaceReview.dto.SpaceReviewResponseDTO;
import umc.ShowHoo.web.spaceReview.entity.SpaceReview;
import umc.ShowHoo.web.spaceReview.entity.SpaceReviewImage;

import java.util.List;
import java.util.stream.Collectors;

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

    public SpaceReviewResponseDTO.ReviewPerformerDTO toGetPerformerReview(SpaceReview spaceReview) {
        List<SpaceReviewResponseDTO.SpaceReviewAnswerDto> answers = spaceReview.getSpaceReviewAnswers().stream()
                .map(answer -> new SpaceReviewResponseDTO.SpaceReviewAnswerDto(answer.getId(), answer.getContent()))
                .collect(Collectors.toList());

        return new SpaceReviewResponseDTO.ReviewPerformerDTO(
                spaceReview.getId(),
                spaceReview.getGrade(),
                spaceReview.getContent(),
                answers
        );
    }
}
