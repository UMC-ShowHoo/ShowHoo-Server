package umc.ShowHoo.web.space.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import umc.ShowHoo.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import umc.ShowHoo.web.rentalFee.service.RentalFeeService;
import umc.ShowHoo.web.space.converter.SpaceConverter;
import umc.ShowHoo.web.space.dto.SpaceRequestDTO;
import umc.ShowHoo.web.space.dto.SpaceResponseDTO;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.space.entity.SpaceType;
import umc.ShowHoo.web.space.exception.handler.SpaceHandler;
import umc.ShowHoo.web.space.service.SpaceService;
import umc.ShowHoo.web.spaceUser.entity.SpaceUser;
import umc.ShowHoo.web.spaceUser.repository.SpaceUserRepository;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class SpaceController {
    private static final Logger logger = LoggerFactory.getLogger(SpaceController.class);
    private final SpaceService spaceService;
    private final SpaceUserRepository spaceUserRepository;
    private final RentalFeeService rentalFeeService;

    @PostMapping(value = "/spaces/{spaceUserId}", consumes = "multipart/form-data")
    @Operation(summary = "공연장 등록 API", description = "공연장 등록할 때 필요한 API입니다. 사진을 등록했을 때 반환받았던 url값을 photoUrls에 넣어주시면 되고 rentalFee는 비성수기 대관료, peakSeasonRentalFee는 성수기 대관료입니다. dayOfWeek에는 MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY 중에 해당하는 요일, fee에는 가격 넣어주시면 됩니다. 월~일까지 모든 요일의 가격을 보내주셔야 합니다. spaceType은 공연장 종류인데 CONCERTHALL, ARTHALL, GRANDHALL, SMALLHALL, GRANDTHEATER, SMALLTHEATER 중에 하나로 넣어주시면 돼요")
    @Parameter(
            in = ParameterIn.HEADER,
            name = "Authorization", required = true,
            schema = @Schema(type = "string"),
            description = "Bearer [Access 토큰]"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<SpaceResponseDTO.ResultDTO> createSpace(
            @PathVariable Long spaceUserId,
            @RequestPart SpaceRequestDTO.SpaceRegisterRequestDTO spaceRegisterRequestDTO,
            @RequestPart(required = false) MultipartFile soundEquipment,
            @RequestPart(required = false) MultipartFile lightingEquipment,
            @RequestPart(required = false) MultipartFile stageMachinery,
            @RequestPart(required = false) MultipartFile spaceDrawing,
            @RequestPart(required = false) MultipartFile spaceStaff,
            @RequestPart(required = false) MultipartFile spaceSeat) {
        try {
            // spaceUserId로 SpaceUser 조회
            Optional<SpaceUser> optionalSpaceUser = spaceUserRepository.findById(spaceUserId);
            SpaceUser spaceUser = optionalSpaceUser.orElse(null);
            if (spaceUser == null) {
                return ApiResponse.onFailure("NOT_FOUND", "SpaceUser not found for given spaceUserId", null);
            }

            // Space 저장
            Space savedSpace = spaceService.saveSpace(spaceRegisterRequestDTO, spaceUser, soundEquipment, lightingEquipment, stageMachinery, spaceDrawing, spaceStaff, spaceSeat);
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

    @GetMapping("/spaces/{spaceId}/description")
    @Operation(summary = "공연장 세부정보 공연장 소개 API", description = "공연장 세부정보를 조회할 때 공연장 소개에 필요한 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<SpaceResponseDTO.SpaceDescriptionDTO> getSpaceDescription(@PathVariable Long spaceId) {
        SpaceResponseDTO.SpaceDescriptionDTO spaceDescription = spaceService.getSpaceDescriptionBySpaceUserId(spaceId);
        return ApiResponse.onSuccess(spaceDescription);
    }

    @Operation(summary = "공연장 세부정보 헤더 조회 API", description = "공연장 세부정보를 조회할 때 사진과 리뷰 갯수, 리뷰 평점, 위치 조회하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    @GetMapping("/spaces/{spaceId}/header")
    public ApiResponse<SpaceResponseDTO.SpaceInfoDTO> getSpaceDetails(@PathVariable Long spaceId) {
        SpaceResponseDTO.SpaceInfoDTO spaceDetails = spaceService.getSpaceDetails(spaceId);
        return ApiResponse.onSuccess(spaceDetails);
    }

    @GetMapping("/spaces/{spaceId}/notice")
    @Operation(summary = "공연장 세부정보 유의사항 API", description = "공연장 세부정보를 조회할 때 유의사항에 필요한 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<SpaceResponseDTO.SpaceNoticeDTO> getSpaceNotice(@PathVariable Long spaceId) {
        SpaceResponseDTO.SpaceNoticeDTO spaceNotice = spaceService.getSpaceNotice(spaceId);
        return ApiResponse.onSuccess(spaceNotice);

    }

    @GetMapping("/spaces/{spaceId}/file")
    @Operation(summary = "공연장 세부정보 시설정보 API", description = "공연장 세부정보를 조회할 때 시설정보에 필요한 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<SpaceResponseDTO.SpaceFileDTO> getSpaceFile(@PathVariable Long spaceId) {
        SpaceResponseDTO.SpaceFileDTO spaceFileDTO = spaceService.getSpaceFile(spaceId);
        return ApiResponse.onSuccess(spaceFileDTO);
    }

    @GetMapping("spaces/{spaceId}/pay")
    @Operation(summary = "공연장 대관 신청하기 - 결제하기 API", description = "공연장 대관 신청하기 - 결제하기에 필요한 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    @Parameter(
            in = ParameterIn.HEADER,
            name = "Authorization", required = true,
            schema = @Schema(type = "string"),
            description = "Bearer [Access 토큰]"
    )
    public ApiResponse<SpaceResponseDTO.SpacePayDTO> getSpacePay(@PathVariable Long spaceId) {
        SpaceResponseDTO.SpacePayDTO spacePayDTO = spaceService.getSpacePay(spaceId);
        return ApiResponse.onSuccess(spacePayDTO);
    }

    @PostMapping("/spaces/{spaceId}/price")
    @Operation(summary = "공연장 세부정보 가격 API", description = "공연장 세부정보 조회할 때 예약 날짜와 추가서비스를 받는 API입니다. 요일마다, 추가서비스를 추가할때마다 가격이 달라서 날짜를 선택하고 추가서비스를 선택한 후 대관비가 응답으로 나옵니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<SpaceResponseDTO.SpacePriceResponseDTO> getSpaceDate(@PathVariable Long spaceId, @RequestBody SpaceRequestDTO.SpacePriceDTO spacePriceDTO) {
        SpaceResponseDTO.SpacePriceResponseDTO spacePrice = rentalFeeService.getSpaceDate(spaceId, spacePriceDTO.getDate(), spacePriceDTO.getAdditionalServices());
        return ApiResponse.onSuccess(spacePrice);
    }

    @GetMapping("/spaces")
    @Operation(summary = "공연장 전체 조회 API", description = "공연장 찜 순과 평점 순으로 각각 8개씩 조회하는 API입니다.")
    public ApiResponse<SpaceResponseDTO.SpaceListDTO> getTopSpaces() {
        SpaceResponseDTO.SpaceListDTO spaces = spaceService.getTopSpaces();
        return ApiResponse.onSuccess(spaces);
    }

    @GetMapping("/spaces/search")
    @Operation(summary = "공연장 검색 API", description = "공연장, 지역, 날짜, 유형으로 검색하고 가격과 수용인원으로 필터링하는 API입니다.")
    public ApiResponse<SpaceResponseDTO.SpaceFilteredListDTO> searchSpaces(@RequestParam(required = false) String name,
                                                                           @RequestParam(required = false) String city,
                                                                           @RequestParam(required = false) String district,
                                                                           @RequestParam(required = false) LocalDate date,
                                                                           @RequestParam(required = false) SpaceType type,
                                                                           @RequestParam(required = false) Integer minPrice,
                                                                           @RequestParam(required = false) Integer maxPrice,
                                                                           @RequestParam(required = false) Integer minCapacity,
                                                                           @RequestParam(required = false) Integer maxCapacity,
                                                                           @RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "12") int size) {
        SpaceRequestDTO.SpaceSearchRequestDTO searchRequest = new SpaceRequestDTO.SpaceSearchRequestDTO(
                name, city, district, date, type, minPrice, maxPrice, minCapacity, maxCapacity, page, size);

        SpaceResponseDTO.SpaceFilteredListDTO spaces = spaceService.searchSpaces(searchRequest);
        return ApiResponse.onSuccess(spaces);
    }

    @GetMapping("/spaces/{performerId}/prefer")
    @Operation(summary = "찜한 공연장 조회 API", description = "performer가 찜한 공연장을 조회하는 API입니다.")
    @Parameter(
            in = ParameterIn.HEADER,
            name = "Authorization", required = true,
            schema = @Schema(type = "string"),
            description = "Bearer [Access 토큰]"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<SpaceResponseDTO.SpaceFilteredListDTO> getPreferSpace(@PathVariable Long performerId) {
        SpaceResponseDTO.SpaceFilteredListDTO spaces = spaceService.getPreferSpace(performerId);
        return ApiResponse.onSuccess(spaces);
    }
}
