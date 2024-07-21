package umc.ShowHoo.web.space.controller;


import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import umc.ShowHoo.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import org.springframework.web.bind.annotation.RestController;
import umc.ShowHoo.web.space.converter.SpaceConverter;
import umc.ShowHoo.web.space.dto.SpaceRequestDTO;
import umc.ShowHoo.web.space.dto.SpaceResponseDTO;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.space.exception.handler.SpaceHandler;
import umc.ShowHoo.web.space.service.SpaceService;
import umc.ShowHoo.web.spaceUser.entity.SpaceUser;
import umc.ShowHoo.web.spaceUser.repository.SpaceUserRepository;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class SpaceController {
    private static final Logger logger = LoggerFactory.getLogger(SpaceController.class);
    private final SpaceService spaceService;
    private final SpaceUserRepository spaceUserRepository;

    @PostMapping("/spaces/{spaceUserId}")
    @Operation(summary = "공연장 등록 API", description = "공연장 등록할 때 필요한 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<SpaceResponseDTO.ResultDTO> createSpace(@PathVariable Long spaceUserId, @RequestBody SpaceRequestDTO spaceRequestDTO) {
        logger.info("Received request to create space: {}", spaceRequestDTO);
        try {
            // spaceUserId로 SpaceUser 조회
            Optional<SpaceUser> optionalSpaceUser = spaceUserRepository.findById(spaceUserId);
            SpaceUser spaceUser = optionalSpaceUser.orElse(null);
            if (spaceUser == null) {
                return ApiResponse.onFailure("NOT_FOUND", "SpaceUser not found for given spaceUserId", null);
            }

            // Space 엔티티 생성 및 Member 설정
            Space space = SpaceConverter.toEntity(spaceRequestDTO);
            space.setSpaceUser(spaceUser);

            // Space 저장
            Space savedSpace = spaceService.saveSpace(space);
            SpaceResponseDTO.ResultDTO result = SpaceConverter.toSpaceResponseDTO(savedSpace);

            return ApiResponse.onSuccess(result);

        } catch (SpaceHandler e) {
            logger.error("SpaceHandler error occurred while creating space", e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error occurred while creating space", e);
            return ApiResponse.onFailure("ERROR_CODE", "Unexpected error occurred while creating space", null);
        }
    }

}
