package umc.ShowHoo.web.Shows.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShowsRequestDTO {
    private Long performerId;
    private String requirement;
    private String name;
    private String description;
    private String date;
    private String time;
    private String runningTime;
    private Integer showAge;
    private String bank;
    private String accountHolder;
    private String accountNum;
    private String ticketPrice;
    private Integer perMaxticket;
}
