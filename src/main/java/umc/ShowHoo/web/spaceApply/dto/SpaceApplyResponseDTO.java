package umc.ShowHoo.web.spaceApply.dto;

import lombok.*;
import umc.ShowHoo.web.performerProfile.entity.PerformerProfile;
import umc.ShowHoo.web.selectedAdditionalService.dto.SelectedAdditionalDTO;
import umc.ShowHoo.web.selectedAdditionalService.entity.SelectedAdditionalService;
import umc.ShowHoo.web.shows.dto.ShowsResponseDTO;
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
        private List<ShowsResponseDTO.ShowTitleAndPosterDTO> shows;
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

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReceiptDTO{
        private LocalDate date;
        private int rentalFee;
        private int rentalSum;
        private List<SelectedAdditionalDTO> selected;
    }




}