package umc.ShowHoo.web.spaceApply.dto;

import lombok.*;
import umc.ShowHoo.web.performerProfile.entity.PerformerProfile;
import umc.ShowHoo.web.spacePhoto.entity.SpacePhoto;


import java.time.LocalDate;
import java.util.List;

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
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpaceApplyWitProfilesDTO {
        private Long id;
        private LocalDate date;
        private int status;
        private int audienceMin;
        private int audienceMax;
        private Integer rentalSum;
        private String spaceName;
        private String spaceLocation;
        private String title; //공연 제목
        private String poster;  //공연 포스터
    }

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpaceApplySimpleDTO {
        private LocalDate date;
        private int status;
    }



}