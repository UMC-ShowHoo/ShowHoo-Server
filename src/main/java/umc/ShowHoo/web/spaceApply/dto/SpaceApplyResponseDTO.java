package umc.ShowHoo.web.spaceApply.dto;

import lombok.*;

import java.time.LocalDate;

public class SpaceApplyResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpaceApplyDetailDTO {
        private Long id;
        private LocalDate date;
        private int status;
        private int audienceMin;
        private int audienceMax;
        private Integer rentalSum;
        private String spaceName;
        private String spaceLocation;
        private String spacePhotoUrl;
    }
    @Builder
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpaceApplySimpleDTO {
        private LocalDate date;
        private int status;
    }
}
