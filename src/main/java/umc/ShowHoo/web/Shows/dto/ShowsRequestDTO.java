package umc.ShowHoo.web.Shows.dto;

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
        private Long performerId;
        //private String poster;
        private String name;
        private String date;
        private String time;
        private String runningTime;
        private Integer showAge;
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
