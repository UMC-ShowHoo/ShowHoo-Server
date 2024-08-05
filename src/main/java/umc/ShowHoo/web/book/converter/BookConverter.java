package umc.ShowHoo.web.book.converter;

import org.springframework.data.domain.Page;
import umc.ShowHoo.web.Shows.entity.Shows;
import umc.ShowHoo.web.audience.entity.Audience;
import umc.ShowHoo.web.book.dto.BookRequestDTO;
import umc.ShowHoo.web.book.dto.BookResponseDTO;
import umc.ShowHoo.web.book.entity.Book;
import umc.ShowHoo.web.cancelBook.entity.CancelBook;

import java.util.List;

public class BookConverter {

    public static Book toBook(Audience audience, Shows shows, BookRequestDTO.postDTO request){
        int payment = request.getTicketNum() * Integer.parseInt(shows.getTicketPrice());

        return Book.builder()
                .audience(audience)
                .shows(shows)
                .name(request.getName())
                .phoneNum(request.getPhoneNum())
                .ticketNum(request.getTicketNum())
                .payment(Integer.toString(payment))
                .build();
    }


    public static CancelBook toCancelBook(Book book, BookRequestDTO.deleteBookDTO request){
        return CancelBook.builder()
                .name(request.getName())
                .bankName(request.getBankName())
                .account(request.getAccount())
                .reason(request.getReason())
                .book(book)
                .performer(book.getShows().getPerformer())
                .build();
    }

    public static BookResponseDTO.postBookDTO toPostBookDTO(Book book){
        return BookResponseDTO.postBookDTO.builder()
                .book_id(book.getId())
                .showsId(book.getShows().getId())
                .audienceId(book.getAudience().getId())
                .payment(book.getPayment())
                .alert("예매가 완료되었습니다!")
                .build();
    }

    public static BookResponseDTO.deleteResponseDTO toDeleteBookDTO(CancelBook cancelBook){
        return BookResponseDTO.deleteResponseDTO.builder()
                .bookId(cancelBook.getBook().getId())
                .cancelBookId(cancelBook.getId())
                .alert("예매가 취소되었습니다.")
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
        if(book == null){
            return BookResponseDTO.getBookDTO.builder()
                    .name("다음 공연이 없습니다.")
                    .build();
        }

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

    public static BookResponseDTO.bookInfoDTO toBookInfoDTO(Book book){
            return BookResponseDTO.bookInfoDTO.builder()
                    .book_id(book.getId())
                    .name(book.getName())
                    .phoneNum(book.getPhoneNum())
                    .ticketNum(book.getTicketNum())
                    .payment(book.getPayment())
                    .dateTime(book.getCreatedAt())
                    .build();

    }

    public static BookResponseDTO.refundBookDTO toRefundDTO(CancelBook cancelBook){
        return BookResponseDTO.refundBookDTO.builder()
                .book_id(cancelBook.getId())
                .name(cancelBook.getName())
                .bankName(cancelBook.getBankName())
                .account(cancelBook.getAccount())
                .dateTime(cancelBook.getCreatedAt())
                .build();
    }


}
