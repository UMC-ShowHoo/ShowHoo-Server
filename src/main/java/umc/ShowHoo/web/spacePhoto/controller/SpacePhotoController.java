package umc.ShowHoo.web.spacePhoto.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import umc.ShowHoo.apiPayload.ApiResponse;
import umc.ShowHoo.web.spacePhoto.dto.SpacePhotoResponseDTO;
import umc.ShowHoo.web.spacePhoto.service.SpacePhotoService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SpacePhotoController {
    private final SpacePhotoService spacePhotoService;

    @Operation(summary = "공연장 사진 등록 API", description = "공연장 사진을 등록할 때 필요한 API입니다. 사진을 업로드하면 사진 url를 반환해줍니다. 사진 url은 최종 공연장 등록하기 버튼을 눌렀을 때 넘겨주시면 돼요")
    @Parameter(
            in = ParameterIn.HEADER,
            name = "Authorization", required = true,
            schema = @Schema(type = "string"),
            description = "Bearer [Access 토큰]"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    @PostMapping(value = "/spaces/photos", consumes = "multipart/form-data")
    public ApiResponse<List<String>> uploadSpacePhotos(
            @RequestPart("photos") List<MultipartFile> photos){
        List<String> photoUrls = spacePhotoService.uploadPhotos(photos);
        return ApiResponse.onSuccess(photoUrls);
    }
}
