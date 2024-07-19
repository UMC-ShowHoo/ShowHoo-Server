package umc.ShowHoo.web.rentalFee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RentalFeeRequestDTO {
    private String dayOfWeek;
    private Integer fee;
}
