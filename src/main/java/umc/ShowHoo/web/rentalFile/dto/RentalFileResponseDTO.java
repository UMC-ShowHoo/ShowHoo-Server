package umc.ShowHoo.web.rentalFile.dto;

import lombok.*;

public class RentalFileResponseDTO {

    @Getter
    @Setter
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
    @Setter
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
