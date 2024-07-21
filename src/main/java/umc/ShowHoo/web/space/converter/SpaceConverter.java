package umc.ShowHoo.web.space.converter;

import umc.ShowHoo.web.rentalFee.entity.DayOfWeek;
import umc.ShowHoo.web.rentalFee.entity.RentalFee;
import umc.ShowHoo.web.space.dto.SpaceRequestDTO;
import umc.ShowHoo.web.space.dto.SpaceResponseDTO;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.spacePhoto.entity.SpacePhoto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class SpaceConverter {
    public static Space toEntity(SpaceRequestDTO dto) {
        Space space = Space.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .rentalHours(dto.getRentalHours())
                .location(dto.getLocation())
                .area(dto.getArea())
                .seatingCapacity(dto.getSeatingCapacity())
                .standingCapacity(dto.getStandingCapacity())
                .additionalServices(dto.getAdditionalServices())
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


}
