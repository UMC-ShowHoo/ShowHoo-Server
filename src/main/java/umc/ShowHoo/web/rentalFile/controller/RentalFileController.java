package umc.ShowHoo.web.rentalFile.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import umc.ShowHoo.apiPayload.ApiResponse;
import org.springframework.web.multipart.MultipartFile;
import umc.ShowHoo.web.shows.repository.ShowsRepository;
import umc.ShowHoo.web.rentalFile.converter.RentalFileConverter;
import umc.ShowHoo.web.rentalFile.dto.RentalFileResponseDTO;
import umc.ShowHoo.web.rentalFile.entity.RentalFile;
import umc.ShowHoo.web.rentalFile.handler.RentalFileHandler;
import umc.ShowHoo.web.rentalFile.service.RentalFileService;
import umc.ShowHoo.web.space.repository.SpaceRepository;

@RestController
@RequiredArgsConstructor
public class RentalFileController {
    private static final Logger logger = LoggerFactory.getLogger(RentalFileController.class);
    private final RentalFileService rentalFileService;
    private final ShowsRepository showsRepository;
    private final SpaceRepository spaceRepository;

    @PostMapping(value = "/performer/{showId}/prepare",consumes = "multipart/form-data")
    @Operation(summary="공연자 - 공연준비 큐시트 작성 API", description = "공연자가 큐시트 작성 시에 제출해야하는 API입니다. 공연장이 폼 파일을 올린 후에 등록이 가능하니, 이 점 유의바랍니다!")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<RentalFileResponseDTO.postFileDTO> createPerformerFile(
            @PathVariable Long showId,
            @RequestPart(required = false)MultipartFile setList,
            @RequestPart(required = false)MultipartFile rentalTime,
            @RequestPart(required = false)MultipartFile addOrder){
        try {


            RentalFile rentalFile=rentalFileService.createPerformerFile(setList,rentalTime,addOrder,showId);


            return ApiResponse.onSuccess(RentalFileConverter.toPostFileDTO(rentalFile));
        }catch (RentalFileHandler e){
            logger.error("RentalFileHandler error occurred while creating rentalFile",e);
            throw e;
        }
    }

    @GetMapping(value = "/performer/{showId}/prepare")
    @Operation(summary = "공연자 - 큐시트 작성 폼 파일 다운 API",description = "공연자가 큐시트 작성 시에 다운 받을 양식 자료입니다")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<RentalFileResponseDTO.SpaceUserSaveDTO> getRentalForm(@PathVariable Long showId){
        RentalFileResponseDTO.SpaceUserSaveDTO spaceUserSaveDTO=rentalFileService.getFormFile(showId);
        return ApiResponse.onSuccess(spaceUserSaveDTO);

    }


    @PostMapping(value = "/space/{spaceApplyId}/prepare",consumes = "multipart/form-data")
    @Operation(summary="공연장 - 공연준비 큐시트 작성 API", description = "공연장이 큐시트 작성 시에 제출해야하는 API입니다")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<RentalFileResponseDTO.postFileDTO> createFormFile(
            @PathVariable Long spaceApplyId,
            @RequestPart(required = false)MultipartFile setListForm,
            @RequestPart(required = false)MultipartFile rentalTimeForm,
            @RequestPart(required = false)MultipartFile addOrderForm){
        try {
            RentalFile rentalFile=rentalFileService.createFormFile(setListForm,rentalTimeForm,addOrderForm,spaceApplyId);

            return ApiResponse.onSuccess(RentalFileConverter.toPostFileDTO(rentalFile));
        }catch (RentalFileHandler e){
            logger.error("RentalFileHandler error occurred while creating rentalFile",e);
            throw e;
        }
    }

    @GetMapping(value = "/space/{showId}/prepare")
    @Operation(summary = "공연장 - 큐시트 파일 다운 API",description = "공연장이 큐시트 작성 시에 다운 받을 양식 자료입니다")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<RentalFileResponseDTO.PerformerSaveDTO> getPerformerFile(@PathVariable Long showId){
        RentalFileResponseDTO.PerformerSaveDTO performerSaveDTO=rentalFileService.getPerformerFile(showId);
        return ApiResponse.onSuccess(performerSaveDTO);

    }

}
