package umc.ShowHoo.web.spaceReview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.time.LocalDateTime;
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
        private LocalDateTime updatedAt;      // 리뷰 수정 시간 추가
        private List<String> imageUrls;       // 이미지 URL 리스트 추가

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
        private List<String> imageUrls;  // 이미지 URL 리스트 추가
        private String memberName;       // 리뷰 작성자 이름 추가
        private URL memberUrl;           // 리뷰 작성자 프로필 이미지 URL 추가
        private LocalDateTime updatedAt; // 리뷰 수정 시간 추가
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpaceReviewAnswerDto{
        private Long id;
        private String content;
        private LocalDateTime updatedAt; // 답변 수정 시간 추가
    }
}
