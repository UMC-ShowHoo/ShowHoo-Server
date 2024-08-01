package umc.ShowHoo.web.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.ShowHoo.web.book.entity.BookDetail;
import umc.ShowHoo.web.book.entity.BookStatus;

import java.net.URL;
import java.util.List;

public class BookResponseDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class postBookDTO {
        Long book_id;
        Long showsId;
        Long audienceId;
        String alert;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getBookListDTO {
        List<getBookDTO> getBookList;
        Integer listSize;
        Integer totalPages;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getBookDTO{
        //공연 예매확인 조회
        //공연 정보(ID, 포스터, 이름, 장소 및 시간, 공연자 등)
        Long showsId;
        URL poster;
        String name;
        String date;
        String time;
        String place;
        String performer;
        BookStatus status;
        BookDetail detail;
        Boolean isCancellable; //취소 가능한지 여부
    }

}
