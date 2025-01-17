package umc.ShowHoo.web.space.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import umc.ShowHoo.web.peakSeasonRentalFee.dto.PeakSeasonRentalFeeRequestDTO;
import umc.ShowHoo.web.rentalFee.dto.RentalFeeRequestDTO;
import umc.ShowHoo.web.spaceAdditionalService.dto.SpaceAdditionalServiceRequestDTO;
import umc.ShowHoo.web.space.entity.SpaceType;
import umc.ShowHoo.web.spacePhoto.dto.SpacePhotoRequestDTO;

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
        private String notice;
        private String bankName;
        private String bankAccount;
        private String bankOwner;
        private List<String> photoUrls;
        private List<LocalDate> holidays;
        private List<RentalFeeRequestDTO> rentalFees;
        private List<PeakSeasonRentalFeeRequestDTO> peakSeasonRentalFees;
        private List<SpaceAdditionalServiceRequestDTO> additionalServices;
        private SpaceType spaceType; // spaceType 추가
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

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpaceSearchRequestDTO {
        private String name;
        private String city;
        private String district;
        private LocalDate date;
        private SpaceType type;
        private Integer minPrice;
        private Integer maxPrice;
        private Integer minCapacity;
        private Integer maxCapacity;
        private int page = 0;
        private int size = 12;
    }

}
