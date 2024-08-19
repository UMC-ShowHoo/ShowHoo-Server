package umc.ShowHoo.web.spaceApply.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.web.bind.annotation.*;
import umc.ShowHoo.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.spaceApply.dto.SpaceApplyRequestDTO;
import umc.ShowHoo.web.spaceApply.dto.SpaceApplyResponseDTO;
import umc.ShowHoo.web.spaceApply.entity.SpaceApply;
import umc.ShowHoo.web.spaceApply.service.SpaceApplyService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SpaceApplyController {
    private final SpaceApplyService spaceApplyService;

    @Operation(summary = "대관 신청 API", description = "공연자 유저가 대관 신청을 클릭했을 때 대관 신청 내역을 저장하는 API입니다. 이때 rentalFee는 날짜에 따른 가격, rentalSum은 날짜가격 + 추가서비스 가격입니다. selectedAdditionalServices는 대관신청하면서 선택한 추가 서비스의 id값 넘겨주시면 돼요")
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
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    @GetMapping("spaces/spaceApply/{performerId}")
    public ApiResponse<List<SpaceApplyResponseDTO.SpaceApplyDetailDTO>> getSpaceAppliesByPerformerId(@PathVariable Long performerId) {
        List<SpaceApplyResponseDTO.SpaceApplyDetailDTO> spaceApplyDetailDTOS = spaceApplyService.getSpaceAppliesByPerformerId(performerId);
        return ApiResponse.onSuccess(spaceApplyDetailDTOS);
    }


    @Operation(summary = "대관 내역 취소 및 거절 API", description = "공연자 대관 내역 확인하는 페이지에서 취소 및 거절할 때 필요한 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    @DeleteMapping("spaceApply/delete/{spaceApplyId}")
    public ApiResponse<Void> deleteSpaceApply(@PathVariable Long spaceApplyId) {
        spaceApplyService.deleteSpaceApply(spaceApplyId);
        return ApiResponse.onSuccess(null);
    }

    @Operation(summary = " 대관 수락 API", description = "공연장이 공연자를 수락할 때 필요한 API. status가 승인 예정은 0, 승인 완료는 1")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "ok, 성공"),
    })
    @PatchMapping("spaces/{spaceId}/spaceApply/{spaceApplyId}")
    public ApiResponse<Void> confirmSpaceApply(
            @PathVariable Long spaceId , @PathVariable Long spaceApplyId) {
        spaceApplyService.setSpaceApply(spaceId, spaceApplyId);
        return ApiResponse.onSuccess(null);
    }
    @Operation(summary = " 대관 가능 날짜 확인 API", description = "공연자가 공연장 대관 가능 날짜를 확인 하는 API, holiday와 status 체크")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "ok, 성공"),
    })
    @GetMapping("spaces/{spaceId}/spaceApply/info")
    public ApiResponse<List<Object>> getSpaceInfo(@PathVariable Long spaceId) {
        return ApiResponse.onSuccess(spaceApplyService.getSpaceApplyInfo(spaceId));

    }

    @Operation(summary = "대관 날짜로 공연자 확인 API", description = "공연장이 날짜를 눌렀을 때 대관 요청한 공연자가 나오는 API 입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "ok, 성공"),
    })
    @GetMapping("spaces/{spaceId}/spaceApply/info/{date}")
    public ApiResponse<SpaceApplyResponseDTO.SpaceApplyDetailDTO> getSpaceApplyByDate(
            @PathVariable Long spaceId, @PathVariable LocalDate date
            ) {
                SpaceApplyResponseDTO.SpaceApplyDetailDTO spaceApplyDTO = spaceApplyService.getSpaceApplyDetailsByDate(spaceId, date);
                return ApiResponse.onSuccess(spaceApplyDTO);

    }
}
