package umc.ShowHoo.web.performerProfile.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.ShowHoo.apiPayload.ApiResponse;
import umc.ShowHoo.web.performerProfile.dto.PerformerProfileRequestDTO;
import umc.ShowHoo.web.performerProfile.entity.PerformerProfile;
import umc.ShowHoo.web.performerProfile.service.PerformerProfileService;

import java.util.List;

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

}
