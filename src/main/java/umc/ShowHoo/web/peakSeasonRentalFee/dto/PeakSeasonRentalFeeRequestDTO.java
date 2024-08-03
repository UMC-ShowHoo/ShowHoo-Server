package umc.ShowHoo.web.peakSeasonRentalFee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PeakSeasonRentalFeeRequestDTO {
    private String dayOfWeek;
    private Integer fee;
}
