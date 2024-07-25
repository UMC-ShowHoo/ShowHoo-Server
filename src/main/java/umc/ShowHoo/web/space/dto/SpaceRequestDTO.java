package umc.ShowHoo.web.space.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.ShowHoo.web.rentalFee.dto.RentalFeeRequestDTO;
import umc.ShowHoo.web.spaceAdditionalService.dto.SpaceAdditionalServiceRequestDTO;
import umc.ShowHoo.web.spacePhoto.dto.SpacePhotoRequestDTO;

import java.net.URL;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SpaceRequestDTO {
    private String name;
    private String description;
    private String rentalHours;
    private String location;
    private String area;
    private Integer seatingCapacity;
    private Integer standingCapacity;
    private List<SpaceAdditionalServiceRequestDTO> additionalServices;
    private URL soundEquipment;
    private URL lightingEquipment;
    private URL stageMachinery;
    private String notice;
    private List<SpacePhotoRequestDTO> photos;
    private List<RentalFeeRequestDTO> rentalFees;
}
