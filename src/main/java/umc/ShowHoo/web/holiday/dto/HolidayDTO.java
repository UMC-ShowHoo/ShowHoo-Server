package umc.ShowHoo.web.holiday.dto;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class HolidayDTO {
    private LocalDate date;;
}
