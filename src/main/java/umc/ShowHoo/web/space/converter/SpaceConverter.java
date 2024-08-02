package umc.ShowHoo.web.space.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.ShowHoo.web.rentalFee.entity.DayOfWeek;
import umc.ShowHoo.web.rentalFee.entity.RentalFee;
import umc.ShowHoo.web.space.dto.SpaceRequestDTO;
import umc.ShowHoo.web.space.dto.SpaceResponseDTO;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.space.repository.SpaceRepository;
import umc.ShowHoo.web.spaceAdditionalService.entity.SpaceAdditionalService;
import umc.ShowHoo.web.spaceAdditionalService.repository.SpaceAdditionalServiceRepository;
import umc.ShowHoo.web.spacePhoto.entity.SpacePhoto;
import umc.ShowHoo.web.spaceUser.entity.SpaceUser;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SpaceConverter {
    private final SpaceRepository spaceRepository;

    public static Space toEntity(SpaceRequestDTO.SpaceRegisterRequestDTO dto, String soundEquipmentUrl, String lightingEquipmentUrl, String stageMachineryUrl, String spaceDrawingUrl, String spaceStaffUrl, String spaceSeatUrl, List<String> photoUrls, SpaceUser spaceUser) {
        Space space = Space.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .rentalHours(dto.getRentalHours())
                .location(dto.getLocation())
                .area(dto.getArea())
                .seatingCapacity(dto.getSeatingCapacity())
                .standingCapacity(dto.getStandingCapacity())
                .soundEquipment(soundEquipmentUrl)
                .lightingEquipment(lightingEquipmentUrl)
                .stageMachinery(stageMachineryUrl)
                .spaceDrawing(spaceDrawingUrl)
                .spaceStaff(spaceStaffUrl)
                .spaceSeat(spaceSeatUrl)
                .notice(dto.getNotice())
                .grade(dto.getGrade())
                .spaceUser(spaceUser)
                .build();

        List<SpacePhoto> photos = photoUrls.stream()
                .map(url -> SpacePhoto.builder().photoUrl(url).space(space).build())
                .collect(Collectors.toList());
        space.setPhotos(photos);

        List<RentalFee> rentalFees = dto.getRentalFees().stream()
                .map(feeDTO -> RentalFee.builder()
                        .dayOfWeek(DayOfWeek.valueOf(feeDTO.getDayOfWeek()))
                        .fee(feeDTO.getFee())
                        .space(space)
                        .build())
                .collect(Collectors.toList());
        space.setRentalFees(rentalFees);

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
        List<SpaceResponseDTO.SpaceAdditionalServiceDTO> additionalServices = space.getAdditionalServices().stream()
                .map(service -> new SpaceResponseDTO.SpaceAdditionalServiceDTO(service.getId(), service.getTitle(), service.getPrice()))
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

    public SpaceResponseDTO.SpaceListDTO toTopSpaceListDTO(List<Space> spacePreferList, List<Space> gradeList, Long performerId) {
        List<SpaceResponseDTO.SpaceSummaryDTO> spacePreferDTOList = spacePreferList.stream()
                .map(space -> toSpaceDTOWithPreference(space, performerId))
                .collect(Collectors.toList());

        List<SpaceResponseDTO.SpaceSummaryDTO> gradeDTOList = gradeList.stream()
                .map(space -> toSpaceDTOWithPreference(space, performerId))
                .collect(Collectors.toList());

        return new SpaceResponseDTO.SpaceListDTO(spacePreferDTOList, gradeDTOList);
    }

    public SpaceResponseDTO.SpaceFilteredListDTO toSpaceListDTO(List<Space> spaces, Long performerId) {
        List<SpaceResponseDTO.SpaceSummaryDTO> spaceSummaryDTOs = spaces.stream()
                .map(space -> toSpaceDTOWithPreference(space, performerId))
                .collect(Collectors.toList());
        return new SpaceResponseDTO.SpaceFilteredListDTO(spaceSummaryDTOs);
    }

    public SpaceResponseDTO.SpaceSummaryDTO toSpaceDTO(Space space){
        Integer totalCapacity = space.getSeatingCapacity() + space.getStandingCapacity();
        String imageURL = space.getPhotos().isEmpty() ? null : space.getPhotos().get(0).getPhotoUrl();
        String additionalService = space.getPhotos().isEmpty() ? null : space.getAdditionalServices().get(0).getTitle();

        Integer minRentalFee = space.getRentalFees().stream()
                .min(Comparator.comparingInt(RentalFee::getFee))
                .map(RentalFee::getFee)
                .orElse(null);

        return new SpaceResponseDTO.SpaceSummaryDTO(
                space.getName(),
                space.getLocation(),
                totalCapacity,
                space.getArea(),
                additionalService,
                imageURL,
                space.getGrade(),
                minRentalFee,
                null
        );
    }

    public static Space toCreateSpaceName(SpaceRequestDTO.SpaceNameDTO spaceNameDTO, SpaceUser spaceUser) {
        return Space.builder()
                .name(spaceNameDTO.getName())
                .spaceUser(spaceUser)
                .build();
    }


    public SpaceResponseDTO.SpaceSummaryDTO toSpaceDTOWithPreference(Space space, Long performerId) {
        SpaceResponseDTO.SpaceSummaryDTO dto = toSpaceDTO(space);
        Boolean isPreferred = spaceRepository.isSpacePreferredByUser(space.getId(), performerId);
        dto.setIsPreferred(isPreferred);
        return dto;
    }
}
