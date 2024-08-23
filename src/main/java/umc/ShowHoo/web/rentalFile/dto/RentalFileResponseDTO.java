package umc.ShowHoo.web.rentalFile.dto;

import lombok.*;

public class RentalFileResponseDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class postFileDTO{
        Long rentalFileId;
        Long showId;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PerformerSaveDTO{
        Long id;
        String setList;
        String rentalTime;
        String addOrder;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpaceUserSaveDTO{
        Long id;
        String setListForm;
        String rentalTimeForm;
        String addOrderForm;
    }

}
