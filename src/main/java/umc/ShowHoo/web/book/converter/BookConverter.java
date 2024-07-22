package umc.ShowHoo.web.book.converter;

import umc.ShowHoo.web.audience.entity.Audience;
import umc.ShowHoo.web.book.dto.BookRequestDTO;
import umc.ShowHoo.web.book.dto.BookResponseDTO;
import umc.ShowHoo.web.book.entity.Book;
import umc.ShowHoo.web.book.entity.BookStatus;

public class BookConverter {

    public static Book toBook(Audience audience){
        return Book.builder()
                .audience(audience)
                .build();
    }

    public static BookResponseDTO.postBookDTO toPostBookDTO(Book book){
        return BookResponseDTO.postBookDTO.builder()
                .book_id(book.getId())
                .status(BookStatus.CONFIRMING)
                .alert("예매가 완료되었습니다!")
                .build();
    }

}
