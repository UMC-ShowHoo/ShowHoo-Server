package umc.ShowHoo.web.performerProfile.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import umc.ShowHoo.aws.s3.AmazonS3Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.ShowHoo.apiPayload.ApiResponse;
import umc.ShowHoo.aws.s3.Uuid;
import umc.ShowHoo.aws.s3.UuidRepository;
import umc.ShowHoo.web.performerProfile.dto.PerformerProfileRequestDTO;
import umc.ShowHoo.web.performerProfile.service.PerformerProfileService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PerformerProfileController {

    private final PerformerProfileService performerProfileService;

    @PostMapping(value = "/profile/{performerUserId}",consumes = "multipart/form-data")
    @Operation(summary = "공연자 프로필 등록 API", description = "공연자가 프로필을 등록할 때 필요한 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<Void> createProfile(
            @PathVariable("performerUserId") Long performerUserId,
            @RequestPart("profileDTO") PerformerProfileRequestDTO.CreateProfileDTO profileDTO,
            @RequestPart("performerProfileImages") List<MultipartFile> profileImages){

        performerProfileService.createProfile(performerUserId, profileDTO, profileImages);
        return ApiResponse.onSuccess(null);
    }

    @PutMapping("/profile/{performerUserId}/{profileId}/text")
    @Operation(summary = "공연자 프로필 텍스트 수정 API", description = "공연자 프로필의 텍스트 정보를 수정하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<Void> updateProfileText(
            @PathVariable("performerUserId") Long performerUserId,
            @PathVariable("profileId") Long profileId,
            @RequestBody PerformerProfileRequestDTO.UpdateProfileTextDTO profileDTO) {

        performerProfileService.updateProfileText(performerUserId, profileId, profileDTO);
        return ApiResponse.onSuccess(null);
    }

}
