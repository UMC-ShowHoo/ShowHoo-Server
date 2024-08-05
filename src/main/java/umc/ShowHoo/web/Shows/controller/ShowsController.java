package umc.ShowHoo.web.Shows.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.ShowHoo.apiPayload.ApiResponse;
import umc.ShowHoo.web.Shows.converter.ShowsConverter;
import umc.ShowHoo.web.Shows.dto.ShowsRequestDTO;
import umc.ShowHoo.web.Shows.dto.ShowsResponseDTO;
import umc.ShowHoo.web.Shows.entity.Shows;
import umc.ShowHoo.web.Shows.service.ShowsService;
import umc.ShowHoo.web.showsDescription.converter.ShowsDscConverter;
import umc.ShowHoo.web.showsDescription.dto.ShowsDscRequestDTO;
import umc.ShowHoo.web.showsDescription.dto.ShowsDscResponseDTO;
import umc.ShowHoo.web.showsDescription.entity.ShowsDescription;

@RestController
@RequiredArgsConstructor
public class ShowsController {
    private static final Logger logger = LoggerFactory.getLogger(ShowsController.class);
    private final ShowsService showsService;

    @PostMapping(value="/{performerId}/{showId}/show-register",consumes = "multipart/form-data")
    @Operation(summary = "공연자 공연 준비-공연 포스터 및 공연 정보 등록 api", description = "공연 포스터 및 정보 등록 시에 필요한 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공")
    })
    public ApiResponse<ShowsResponseDTO.postShowDTO> createShow(
            @PathVariable Long showId,
            @PathVariable Long performerId,
            //@RequestBody ShowsRequestDTO showsRequestDTO,
            @RequestPart ShowsRequestDTO.ShowInfoDTO showsRequestDTO,
            @RequestPart(required = false) MultipartFile poster){

        Shows shows= showsService.createShows(showsRequestDTO,poster,performerId);
        shows.setId(showId);

        return ApiResponse.onSuccess(ShowsConverter.toPostShowDTO(shows));
    }

    @PostMapping(value="/{performerId}/{showId}/show-register/description",consumes = "multipart/form-data")
    @Operation(summary = "공연자 공연 준비- 공연 설명 등록 API", description = "공연을 등록할 때 공연 설명을 작성하는 API")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공")
    })
/*    @Parameters({
            @Parameter(name = "text",description = "등록하는 공연 설명의 텍스트"),
    })*/
    public ApiResponse<ShowsDscResponseDTO.PostDscDTO> createShowDsc(@PathVariable Long showId, @RequestPart ShowsDscRequestDTO.DescriptionDTO descriptionDTO,
                                                                     @RequestBody(required = false) MultipartFile img){
        ShowsDescription showsDescription=showsService.createShowDsc(descriptionDTO,img,showId);

        return ApiResponse.onSuccess(ShowsDscConverter.toPostDscDTO(showsDescription));
    }

    @PostMapping(value="/{performerId}/{showId}/ticket-register")
    @Operation(summary = "공연자 공연 준비-티켓 발행 등록 api", description = "공연 은행정보, 티켓 등록 시에 필요한 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공")
    })
    public ApiResponse<ShowsResponseDTO.postShowDTO> createShowTicket(
            @PathVariable Long showId,
            @RequestBody ShowsRequestDTO.ticketInfoDTO ticketInfoDTO){

        Shows shows=showsService.createShowsTicket(ticketInfoDTO,showId);

        return ApiResponse.onSuccess(ShowsConverter.toPostShowDTO(shows));
    }

    @PostMapping(value="/{performerId}/{showId}/requirement-register")
    @Operation(summary = "공연자 공연 준비-요청 사항 등록 api", description = "공연 요청사항 등록 시에 필요한 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공")
    })
    public ApiResponse<ShowsResponseDTO.postShowDTO> createShowReq(
            @PathVariable Long showId,
            @RequestBody ShowsRequestDTO.requirementDTO requirementDTO){

        Shows shows=showsService.createShowsReq(requirementDTO,showId);

        return ApiResponse.onSuccess(ShowsConverter.toPostShowDTO(shows));
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
