package umc.ShowHoo.web.spaceReview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class SpaceReviewResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewPerformerDTO{
        private Long id;
        private double grade;
        private String content;
        private List<SpaceReviewAnswerDto> answers;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewSpaceDTO{
        private Long id;
        private double grade;
        private String content;
        private List<SpaceReviewAnswerDto> answers;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpaceReviewAnswerDto{
        private Long id;
        private String content;
    }
}
