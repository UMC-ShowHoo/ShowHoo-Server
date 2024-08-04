package umc.ShowHoo.web.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import umc.ShowHoo.web.shows.entity.Shows;
import umc.ShowHoo.web.shows.handler.ShowsHandler;
import umc.ShowHoo.web.shows.repository.ShowsRepository;
import umc.ShowHoo.web.book.converter.BookConverter;
import umc.ShowHoo.web.book.dto.BookResponseDTO;
import umc.ShowHoo.web.book.entity.Book;
import umc.ShowHoo.web.book.entity.BookDetail;
import umc.ShowHoo.web.book.repository.BookRepository;
import umc.ShowHoo.web.cancelBook.entity.CancelBook;
import umc.ShowHoo.web.cancelBook.repository.CancelBookRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookAdminService {
    private final ShowsRepository showsRepository;
    private final BookRepository bookRepository;
    private final CancelBookRepository cancelBookRepository;

    public List<BookResponseDTO.bookInfoDTO> getBookInfoList(Long showsId, BookDetail detail){
        Shows shows=showsRepository.findById(showsId)
                .orElseThrow(()->new ShowsHandler(ErrorStatus.SHOW_NOT_FOUND));
        List<Book> bookList=bookRepository.findAllByShowsAndDetail(shows,detail);
        return bookList.stream()
                .map(BookConverter::toBookInfoDTO)
                .collect(Collectors.toList());
    }

    public List<BookResponseDTO.refundBookDTO> getRefundBookList(Long showsId){
        Shows shows=showsRepository.findById(showsId)
                .orElseThrow(()->new ShowsHandler(ErrorStatus.SHOW_NOT_FOUND));
        List<Book> bookList=bookRepository.findAllByShowsAndDetail(shows,BookDetail.CANCELLING);

        List<CancelBook> cancelBookList=cancelBookRepository.findAllByBookIn(bookList);

        return cancelBookList.stream()
                .map(BookConverter::toRefundDTO)
                .collect(Collectors.toList());
    }


}
