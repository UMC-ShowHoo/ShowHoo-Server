package umc.ShowHoo.web.Shows.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ShowsResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShowinfoDTO{
        Long shows_id;
        String poster;
        String name;
        String description;
        String date;
        String time;
        String runningTime;
        Integer showAge;
        String ticketPrice;
        Integer perMaxticket;
        String bank;
        String accountHolder;
        String accountNum;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShowRequirementDTO{
        String requirement;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShowPosterDTO{
        Long id;
        String poster; //공연 포스터
    }

}
