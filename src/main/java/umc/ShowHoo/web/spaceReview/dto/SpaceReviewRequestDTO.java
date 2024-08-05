package umc.ShowHoo.web.spaceReview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class SpaceReviewRequestDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewRegisterDTO{
        private double grade;
        private String content;
        private List<String> imageUrls;
    }

}
