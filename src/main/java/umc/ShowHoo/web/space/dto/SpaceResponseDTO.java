package umc.ShowHoo.web.space.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.time.LocalDateTime;


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


}
