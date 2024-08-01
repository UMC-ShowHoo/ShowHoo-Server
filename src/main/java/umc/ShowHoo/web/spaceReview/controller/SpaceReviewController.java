package umc.ShowHoo.web.spaceReview.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import umc.ShowHoo.apiPayload.ApiResponse;
import umc.ShowHoo.web.spaceReview.dto.SpaceReviewRequestDTO;
import umc.ShowHoo.web.spaceReview.service.SpaceReviewService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SpaceReviewController {
    private final SpaceReviewService spaceReviewService;


    @PostMapping(value = "/spaces/{spaceId}/review/{performerId}", consumes = "multipart/form-data")
    public ApiResponse<Void> createSpaceReview(
            @PathVariable Long spaceId,
            @PathVariable Long performerId,
            @RequestPart SpaceReviewRequestDTO.ReviewRegisterDTO reviewRegisterDTO,
            @RequestPart(required = false) List<MultipartFile> reviewImages){
        spaceReviewService.createSpaceReview(spaceId, performerId, reviewRegisterDTO, reviewImages);
        return ApiResponse.onSuccess(null);
    }

}
