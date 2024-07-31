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

    @Operation(summary = "대관 신청 API", description = "공연자 유저가 대관 신청을 클릭했을 때 대관 신청 내역을 저장하는 API입니다.")
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

    @GetMapping("spaces/spaceApply/{performerId}")
    public ApiResponse<List<SpaceApplyResponseDTO.SpaceApplyDetailDTO>> getSpaceAppliesByPerformerId(@PathVariable Long performerId) {
        List<SpaceApplyResponseDTO.SpaceApplyDetailDTO> spaceApplyDetailDTOS = spaceApplyService.getSpaceAppliesByPerformerId(performerId);
        return ApiResponse.onSuccess(spaceApplyDetailDTOS);
    }



}
