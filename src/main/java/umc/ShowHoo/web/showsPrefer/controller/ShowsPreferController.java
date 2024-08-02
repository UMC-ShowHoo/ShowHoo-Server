package umc.ShowHoo.web.showsPrefer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.ShowHoo.apiPayload.ApiResponse;
import umc.ShowHoo.web.showsPrefer.dto.ShowsPreferRequestDTO;
import umc.ShowHoo.web.showsPrefer.dto.ShowsPreferResponseDTO;
import umc.ShowHoo.web.showsPrefer.entity.ShowsPrefer;
import umc.ShowHoo.web.showsPrefer.service.ShowsPreferCommandService;

@RestController
@RequestMapping("/shows-prefer")
@RequiredArgsConstructor
public class ShowsPreferController {

    private final ShowsPreferCommandService showsPreferCommandService;

    @PostMapping
    @Operation(summary = "공연 찜 등록 API", description = "관람자가 공연에 대해 찜 등록을 할 때 필요한 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUDIENCE001", description = "Audience not found", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<ShowsPreferResponseDTO.createDTO> createShowsPrefer(@RequestBody ShowsPreferRequestDTO.createDTO request){
        return ApiResponse.onSuccess(showsPreferCommandService.createShowsPrefer(request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "공연 찜 등록 해제 API", description = "관람자가 공연에 대해 찜 등록을 해제할 때 필요한 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUDIENCE001", description = "Audience not found", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<ShowsPreferResponseDTO.deleteDTO> deleteShowsPrefer(@PathVariable(name = "id") Long id){
        String msg = showsPreferCommandService.deleteShowsPrefer(id);
        return ApiResponse.onSuccess(ShowsPreferResponseDTO.deleteDTO.builder()
                .alert(msg)
                .build());
    }

    @GetMapping("/{audienceId}")
    @Operation(summary = "관람자 별 공연 찜리스트 조회 API", description = "관람자가 찜리스트에 등록한 공연을 조회하는 API, 페이지 번호 필요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUDIENCE001", description = "Audience not found", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<ShowsPreferResponseDTO.getPreferListDTO> getPreferList(@PathVariable(name = "audienceId") Long id, @RequestParam(name = "page") Integer page){
        return null;
    }
}
