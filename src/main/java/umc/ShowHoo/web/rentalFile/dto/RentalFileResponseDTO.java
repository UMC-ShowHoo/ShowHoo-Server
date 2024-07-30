package umc.ShowHoo.web.rentalFile.dto;

import lombok.*;

public class RentalFileResponseDTO {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SaveRentalFileDTO{
        Long id;
        String setListForm;
        String rentalTimeForm;
        String addOrderForm;

        String setList;
        String rentalTime;
        String addOrder;
    }

}
