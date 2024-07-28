package umc.ShowHoo.web.space.dto;

import lombok.*;
import org.hibernate.type.internal.ImmutableNamedBasicTypeImpl;

import java.net.URL;
import java.time.LocalDate;
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
        String name;
        String description;
        String rentalHours;
        String location;
        String area;
        Integer seatingCapacity;
        Integer standingCapacity;
        String additionalServices;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpaceNoticeDTO{
        String notice;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpacePriceDTO {
        private Integer basePrice;
        private Integer additionalServicePrice;
        private Integer totalPrice;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpaceListDTO {
        private List<SpaceSummaryDTO> spaces;
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
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SpaceFileDTO {
        String soundEquipment; //음향 장비 정보 - 사진
        String lightingEquipment; //조명 장비 정보 -사진
        String stageMachinery; //무대 장치 -사진
        String spaceDrawing; //공연장 도면 -사진
        String spaceStaff; //공연장 인력 가이드 -사진
        String spaceSeat; //좌석 배치도 -사진
    }
}
