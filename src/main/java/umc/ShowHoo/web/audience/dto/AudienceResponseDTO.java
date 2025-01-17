package umc.ShowHoo.web.audience.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class AudienceResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getShowsListDTO{
        List<getShowsDTO> showsList;
        Integer listSize;
    }


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getShowsDTO{
        Long showsId;
        String name;
        String poster;
        String date;
        String time;
        Boolean isPreferred;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ShowsDetailDTO{
        Long showsId;
        String host;    //공연자
        String poster; //포스터 사진
        String name; //공연 이름
        String place; //공연 장소
        String description;//공연 소개
        String descriptionImg;
        String date; //공연 날짜
        String time; //공연 시간
        String cancelDate; //취소 가능 날짜
        String cancelTime; //취소 가능 시간
        String runningTime; //러닝 타임

        Integer remainTicketNum; //남은 티켓 매수
        String ticketPrice; //티켓 가격
        Integer perMaxticket; //티켓 인당 구매 제한
        String bank; //은행명
        String accountHolder; //예금주
        String accountNum; //계좌번호
    }
}
