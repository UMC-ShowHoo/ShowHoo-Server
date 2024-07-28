package umc.ShowHoo.web.Show.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URL;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShowsRequestDTO {
    private Long performerId;
    private String requirement;
    private URL poster;
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
