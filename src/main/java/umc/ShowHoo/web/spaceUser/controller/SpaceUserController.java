package umc.ShowHoo.web.spaceUser.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import umc.ShowHoo.apiPayload.ApiResponse;
import umc.ShowHoo.web.spaceUser.dto.SpaceUserResponseDTO;
import umc.ShowHoo.web.spaceUser.service.SpaceUserService;

@RestController
@RequiredArgsConstructor
public class SpaceUserController {
    private final SpaceUserService spaceUserService;

    @GetMapping("/space-user/mypage/{spaceUserId}")
    @Operation(summary = "공연장 마이페이지 조회 API", description = "공연장 마이페이지( 회원 정보)를 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<SpaceUserResponseDTO.MyPageDTO> getSpaceUserMyPage(@PathVariable Long spaceUserId) {
        SpaceUserResponseDTO.MyPageDTO myPage = spaceUserService.getMyPageProfiles(spaceUserId);
        return ApiResponse.onSuccess(myPage);
    }
}
