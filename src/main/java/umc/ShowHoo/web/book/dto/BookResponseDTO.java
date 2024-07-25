package umc.ShowHoo.web.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.ShowHoo.web.book.entity.BookStatus;

public class BookResponseDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class postBookDTO {
        Long book_id;
        BookStatus status;
        String alert;
    }

}
