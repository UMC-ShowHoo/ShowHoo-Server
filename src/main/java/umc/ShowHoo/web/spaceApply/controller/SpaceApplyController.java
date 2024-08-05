package umc.ShowHoo.web.spaceApply.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.ShowHoo.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.spaceApply.dto.SpaceApplyRequestDTO;
import umc.ShowHoo.web.spaceApply.dto.SpaceApplyResponseDTO;
import umc.ShowHoo.web.spaceApply.entity.SpaceApply;
import umc.ShowHoo.web.spaceApply.service.SpaceApplyService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SpaceApplyController {
    private final SpaceApplyService spaceApplyService;

    @Operation(summary = "대관 신청 API", description = "공연자 유저가 대관 신청을 클릭했을 때 대관 신청 내역을 저장하는 API입니다. 이때 rentalFee는 날짜에 따른 가격, rentalSum은 날짜가격 + 추가서비스 가격입니다. selectedAdditionalServices는 대관신청하면서 선택한 추가 서비스의 id값 넘겨주시면 돼요")
    @Parameter(
            in = ParameterIn.HEADER,
            name = "Authorization", required = true,
            schema = @Schema(type = "string"),
            description = "Bearer [Access 토큰]"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    @PostMapping("spaces/{spaceUserId}/spaceApply/{performerId}")
    public ApiResponse<Void> createSpaceApply(
            @PathVariable Long spaceUserId,
            @PathVariable Long performerId,
            @RequestBody SpaceApplyRequestDTO.RegisterDTO registerDTO) {
        SpaceApply spaceApply = spaceApplyService.createSpaceApply(spaceUserId, performerId, registerDTO);
        return ApiResponse.onSuccess(null);
    }

    @Operation(summary = "대관 내역 API", description = "공연자 유저의 대관 내역을 확인할 수 있는 API입니다. status가 0이면 승인예정, 1이면 승인완료, -1일 때 승인거절입니다. 날짜를 확인한 후 과거 날짜이면 지난 공연이라고 표시하면 됩니다.")
    @Parameter(
            in = ParameterIn.HEADER,
            name = "Authorization", required = true,
            schema = @Schema(type = "string"),
            description = "Bearer [Access 토큰]"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    @GetMapping("spaces/spaceApply/{performerId}")
    public ApiResponse<List<SpaceApplyResponseDTO.SpaceApplyDetailDTO>> getSpaceAppliesByPerformerId(@PathVariable Long performerId) {
        List<SpaceApplyResponseDTO.SpaceApplyDetailDTO> spaceApplyDetailDTOS = spaceApplyService.getSpaceAppliesByPerformerId(performerId);
        return ApiResponse.onSuccess(spaceApplyDetailDTOS);
    }


    @Operation(summary = "대관 내역 취소 API", description = "공연자 대관 내역 확인하는 페이지에서 취소할 때 필요한 API입니다.")
    @Parameter(
            in = ParameterIn.HEADER,
            name = "Authorization", required = true,
            schema = @Schema(type = "string"),
            description = "Bearer [Access 토큰]"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    @DeleteMapping("spaceApply/delete/{spaceApplyId}")
    public ApiResponse<Void> deleteSpaceApply(@PathVariable Long spaceApplyId) {
        spaceApplyService.deleteSpaceApply(spaceApplyId);
        return ApiResponse.onSuccess(null);
    }



}
