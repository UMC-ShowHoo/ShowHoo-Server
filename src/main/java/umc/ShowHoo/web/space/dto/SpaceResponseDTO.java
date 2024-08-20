package umc.ShowHoo.web.space.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


public class SpaceResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResultDTO{
        Long spaceId;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpaceDescriptionDTO{
        Long id;
        String name;
        String description;
        String rentalHours;
        String location;
        String area;
        Integer seatingCapacity;
        Integer standingCapacity;
        List<SpaceAdditionalServiceDTO> additionalServices;
        List<RentalFeeDTO> rentalFees;  // RentalFee 리스트 추가
        List<PeakSeasonRentalFeeDTO> peakSeasonRentalFees;  // PeakSeasonRentalFee 리스트 추가
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RentalFeeDTO {
        Long id;
        umc.ShowHoo.web.rentalFee.entity.DayOfWeek dayOfWeek;  // 요일
        Integer fee;  // 요금
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PeakSeasonRentalFeeDTO {
        Long id;
        umc.ShowHoo.web.rentalFee.entity.DayOfWeek dayOfWeek;  // 요일
        Integer fee;  // 요금
    }



    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpaceAdditionalServiceDTO {
        private Long id;
        private String title;
        private String price;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpaceNoticeDTO{
        Long id;
        String notice;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpacePriceResponseDTO {
        private Integer basePrice;
        private Integer additionalServicePrice;
        private Integer totalPrice;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpaceListDTO {
        private List<SpaceSummaryDTO> spacePreferList;
        private List<SpaceSummaryDTO> gradeList;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpaceFilteredListDTO {
        private List<SpaceSummaryDTO> spaceList;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpaceSummaryDTO{
        String name;
        String location;
        Integer totalCapacity;
        String area;
        String additionalService;
        String imageURL;
        Double grade;
        Integer minRentalFee;
        Boolean isPreferred;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SpaceFileDTO {
        Long id;
        String soundEquipment; //음향 장비 정보 - 사진
        String lightingEquipment; //조명 장비 정보 -사진
        String stageMachinery; //무대 장치 -사진
        String spaceDrawing; //공연장 도면 -사진
        String spaceStaff; //공연장 인력 가이드 -사진
        String spaceSeat; //좌석 배치도 -사진
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SpacePayDTO {
        String bankName;  //은행명
        String bankAccount; //계좌번호
        String bankOwner; //예금주

    }
}
