package umc.ShowHoo.web.spaceReview.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    @Operation(summary = "공연자 리뷰 작성 API", description = "공연자가 공연장에 대한 리뷰를 작성할 때 필요한 API입니다.")
    @Parameter(
            in = ParameterIn.HEADER,
            name = "Authorization", required = true,
            schema = @Schema(type = "string"),
            description = "Bearer [Access 토큰]"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
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
