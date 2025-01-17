package umc.ShowHoo.web.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.ShowHoo.web.book.entity.Book;
import umc.ShowHoo.web.book.entity.BookDetail;
import umc.ShowHoo.web.book.entity.BookStatus;

import java.time.LocalDateTime;
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
        String payment;
        String alert;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class bookInfoDTO{
        Long book_id;
        String name;
        String phoneNum;
        Integer ticketNum;
        String payment;
        BookDetail detail;
        //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime dateTime;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getEntranceListDTO {
        List<getEntranceDTO> entranceList;
        Integer listSize;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getEntranceDTO{
        Long bookId;
        String name;
        String phoneNum;
        Integer headCount; //인원 수
        Boolean entrance; //입장 여부
        BookDetail detail; //예매 상태
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
        Long bookId;
        //공연 정보(ID, 포스터, 이름, 장소 및 시간, 공연자 등)
        Long showsId;
        String poster;
        String name;
        String date;
        String time;
        String place;
        String performer;
        BookStatus status;
        BookDetail detail;
        Boolean isCancellable; //취소 가능한지 여부
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class deleteResponseDTO{
        String alert;
        Long bookId;
        Long cancelBookId;
        //바로 삭제하지 않고 상태를 우선 취소로 바꿈
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class refundBookDTO{
        Long book_id;
        String name;
        String bankName;
        String account;
        BookDetail bookDetail;
        LocalDateTime dateTime;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class changeStatusResponseDTO{
        Long bookId;
        BookStatus status;
        BookDetail detail;
        String alert;
    }

}
