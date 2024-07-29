package umc.ShowHoo.web.space.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import umc.ShowHoo.web.rentalFee.dto.RentalFeeRequestDTO;
import umc.ShowHoo.web.spaceAdditionalService.dto.SpaceAdditionalServiceRequestDTO;
import umc.ShowHoo.web.spacePhoto.dto.SpacePhotoRequestDTO;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;


public class SpaceRequestDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpaceRegisterRequestDTO {
        private String name;
        private String description;
        private String rentalHours;
        private String location;
        private String area;
        private Integer seatingCapacity;
        private Integer standingCapacity;
//        private MultipartFile soundEquipment;
//        private MultipartFile lightingEquipment;
//        private MultipartFile stageMachinery;
//        private MultipartFile spaceDrawing;
//        private MultipartFile spaceStaff;
//        private MultipartFile spaceSeat;
        private String notice;
        private Double grade;
//        private List<MultipartFile> photos;
        private List<RentalFeeRequestDTO> rentalFees;
        private List<SpaceAdditionalServiceRequestDTO> additionalServices;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpacePriceDTO {
        LocalDate date;
        private List<String> additionalServices;
    }


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpaceNameDTO {
        private String name;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpaceLocationDTO {
        private String location;
    }

}
