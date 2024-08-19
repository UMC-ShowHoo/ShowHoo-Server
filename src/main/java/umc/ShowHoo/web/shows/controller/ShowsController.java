package umc.ShowHoo.web.shows.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.ShowHoo.apiPayload.ApiResponse;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import umc.ShowHoo.web.shows.converter.ShowsConverter;
import umc.ShowHoo.web.shows.dto.ShowsRequestDTO;
import umc.ShowHoo.web.shows.dto.ShowsResponseDTO;
import umc.ShowHoo.web.shows.entity.Shows;
import umc.ShowHoo.web.shows.service.S3Service;
import umc.ShowHoo.web.shows.service.ShowsService;
import umc.ShowHoo.web.showsDescription.converter.ShowsDscConverter;
import umc.ShowHoo.web.showsDescription.dto.ShowsDscRequestDTO;
import umc.ShowHoo.web.showsDescription.dto.ShowsDscResponseDTO;
import umc.ShowHoo.web.showsDescription.entity.ShowsDescription;
import umc.ShowHoo.web.spaceApply.dto.SpaceApplyResponseDTO;
import umc.ShowHoo.web.spaceApply.entity.SpaceApply;
import umc.ShowHoo.web.spaceApply.exception.handler.SpaceApplyHandler;
import umc.ShowHoo.web.spaceApply.repository.SpaceApplyRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ShowsController {
    private static final Logger logger = LoggerFactory.getLogger(ShowsController.class);
    private final ShowsService showsService;
    private final S3Service s3Service;

    @GetMapping(value="/{spaceApplyId}/show-date")
    @Operation(summary = "공연 준비 시, 공연 날짜와 공연 디데이 API",description = "공연 준비 시 위에 띄워야하는 공연 날짜, 공연까지의 디데이 API입니다")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공")
    })
    public ApiResponse<ShowsResponseDTO.ShowDateDTO> getShowDate(@PathVariable Long spaceApplyId){

        ShowsResponseDTO.ShowDateDTO showDate=showsService.getShowDate(spaceApplyId);

        return ApiResponse.onSuccess(showDate);
    }

    @PostMapping(value="/{performerProfileId}/show-register")
    @Operation(summary = "공연자 공연 준비-공연 정보 등록 api", description = "공연 포스터 및 정보 등록 시에 필요한 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공")
    })
    public ApiResponse<ShowsResponseDTO.postShowDTO> createShow(
            @PathVariable Long performerProfileId,
            @RequestPart ShowsRequestDTO.ShowInfoDTO showsRequestDTO)throws IOException {

        Shows shows = showsService.createShows(showsRequestDTO, performerProfileId);

        return ApiResponse.onSuccess(ShowsConverter.toPostShowDTO(shows));
    }

    @PostMapping(value="/upload-poster", consumes = "multipart/form-data")
    @Operation(summary = "공연 포스터url 반환 API", description = "포스터를 업로드하고 해당 URL을 반환하는 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공")
    })
    public ApiResponse<ShowsResponseDTO.ShowPosterDTO> uploadPoster(
            @RequestPart MultipartFile poster) throws IOException {

        if (poster == null || poster.isEmpty()) {
            throw new IllegalArgumentException("포스터 파일이 필요합니다.");
        }

        String objectKey = "posters/" + UUID.randomUUID().toString() + "-" + poster.getOriginalFilename();
        String posterUrl = s3Service.uploadFile(poster, objectKey);

        ShowsResponseDTO.ShowPosterDTO responseDTO = new ShowsResponseDTO.ShowPosterDTO(posterUrl);

        return ApiResponse.onSuccess(responseDTO);
    }



    @PostMapping(value="/{showId}/show-register/description",consumes = "multipart/form-data")
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

    @PostMapping(value="/{showId}/ticket-register")
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

    @PostMapping(value="/{showId}/requirement-register")
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
