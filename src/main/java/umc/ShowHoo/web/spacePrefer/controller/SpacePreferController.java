package umc.ShowHoo.web.spacePrefer.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import umc.ShowHoo.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import umc.ShowHoo.web.spacePrefer.dto.SpacePreferRequestDTO;
import umc.ShowHoo.web.spacePrefer.dto.SpacePreferResponseDTO;
import umc.ShowHoo.web.spacePrefer.service.SpacePreferService;

@RestController
@RequestMapping("/spaces/prefer")
@RequiredArgsConstructor
public class SpacePreferController {
    private final SpacePreferService spacePreferService;

    @PostMapping
    @Operation(summary = "공연장 찜 등록 API", description = "공연장 찜 등록할 때 필요한 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<SpacePreferResponseDTO.ResultDTO> createSpacePrefer(@RequestBody SpacePreferRequestDTO request){
        SpacePreferResponseDTO.ResultDTO spacePrefer = spacePreferService.saveSpacePrefer(request);
        return ApiResponse.onSuccess(spacePrefer);
    }

    @DeleteMapping("/{spacePreferId}")
    @Operation(summary = "공연장 찜 삭제 API", description = "공연장 찜을 삭제할 때 필요한 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<Void> deleteSpacePrefer(@PathVariable Long spacePreferId) {
        spacePreferService.deleteSpacePrefer(spacePreferId);
        return ApiResponse.onSuccess(null);
    }

    @GetMapping("/{spaceId}/{performerId}")
    @Operation(summary = "공연장 찜 유무 조회 API", description = "현재 공연자가 해당 공연장 찜을 했는지 true, false 값을 넘겨주는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<Boolean> checkSpacePreference(@PathVariable Long spaceId, @PathVariable Long performerId){
        Boolean check = spacePreferService.checkSpacePreference(spaceId, performerId);
        return ApiResponse.onSuccess(check);
    }
}
