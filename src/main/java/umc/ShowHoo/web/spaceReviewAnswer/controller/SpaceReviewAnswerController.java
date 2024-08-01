package umc.ShowHoo.web.spaceReviewAnswer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.ShowHoo.apiPayload.ApiResponse;
import umc.ShowHoo.web.spaceReviewAnswer.dto.SpaceReviewAnswerRequestDTO;
import umc.ShowHoo.web.spaceReviewAnswer.service.SpaceReviewAnswerService;

@RestController
@RequiredArgsConstructor
public class SpaceReviewAnswerController {

    private final SpaceReviewAnswerService spaceReviewAnswerService;

    @Operation(summary = "공연자 리뷰에 대한 답글 작성 API", description = "공연자가 작성한 댓글에 대한 답글 작성을 할 때 필요한 API입니다.")
    @Parameter(
            in = ParameterIn.HEADER,
            name = "Authorization", required = true,
            schema = @Schema(type = "string"),
            description = "Bearer [Access 토큰]"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    @PostMapping("spaces/{spaceId}/review/{reviewId}/reviewAnswer")
    public ApiResponse<Void> createSpaceReviewAnswer(
            @PathVariable Long spaceId,
            @PathVariable Long reviewId,
            @RequestBody SpaceReviewAnswerRequestDTO.AnswerContentDTO answerContentDTO){
        spaceReviewAnswerService.createSpaceReviewAnswer(spaceId,reviewId, answerContentDTO);
        return ApiResponse.onSuccess(null);
    }

    @Operation(summary = "공연자 리뷰에 대한 답글 삭제 API", description = "공연자가 작성한 댓글에 대한 답글 삭제를 할 때 필요한 API입니다.")
    @Parameter(
            in = ParameterIn.HEADER,
            name = "Authorization", required = true,
            schema = @Schema(type = "string"),
            description = "Bearer [Access 토큰]"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    @DeleteMapping("/reviewAnswer/{reviewAnswerId}")
    public ApiResponse<Void> deleteSpaceReviewAnswer(@PathVariable Long reviewAnswerId){
        spaceReviewAnswerService.deleteSpaceReviewAnswer(reviewAnswerId);
        return ApiResponse.onSuccess(null);
    }
}
