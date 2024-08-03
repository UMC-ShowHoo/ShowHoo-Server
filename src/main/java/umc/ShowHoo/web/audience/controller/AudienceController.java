package umc.ShowHoo.web.audience.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import umc.ShowHoo.apiPayload.ApiResponse;
import umc.ShowHoo.web.audience.dto.AudienceResponseDTO;

@RestController
@RequestMapping("/aud")
@RequiredArgsConstructor
public class AudienceController {

    @GetMapping
    @Operation(summary = "전체 공연게시글 조회 API", description = "공연게시글을 전체 조회하는 API, 페이지 번호 필요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    public ApiResponse<AudienceResponseDTO.getShowsListDTO> getShowsList(@RequestParam(name = "page") Integer page){
        return null;
    }

    @GetMapping("/{showsId}")
    @Operation(summary = "상세 공연게시글 조회 API", description = "공연게시글의 상세정보를 조회하는 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SHOW001", description = "Shows not found", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    public ApiResponse<AudienceResponseDTO.ShowsDetailDTO> getShows(@PathVariable(name = "showsId") Long showsId){
        return null;
    }

    @GetMapping("/liked")
    @Operation(summary = "공연게시글 필터링 조회 API", description = "공연게시글을 좋아요 순으로 필터링하여 전체 조회하는 API, 페이지 번호 필요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    public ApiResponse<AudienceResponseDTO.getShowsListDTO> getLikedShowsList(@RequestParam(name = "page") Integer page){
        return null;
    }
}
