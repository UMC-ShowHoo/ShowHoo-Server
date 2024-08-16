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
@RequestMapping("/space-prefer")
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

    @DeleteMapping("/{id}")
    @Operation(summary = "공연장 찜 삭제 API", description = "공연장 찜을 삭제할 때 필요한 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<Void> deleteSpacePrefer(@PathVariable Long id) {
        spacePreferService.deleteSpacePrefer(id);
        return ApiResponse.onSuccess(null);
    }
}
