package umc.ShowHoo.web.space.dto;

import lombok.*;
import org.hibernate.type.internal.ImmutableNamedBasicTypeImpl;

import java.net.URL;
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
    public static class SpaceDateDTO {
        String date;

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
        URL imageURL;
        Double grade;
        Integer minRentalFee;
    }

}
