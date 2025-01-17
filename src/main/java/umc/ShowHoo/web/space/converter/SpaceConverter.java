package umc.ShowHoo.web.space.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.ShowHoo.web.holiday.entity.Holiday;
import umc.ShowHoo.web.peakSeasonRentalFee.entity.PeakSeasonRentalFee;
import umc.ShowHoo.web.rentalFee.entity.DayOfWeek;
import umc.ShowHoo.web.rentalFee.entity.RentalFee;
import umc.ShowHoo.web.space.dto.SpaceRequestDTO;
import umc.ShowHoo.web.space.dto.SpaceResponseDTO;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.space.repository.SpaceRepository;
import umc.ShowHoo.web.spaceAdditionalService.entity.SpaceAdditionalService;
import umc.ShowHoo.web.spacePhoto.entity.SpacePhoto;
import umc.ShowHoo.web.spacePrefer.entity.SpacePrefer;
import umc.ShowHoo.web.spaceUser.entity.SpaceUser;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SpaceConverter {
    private final SpaceRepository spaceRepository;

    public static Space toEntity(SpaceRequestDTO.SpaceRegisterRequestDTO dto, String soundEquipmentUrl, String lightingEquipmentUrl, String stageMachineryUrl, String spaceDrawingUrl, String spaceStaffUrl, String spaceSeatUrl, SpaceUser spaceUser) {
        Space space = Space.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .rentalHours(dto.getRentalHours())
                .location(dto.getLocation())
                .area(dto.getArea())
                .seatingCapacity(dto.getSeatingCapacity())
                .standingCapacity(dto.getStandingCapacity())
                .bankOwner(dto.getBankOwner())
                .bankAccount(dto.getBankAccount())
                .bankName(dto.getBankName())
                .soundEquipment(soundEquipmentUrl)
                .lightingEquipment(lightingEquipmentUrl)
                .stageMachinery(stageMachineryUrl)
                .spaceDrawing(spaceDrawingUrl)
                .spaceStaff(spaceStaffUrl)
                .spaceSeat(spaceSeatUrl)
                .notice(dto.getNotice())
                .spaceUser(spaceUser)
                .spaceType(dto.getSpaceType())
                .build();

        if (dto.getPhotoUrls() != null) {
            List<SpacePhoto> photos = dto.getPhotoUrls().stream()
                    .map(url -> SpacePhoto.builder().photoUrl(url).space(space).build())
                    .collect(Collectors.toList());
            space.setPhotos(photos);
        }

        if (dto.getHolidays() != null) {
            List<Holiday> holidays = dto.getHolidays().stream()
                    .map(date -> Holiday.builder().date(date).space(space).build())
                    .collect(Collectors.toList());
            space.setHolidays(holidays);
        }

        List<RentalFee> rentalFees = dto.getRentalFees().stream()
                .map(feeDTO -> RentalFee.builder()
                        .dayOfWeek(DayOfWeek.valueOf(feeDTO.getDayOfWeek()))
                        .fee(feeDTO.getFee())
                        .space(space)
                        .build())
                .collect(Collectors.toList());
        space.setRentalFees(rentalFees);

        List<PeakSeasonRentalFee> peakSeasonRentalFees = dto.getPeakSeasonRentalFees().stream()
                .map(peakfeeDTO -> PeakSeasonRentalFee.builder()
                        .dayOfWeek(DayOfWeek.valueOf(peakfeeDTO.getDayOfWeek()))
                        .fee(peakfeeDTO.getFee())
                        .space(space)
                        .build())
                .collect(Collectors.toList());
        space.setPeakSeasonRentalFees(peakSeasonRentalFees);

        List<SpaceAdditionalService> additionalServices = dto.getAdditionalServices().stream()
                .map(serviceDTO -> SpaceAdditionalService.builder()
                        .title(serviceDTO.getTitle())
                        .price(serviceDTO.getPrice())
                        .space(space)
                        .build())
                .collect(Collectors.toList());
        space.setAdditionalServices(additionalServices);

        return space;
    }

    public static SpaceResponseDTO.ResultDTO toSpaceResponseDTO(Space space){
        return SpaceResponseDTO.ResultDTO.builder()
                .spaceId(space.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static SpaceResponseDTO.SpaceDescriptionDTO toSpaceDescriptionDTO(Space space) {
        // 기존의 추가 서비스 변환 로직
        List<SpaceResponseDTO.SpaceAdditionalServiceDTO> additionalServices = space.getAdditionalServices().stream()
                .map(service -> new SpaceResponseDTO.SpaceAdditionalServiceDTO(service.getId(), service.getTitle(), service.getPrice()))
                .collect(Collectors.toList());

        // RentalFee 변환 로직
        List<SpaceResponseDTO.RentalFeeDTO> rentalFeeDTOs = space.getRentalFees().stream()
                .map(fee -> SpaceResponseDTO.RentalFeeDTO.builder()
                        .id(fee.getId())
                        .dayOfWeek(fee.getDayOfWeek())  // 요일
                        .fee(fee.getFee())  // 요금
                        .build())
                .collect(Collectors.toList());

        // PeakSeasonRentalFee 변환 로직
        List<SpaceResponseDTO.PeakSeasonRentalFeeDTO> peakSeasonRentalFeeDTOs = space.getPeakSeasonRentalFees().stream()
                .map(fee -> SpaceResponseDTO.PeakSeasonRentalFeeDTO.builder()
                        .id(fee.getId())
                        .dayOfWeek(fee.getDayOfWeek())  // 요일
                        .fee(fee.getFee())  // 요금
                        .build())
                .collect(Collectors.toList());

        return SpaceResponseDTO.SpaceDescriptionDTO.builder()
                .id(space.getId())
                .name(space.getName())
                .description(space.getDescription())
                .rentalHours(space.getRentalHours())
                .location(space.getLocation())
                .area(space.getArea())
                .seatingCapacity(space.getSeatingCapacity())
                .standingCapacity(space.getStandingCapacity())
                .additionalServices(additionalServices)
                .rentalFees(rentalFeeDTOs)  // RentalFee 추가
                .peakSeasonRentalFees(peakSeasonRentalFeeDTOs)  // PeakSeasonRentalFee 추가
                .build();
    }

    public static SpaceResponseDTO.SpaceNoticeDTO toSpaceNoticeDTO(Space space) {
        return SpaceResponseDTO.SpaceNoticeDTO.builder()
                .id(space.getId())
                .notice(space.getNotice())
                .build();
    }

    public static SpaceResponseDTO.SpaceFileDTO toSpaceFileDTO(Space space) {
        return SpaceResponseDTO.SpaceFileDTO.builder()
                .id(space.getId())
                .soundEquipment(space.getSoundEquipment())
                .lightingEquipment(space.getLightingEquipment())
                .stageMachinery(space.getStageMachinery())
                .spaceDrawing(space.getSpaceDrawing())
                .spaceStaff(space.getSpaceStaff())
                .spaceSeat(space.getSpaceSeat())
                .build();
    }

    public static SpaceResponseDTO.SpacePayDTO toSpacePayDTO(Space space) {
        return SpaceResponseDTO.SpacePayDTO.builder()
                .bankAccount(space.getBankAccount())
                .bankName(space.getBankName())
                .bankOwner(space.getBankOwner())
                .build();
    }

    public SpaceResponseDTO.SpaceListDTO toTopSpaceListDTO(List<Space> spacePreferList, List<Space> gradeList) {
        List<SpaceResponseDTO.SpaceSummaryDTO> spacePreferDTOList = spacePreferList.stream()
                .map(space -> toSpaceDTO(space))
                .collect(Collectors.toList());

        List<SpaceResponseDTO.SpaceSummaryDTO> gradeDTOList = gradeList.stream()
                .map(space -> toSpaceDTO(space))
                .collect(Collectors.toList());

        return new SpaceResponseDTO.SpaceListDTO(spacePreferDTOList, gradeDTOList);
    }

    public SpaceResponseDTO.SpaceFilteredListDTO toSpaceListDTO(List<Space> spaces) {
        List<SpaceResponseDTO.SpaceSummaryDTO> spaceSummaryDTOs = spaces.stream()
                .map(space -> toSpaceDTO(space))
                .collect(Collectors.toList());
        return new SpaceResponseDTO.SpaceFilteredListDTO(spaceSummaryDTOs);
    }

    public SpaceResponseDTO.SpaceFilteredListDTO toSpaceByPreferListDTO(List<SpacePrefer> spacePrefers) {
        List<SpaceResponseDTO.SpaceSummaryDTO> spaceSummaryDTOs = spacePrefers.stream()
                .map(spacePrefer -> toSpaceDTO(spacePrefer.getSpace()))
                .collect(Collectors.toList());
        return new SpaceResponseDTO.SpaceFilteredListDTO(spaceSummaryDTOs);
    }

    public SpaceResponseDTO.SpaceSummaryDTO toSpaceDTO(Space space){
        Integer seatingCapacity = Optional.ofNullable(space.getSeatingCapacity()).orElse(0);
        Integer standingCapacity = Optional.ofNullable(space.getStandingCapacity()).orElse(0);
        Integer totalCapacity = seatingCapacity + standingCapacity;
        String imageURL = space.getPhotos().isEmpty() ? null : space.getPhotos().get(0).getPhotoUrl();
        String additionalService = space.getAdditionalServices().isEmpty() ? null : space.getAdditionalServices().get(0).getTitle();

        Integer minRentalFee = space.getRentalFees().stream()
                .min(Comparator.comparingInt(RentalFee::getFee))
                .map(RentalFee::getFee)
                .orElse(null);

        return new SpaceResponseDTO.SpaceSummaryDTO(
                space.getId(),
                space.getName(),
                space.getLocation(),
                totalCapacity,
                space.getArea(),
                additionalService,
                imageURL,
                space.getGrade(),
                minRentalFee
        );
    }

    public static Space toCreateSpaceName(SpaceRequestDTO.SpaceNameDTO spaceNameDTO, SpaceUser spaceUser) {
        return Space.builder()
                .name(spaceNameDTO.getName())
                .spaceUser(spaceUser)
                .build();
    }

}
