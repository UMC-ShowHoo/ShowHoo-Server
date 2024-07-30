package umc.ShowHoo.web.book.converter;

import org.springframework.data.domain.Page;
import umc.ShowHoo.web.audience.entity.Audience;
import umc.ShowHoo.web.book.dto.BookResponseDTO;
import umc.ShowHoo.web.book.entity.Book;

import java.util.List;

public class BookConverter {

    public static Book toBook(Audience audience){
        return Book.builder()
                .audience(audience)
                .build();
    }

    public static BookResponseDTO.postBookDTO toPostBookDTO(Book book){
        return BookResponseDTO.postBookDTO.builder()
                .book_id(book.getId())
                .status(book.getStatus())
                .alert("예매가 완료되었습니다!")
                .build();
    }

    public static BookResponseDTO.getBookListDTO toGetBookListDTO(Page<Book> bookList){
        List<BookResponseDTO.getBookDTO> getBookDTOList = bookList.stream()
                .map(BookConverter::toGetBookDTO).toList();

        return BookResponseDTO.getBookListDTO.builder()
                .isFirst(bookList.isFirst())
                .isLast(bookList.isLast())
                .totalPages(bookList.getTotalPages())
                .totalElements(bookList.getTotalElements())
                .listSize(getBookDTOList.size())
                .getBookList(getBookDTOList)
                .build();
    }

    public static BookResponseDTO.getBookDTO toGetBookDTO(Book book){
        return BookResponseDTO.getBookDTO.builder()
                .showsId(book.getShows().getId())
                .poster(book.getShows().getPoster())
                .name(book.getShows().getName())
                .date(book.getShows().getDate())
                .time(book.getShows().getTime())
                .place("test")
                .performer(book.getShows().getPerformer().getMember().getName())
                .status(book.getStatus())
                .detail(book.getDetail())
                .isCancellable(false)
                .build();
    }

}
