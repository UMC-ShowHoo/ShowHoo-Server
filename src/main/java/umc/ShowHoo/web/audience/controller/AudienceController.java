package umc.ShowHoo.web.audience.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.ShowHoo.apiPayload.ApiResponse;
import umc.ShowHoo.web.shows.entity.Shows;
import umc.ShowHoo.web.audience.converter.AudienceConverter;
import umc.ShowHoo.web.audience.dto.AudienceResponseDTO;
import umc.ShowHoo.web.audience.service.AudienceQueryService;

import java.util.List;

@RestController
@RequestMapping("/aud")
@RequiredArgsConstructor
public class AudienceController {

    private final AudienceQueryService audienceQueryService;

    //공연 찜 정보 제공 필요
    @GetMapping
    @Operation(summary = "전체 공연게시글 조회 API", description = "공연게시글을 전체 조회하는 API, 페이지 번호 필요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    public ApiResponse<AudienceResponseDTO.getShowsListDTO> getShowsList(){
        List<Shows> showsList = audienceQueryService.getShowsList();
        return ApiResponse.onSuccess(AudienceConverter.toGetShowsListDTO(showsList));
    }

    @GetMapping("/{audienceId}")
    @Operation(summary = "전체 공연게시글 조회 API", description = "공연게시글을 전체 조회하는 API, 관람자 별 찜 여부 확인 가능, 페이지 번호 필요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    public ApiResponse<AudienceResponseDTO.getShowsListDTO> getLikedShowsList(@PathVariable(name = "audienceId")Long id){
        return ApiResponse.onSuccess(audienceQueryService.getLikedShowsList(id));
    }

    @GetMapping("/{showsId}/detail")
    @Operation(summary = "상세 공연게시글 조회 API", description = "공연게시글의 상세정보를 조회하는 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SHOW001", description = "Shows not found", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<AudienceResponseDTO.ShowsDetailDTO> getShows(@PathVariable(name = "showsId") Long showsId){
        Shows shows = audienceQueryService.getShowsDetail(showsId);
        Boolean isExist = audienceQueryService.isDescriptionExist(shows);
        return ApiResponse.onSuccess(AudienceConverter.toGetShowsDetailDTO(shows, isExist));
    }

    @GetMapping("/search/{request}")
    @Operation(summary = "공연게시글 검색 조회 API", description = "공연게시글의 제목으로 검색하여 조회하는 API, 페이지 번호 필요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    public ApiResponse<AudienceResponseDTO.getShowsListDTO> searchShows(@PathVariable(name = "request") String request){
        List<Shows> searchedList = audienceQueryService.getSearchedShowsList(request);
        return ApiResponse.onSuccess(AudienceConverter.toGetShowsListDTO(searchedList));
    }

    @GetMapping("/{audienceId}/search/{request}")
    @Operation(summary = "공연게시글 검색 조회 API", description = "공연게시글의 제목으로 검색하여 조회하는 API, 관람자 별 찜 여부 확인 가능, 페이지 번호 필요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    public ApiResponse<AudienceResponseDTO.getShowsListDTO> searchLikedShows(@PathVariable(name = "audienceId") Long id, @PathVariable(name = "request") String request){
        return ApiResponse.onSuccess(audienceQueryService.getSearchedLikedShowsList(id, request));
    }

}
