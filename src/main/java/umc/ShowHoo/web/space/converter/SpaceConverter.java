package umc.ShowHoo.web.space.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import umc.ShowHoo.web.rentalFee.entity.DayOfWeek;
import umc.ShowHoo.web.rentalFee.entity.RentalFee;
import umc.ShowHoo.web.space.dto.SpaceRequestDTO;
import umc.ShowHoo.web.space.dto.SpaceResponseDTO;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.spaceAdditionalService.entity.SpaceAdditionalService;
import umc.ShowHoo.web.spaceAdditionalService.repository.SpaceAdditionalServiceRepository;
import umc.ShowHoo.web.spacePhoto.entity.SpacePhoto;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SpaceConverter {

    private static SpaceAdditionalServiceRepository spaceAdditionalServiceRepository;

    @Autowired
    public SpaceConverter(SpaceAdditionalServiceRepository spaceAdditionalServiceRepository) {
        this.spaceAdditionalServiceRepository = spaceAdditionalServiceRepository;
    }

    public static Space toEntity(SpaceRequestDTO dto) {
        Space space = Space.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .rentalHours(dto.getRentalHours())
                .location(dto.getLocation())
                .area(dto.getArea())
                .seatingCapacity(dto.getSeatingCapacity())
                .standingCapacity(dto.getStandingCapacity())
                .soundEquipment(dto.getSoundEquipment())
                .lightingEquipment(dto.getLightingEquipment())
                .stageMachinery(dto.getStageMachinery())
                .notice(dto.getNotice())
                .build();

        List<SpacePhoto> photos = dto.getPhotos().stream()
                .map(photoDTO -> SpacePhoto.builder()
                        .photoUrl(photoDTO.getPhotoUrl())
                        .space(space)
                        .build())
                .collect(Collectors.toList());

        List<RentalFee> rentalFees = dto.getRentalFees().stream()
                .map(feeDTO -> RentalFee.builder()
                        .dayOfWeek(DayOfWeek.valueOf(feeDTO.getDayOfWeek()))
                        .fee(feeDTO.getFee())
                        .space(space)
                        .build())
                .collect(Collectors.toList());

        space.setPhotos(photos);
        space.setRentalFees(rentalFees);

        return space;
    }

    public static SpaceResponseDTO.ResultDTO toSpaceResponseDTO(Space space){
        return SpaceResponseDTO.ResultDTO.builder()
                .spaceId(space.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static SpaceResponseDTO.SpaceDescriptionDTO toSpaceDescriptionDTO(Space space) {
        return SpaceResponseDTO.SpaceDescriptionDTO.builder()
                .name(space.getName())
                .description(space.getDescription())
                .rentalHours(space.getRentalHours())
                .location(space.getLocation())
                .area(space.getArea())
                .seatingCapacity(space.getSeatingCapacity())
                .standingCapacity(space.getStandingCapacity())
//                .additionalServices(space.getAdditionalServices())
                .build();
    }

    public static SpaceResponseDTO.SpaceNoticeDTO toSpaceNoticeDTO(Space space) {
        return SpaceResponseDTO.SpaceNoticeDTO.builder()
                .notice(space.getNotice())
                .build();
    }

    public static SpaceResponseDTO.SpaceListDTO toSpaceListDTO(List<Space> spaces) {
        List<SpaceResponseDTO.SpaceSummaryDTO> spaceSummaryDTOs = spaces.stream()
                .map(SpaceConverter::toSpaceDTO)
                .collect(Collectors.toList());
        return new SpaceResponseDTO.SpaceListDTO(spaceSummaryDTOs);
    }

    public static SpaceResponseDTO.SpaceSummaryDTO toSpaceDTO(Space space){
        Integer totalCapacity = space.getSeatingCapacity() + space.getStandingCapacity();
        URL imageURL = space.getPhotos().isEmpty() ? null : space.getPhotos().get(0).getPhotoUrl();

        Integer minRentalFee = space.getRentalFees().stream()
                .min(Comparator.comparingInt(RentalFee::getFee))
                .map(RentalFee::getFee)
                .orElse(null);

        String additionalService = null;
        SpaceAdditionalService selectedService = spaceAdditionalServiceRepository
                .findBySpaceAndIsSelected(space, true)
                .orElse(null);
        if (selectedService != null) { additionalService = selectedService.getTitle(); }

        return new SpaceResponseDTO.SpaceSummaryDTO(
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
}
