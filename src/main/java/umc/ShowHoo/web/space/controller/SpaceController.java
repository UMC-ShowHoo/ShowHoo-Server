package umc.ShowHoo.web.space.controller;


import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import umc.ShowHoo.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.ShowHoo.web.space.converter.SpaceConverter;
import umc.ShowHoo.web.space.dto.SpaceRequestDTO;
import umc.ShowHoo.web.space.dto.SpaceResponseDTO;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.space.service.SpaceService;

@RestController
@RequiredArgsConstructor
public class SpaceController {
    private static final Logger logger = LoggerFactory.getLogger(SpaceController.class);
    private final SpaceService spaceService;

    @PostMapping("/spaces")
    @Operation(summary = "공연장 등록", description = "공연장 등록할 때 필요한 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<SpaceResponseDTO.ResultDTO> createSpace(@RequestBody SpaceRequestDTO spaceRequestDTO) {
        logger.info("Received request to create space: {}", spaceRequestDTO);
        try {
            Space space = SpaceConverter.toEntity(spaceRequestDTO);
            logger.info("Converted SpaceRequestDTO to Space entity");
            Space savedSpace = spaceService.saveSpace(space);
            logger.info("Space entity saved successfully with ID: {}", savedSpace.getId());
            SpaceResponseDTO.ResultDTO result = SpaceConverter.toSpaceResponseDTO(savedSpace);
            return ApiResponse.onSuccess(result);
        } catch (Exception e) {
            logger.error("Error occurred while creating space", e);
            return ApiResponse.onFailure("ERROR_CODE", "Error occurred while creating space", null);
        }
    }

}
