package umc.ShowHoo.web.rentalFile.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import umc.ShowHoo.apiPayload.ApiResponse;
import org.springframework.web.multipart.MultipartFile;
import umc.ShowHoo.web.shows.entity.Shows;
import umc.ShowHoo.web.shows.repository.ShowsRepository;
import umc.ShowHoo.web.rentalFile.converter.RentalFileConverter;
import umc.ShowHoo.web.rentalFile.dto.RentalFileResponseDTO;
import umc.ShowHoo.web.rentalFile.entity.RentalFile;
import umc.ShowHoo.web.rentalFile.handler.RentalFileHandler;
import umc.ShowHoo.web.rentalFile.service.RentalFileService;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.space.repository.SpaceRepository;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class RentalFileController {
    private static final Logger logger = LoggerFactory.getLogger(RentalFileController.class);
    private final RentalFileService rentalFileService;
    private final ShowsRepository showsRepository;
    private final SpaceRepository spaceRepository;

    @PostMapping(value = "/performer/{showId}/prepare",consumes = "multipart/form-data")
    @Operation(summary="공연자 - 공연준비 큐시트 작성 API", description = "공연자가 큐시트 작성 시에 제출해야하는 API입니다")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<RentalFileResponseDTO.postFileDTO> createPerformerFile(
            @PathVariable Long showId,
            @RequestPart(required = false)MultipartFile setList,
            @RequestPart(required = false)MultipartFile rentalTime,
            @RequestPart(required = false)MultipartFile addOrder){
        try {
            Optional<Shows> optionalShows = showsRepository.findById((showId));
            Shows shows = optionalShows.orElse(null);
            if (shows == null) {
                return ApiResponse.onFailure("NOT_FOUND", "Show not found for given showId", null);

            }
            RentalFile rentalFile=rentalFileService.createPerformerFile(setList,rentalTime,addOrder);
            rentalFile.setShows(shows);

            return ApiResponse.onSuccess(RentalFileConverter.toPostFileDTO(rentalFile));
        }catch (RentalFileHandler e){
            logger.error("RentalFileHandler error occurred while creating rentalFile",e);
            throw e;
        }
    }

    @GetMapping(value = "/performer/{showId}/prepare", consumes = "multipart/form-data")
    @Operation(summary = "공연자 - 큐시트 작성 API",description = "공연자가 큐시트 작성 시에 다운 받을 양식 자료입니다")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<RentalFileResponseDTO.SpaceUserSaveDTO> getRentalForm(@PathVariable Long showsId){
        RentalFileResponseDTO.SpaceUserSaveDTO spaceUserSaveDTO=rentalFileService.getFormFile(showsId);
        return ApiResponse.onSuccess(spaceUserSaveDTO);

    }


    @PostMapping(value = "/space/{spaceId}/prepare",consumes = "multipart/form-data")
    @Operation(summary="공연장 - 공연준비 큐시트 작성 API", description = "공연장이 큐시트 작성 시에 제출해야하는 API입니다")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<RentalFileResponseDTO.postFileDTO> createFormFile(
            @PathVariable Long spaceId,
            @RequestPart(required = false)MultipartFile setListForm,
            @RequestPart(required = false)MultipartFile rentalTimeForm,
            @RequestPart(required = false)MultipartFile addOrderForm){
        try {
            Optional<Space> optionalSpace = spaceRepository.findById((spaceId));
            Space space = optionalSpace.orElse(null);
            if (space == null) {
                return ApiResponse.onFailure("NOT_FOUND", "Space not found for given spaceId", null);

            }
            RentalFile rentalFile=rentalFileService.createFormFile(setListForm,rentalTimeForm,addOrderForm);
            rentalFile.setSpace(space);

            return ApiResponse.onSuccess(RentalFileConverter.toPostFileDTO(rentalFile));
        }catch (RentalFileHandler e){
            logger.error("RentalFileHandler error occurred while creating rentalFile",e);
            throw e;
        }
    }

    @GetMapping(value = "/space/{showId}/prepare", consumes = "multipart/form-data")
    @Operation(summary = "공연장 - 큐시트 작성 API",description = "공연장이 큐시트 작성 시에 다운 받을 양식 자료입니다")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<RentalFileResponseDTO.PerformerSaveDTO> getPerformerFile(@PathVariable Long showId){
        RentalFileResponseDTO.PerformerSaveDTO performerSaveDTO=rentalFileService.getPerformerFile(showId);
        return ApiResponse.onSuccess(performerSaveDTO);

    }

}
