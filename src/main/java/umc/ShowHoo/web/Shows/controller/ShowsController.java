package umc.ShowHoo.web.Shows.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.ShowHoo.apiPayload.ApiResponse;
import umc.ShowHoo.web.Shows.converter.ShowsConverter;
import umc.ShowHoo.web.Shows.dto.ShowsRequestDTO;
import umc.ShowHoo.web.Shows.dto.ShowsResponseDTO;
import umc.ShowHoo.web.Shows.entity.Shows;
import umc.ShowHoo.web.Shows.repository.ShowsRepository;
import umc.ShowHoo.web.Shows.service.ShowsService;

@RestController
@RequiredArgsConstructor
public class ShowsController {
    private final ShowsRepository showsRepository;
    private final ShowsService showsService;

    @PostMapping("/performer/{showId}/register")
    @Operation(summary = "공연자 공연 준비-요청사항, 공연 포스터/티켓 발행 api", description = "공연 포스터 및 정보 등록 시에 필요한 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공")
    })
    public ApiResponse<ShowsResponseDTO.ShowinfoDTO> createShow(@RequestBody ShowsRequestDTO showsRequestDTO){
        Shows shows= showsService.createShows(showsRequestDTO);

        return ApiResponse.onSuccess(ShowsConverter.toShowsDTO(shows));
    }

    @GetMapping("/space/{showId}/show-prepare")
    @Operation(summary = "공연장 공연 준비- 요청사항",description = "공연장이 공연자의 요청사항을 확인할 때 필요한 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공")
    })
    public ApiResponse<ShowsResponseDTO.ShowRequirementDTO> getShowRequirement(@PathVariable Long showId){
        ShowsResponseDTO.ShowRequirementDTO showRequirement = showsService.getShowRequirement(showId);
        return ApiResponse.onSuccess(showRequirement);
    }

}
