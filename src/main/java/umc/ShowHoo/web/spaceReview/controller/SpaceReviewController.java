package umc.ShowHoo.web.spaceReview.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.ShowHoo.apiPayload.ApiResponse;
import umc.ShowHoo.web.spaceReview.dto.SpaceReviewRequestDTO;
import umc.ShowHoo.web.spaceReview.dto.SpaceReviewResponseDTO;
import umc.ShowHoo.web.spaceReview.service.SpaceReviewService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SpaceReviewController {
    private final SpaceReviewService spaceReviewService;

    @PostMapping(value = "/reviewImage/upload", consumes = "multipart/form-data")
    @Parameter(
            in = ParameterIn.HEADER,
            name = "Authorization", required = true,
            schema = @Schema(type = "string"),
            description = "Bearer [Access 토큰]"
    )
    @Operation(summary = "리뷰 이미지 업로드 API", description = "리뷰 이미지를 S3에 업로드하고 URL을 반환합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<List<String>>uploadReviewImages(@RequestPart List<MultipartFile> reviewImages){
        List<String> imageUrls = spaceReviewService.uploadReviewImages(reviewImages);
        return ApiResponse.onSuccess(imageUrls);
    }



    @Operation(summary = "공연자 리뷰 작성 API", description = "공연자가 공연장에 대한 리뷰를 작성하고 등록하기 버튼을 눌렀을 때 필요한 API입니다. 사용자 리뷰를 입력하고 그 값을 전달해주면 db에 저장하게 됩니다.")
    @Parameter(
            in = ParameterIn.HEADER,
            name = "Authorization", required = true,
            schema = @Schema(type = "string"),
            description = "Bearer [Access 토큰]"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    @PostMapping(value = "/spaces/{spaceId}/review/{performerId}")
    public ApiResponse<Void> createSpaceReview(
            @PathVariable Long spaceId,
            @PathVariable Long performerId,
            @RequestBody SpaceReviewRequestDTO.ReviewRegisterDTO reviewRegisterDTO){
        spaceReviewService.createSpaceReview(spaceId, performerId, reviewRegisterDTO);
        return ApiResponse.onSuccess(null);
    }

    @Operation(summary = "공연자 리뷰 삭제 API", description = "공연자가 공연장에 대한 리뷰를 삭제할 때 필요한 API입니다.")
    @Parameter(
            in = ParameterIn.HEADER,
            name = "Authorization", required = true,
            schema = @Schema(type = "string"),
            description = "Bearer [Access 토큰]"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    @DeleteMapping("/review/{reviewId}")
    public ApiResponse<Void> deleteSpaceReview(@PathVariable Long reviewId){
        spaceReviewService.deleteSpaceReview(reviewId);
        return ApiResponse.onSuccess(null);
    }


    @Operation(summary = "공연자 마이페이지 리뷰 조회 API", description = "공연자가 마이페이지에서 리뷰 조회에 필요한 API입니다.")
    @Parameter(
            in = ParameterIn.HEADER,
            name = "Authorization", required = true,
            schema = @Schema(type = "string"),
            description = "Bearer [Access 토큰]"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    @GetMapping("/review/performer/{performerId}")
    public ApiResponse<List<SpaceReviewResponseDTO.ReviewPerformerDTO>> getReviewsByPerformerId(@PathVariable Long performerId){
        List<SpaceReviewResponseDTO.ReviewPerformerDTO> reviews = spaceReviewService.getReviewsByPerformerId(performerId);
        return ApiResponse.onSuccess(reviews);
    }


    @Operation(summary = "공연장 세부정보 리뷰 API", description = "공연장 세부정보 리뷰 조회할 때 필요한 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    @GetMapping("/review/space/{spaceId}")
    public ApiResponse<List<SpaceReviewResponseDTO.ReviewSpaceDTO>> getReviewsBySpaceId(@PathVariable Long spaceId){
        List<SpaceReviewResponseDTO.ReviewSpaceDTO> reviews = spaceReviewService.getReviewsBySpaceId(spaceId);
        return ApiResponse.onSuccess(reviews);
    }

}
