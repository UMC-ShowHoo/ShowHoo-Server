package umc.ShowHoo.web.showsPrefer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.ShowHoo.apiPayload.ApiResponse;
import umc.ShowHoo.web.showsPrefer.dto.ShowsPreferRequestDTO;
import umc.ShowHoo.web.showsPrefer.dto.ShowsPreferResponseDTO;

@RestController
@RequestMapping("/shows-prefer")
@RequiredArgsConstructor
public class ShowsPreferController {

    @PostMapping
    @Operation(summary = "공연 찜 등록 API", description = "관람자가 공연에 대해 찜 등록을 할 때 필요한 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUDIENCE001", description = "Audience not found", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<ShowsPreferResponseDTO.createDTO> createShowsPrefer(@RequestBody ShowsPreferRequestDTO.createDTO request){
        return null;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "공연 찜 등록 해제 API", description = "관람자가 공연에 대해 찜 등록을 해제할 때 필요한 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUDIENCE001", description = "Audience not found", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<ShowsPreferResponseDTO.deleteDTO> deleteShowsPrefer(@PathVariable(name = "id") Long id){
        return null;
    }
}
