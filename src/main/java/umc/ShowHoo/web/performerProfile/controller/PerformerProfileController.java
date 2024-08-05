package umc.ShowHoo.web.performerProfile.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.ShowHoo.apiPayload.ApiResponse;
import umc.ShowHoo.web.performerProfile.dto.PerformerProfileRequestDTO;
import umc.ShowHoo.web.performerProfile.dto.PerformerProfileResponseDTO;
import umc.ShowHoo.web.performerProfile.service.PerformerProfileService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PerformerProfileController {

    private final PerformerProfileService performerProfileService;

    @PostMapping(value = "/profileImage/upload", consumes = "multipart/form-data")
    @Parameter(
            in = ParameterIn.HEADER,
            name = "Authorization", required = true,
            schema = @Schema(type = "string"),
            description = "Bearer [Access 토큰]"
    )
    @Operation(summary = "프로필 이미지 업로드 API", description = "프로필 이미지를 S3에 업로드하고 URL을 반환합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<List<String>>uploadProfileImages(@RequestPart List<MultipartFile> profileImages){
        List<String> imageUrls = performerProfileService.uploadProfileImages(profileImages);
        return ApiResponse.onSuccess(imageUrls);
    }




    @PostMapping(value = "/profile/{performerUserId}")
    @Parameter(
            in = ParameterIn.HEADER,
            name = "Authorization", required = true,
            schema = @Schema(type = "string"),
            description = "Bearer [Access 토큰]"
    )
    @Operation(summary = "공연자 프로필 등록 API", description = "공연자가 프로필을 등록할 때 필요한 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<Void> createProfile(
            @PathVariable("performerUserId") Long performerUserId,
            @RequestBody PerformerProfileRequestDTO.CreateProfileDTO profileDTO){

        performerProfileService.createProfile(performerUserId, profileDTO);
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

    @DeleteMapping("/profile/profileImage")
    @Operation(summary = "공연자 프로필 이미지 수정 - 이미지 삭제 API", description = "공연자 프로필 수정할 때 프로필 이미지를 삭제하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<Void> deleteProfileImage(@RequestBody PerformerProfileRequestDTO.DeleteProfileImageDTO requestDTO) {
        performerProfileService.deleteProfileImageByUrl(requestDTO.getProfileImageUrl());
        return ApiResponse.onSuccess(null);
    }

    @PostMapping(value = "/profile/{performerUserId}/{profileId}/profileImage" ,consumes = "multipart/form-data")
    @Operation(summary = "공연자 프로필 이미지 수정 - 이미지 추가 API", description = "공연자 프로필 수정할 때 프로필 이미지를 추가하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<String> addProfileImage(@PathVariable Long performerUserId,
                                             @PathVariable Long profileId,
                                             @ModelAttribute PerformerProfileRequestDTO.AddProfileImageDTO requestDTO) {
        String imageUrl = performerProfileService.addProfileImage(performerUserId, profileId, requestDTO);
        return ApiResponse.onSuccess(imageUrl);
    }

    @DeleteMapping("/profile/{performerUserId}/{profileId}")
    @Operation(summary = "공연자 프로필 삭제 API", description = "공연자 프로필을 삭제하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<Void> deleteProfile(@PathVariable Long performerUserId, @PathVariable Long profileId) {
        performerProfileService.deleteProfile(performerUserId, profileId);
        return ApiResponse.onSuccess(null);
    }

    @GetMapping("/profile/{performerUserId}")
    @Operation(summary = "공연자 프로필 조회 API", description = "공연자의 모든 프로필을 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<List<PerformerProfileResponseDTO.ProfileDTO>> getAllProfiles(@PathVariable Long performerUserId) {
        List<PerformerProfileResponseDTO.ProfileDTO> profiles = performerProfileService.getAllProfiles(performerUserId);
        return ApiResponse.onSuccess(profiles);
    }

    @GetMapping("/performer/mypage/{performerUserId}")
    @Operation(summary = "공연자 마이페이지 조회 API", description = "공연자의 마이페이지(가장 최근 프로필, 회원 정보)를 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<PerformerProfileResponseDTO.MyPageProfileDTO> getMyPageProfiles(@PathVariable Long performerUserId) {
        PerformerProfileResponseDTO.MyPageProfileDTO myPageProfile = performerProfileService.getMyPageProfiles(performerUserId);
        return ApiResponse.onSuccess(myPageProfile);
    }

}
