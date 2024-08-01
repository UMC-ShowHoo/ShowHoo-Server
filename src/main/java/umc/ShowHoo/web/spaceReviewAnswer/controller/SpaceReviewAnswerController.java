package umc.ShowHoo.web.spaceReviewAnswer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import umc.ShowHoo.apiPayload.ApiResponse;
import umc.ShowHoo.web.spaceReviewAnswer.dto.SpaceReviewAnswerRequestDTO;
import umc.ShowHoo.web.spaceReviewAnswer.service.SpaceReviewAnswerService;

@RestController
@RequiredArgsConstructor
public class SpaceReviewAnswerController {

    private final SpaceReviewAnswerService spaceReviewAnswerService;

    @PostMapping("spaces/{spaceId}/review/{reviewId}/reviewAnswer")
    public ApiResponse<Void> createSpaceReviewAnswer(
            @PathVariable Long spaceId,
            @PathVariable Long reviewId,
            @RequestBody SpaceReviewAnswerRequestDTO.AnswerContentDTO answerContentDTO){
        spaceReviewAnswerService.createSpaceReviewAnswer(spaceId,reviewId, answerContentDTO);
        return ApiResponse.onSuccess(null);
    }
}
