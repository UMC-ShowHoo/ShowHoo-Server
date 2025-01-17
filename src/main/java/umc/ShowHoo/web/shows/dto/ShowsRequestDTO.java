package umc.ShowHoo.web.shows.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class ShowsRequestDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShowInfoDTO{
        private String name;
        private String posterUrl;
        private String date;
        private String time;
        private String runningTime;
        private Integer showAge;
        private String cancelDate;
        private String cancelTime;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ticketInfoDTO{
        private String bank;
        private String accountHolder;
        private String accountNum;
        private String ticketPrice;
        private Integer ticketNum;
        private Integer perMaxticket;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class requirementDTO {
        private String requirement;
    }

}
