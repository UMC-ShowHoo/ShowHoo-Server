package umc.ShowHoo.web.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import umc.ShowHoo.apiPayload.exception.handler.AudienceHandler;
import umc.ShowHoo.web.audience.entity.Audience;
import umc.ShowHoo.web.audience.repository.AudienceRepository;
import umc.ShowHoo.web.book.converter.BookConverter;
import umc.ShowHoo.web.book.dto.BookRequestDTO;
import umc.ShowHoo.web.book.entity.Book;
import umc.ShowHoo.web.book.repository.BookRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class BookCommandServiceImpl implements BookCommandService {

    private final BookRepository bookRepository;

    private final AudienceRepository audienceRepository;

    public Book postBook(BookRequestDTO.postDTO request) {
        Audience audience = audienceRepository.findById(request.getAudienceId())
                .orElseThrow(()-> new AudienceHandler(ErrorStatus.AUDIENCE_NOT_FOUND));

        return bookRepository.save(BookConverter.toBook(audience));
    }

    public Book requestConfirm(Long bookId){
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()->new BookHandler(ErrorStatus.BOOK_NOT_FOUND));

        if(book.getDetail() != BookDetail.CONFIRMING){
            return null;
        }

        book.setDetail(BookDetail.CONFIRMED);
        return bookRepository.save(book);
    }

    public Book requestCanceled(Long bookId){
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()->new BookHandler(ErrorStatus.BOOK_NOT_FOUND));

        if(book.getDetail() != BookDetail.CANCELLING){
            return null;
        }

        book.setStatus(BookStatus.CANCEL);
        book.setDetail(BookDetail.CANCELED);
        return bookRepository.save(book);
    }

    public Book requestWatched(Long bookId){
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()->new BookHandler(ErrorStatus.BOOK_NOT_FOUND));

        if(book.getDetail() != BookDetail.CONFIRMED){
            return null;
        }

        book.setStatus(BookStatus.WATCHED);
        book.setDetail(BookDetail.WATCHED);
        return bookRepository.save(book);
    }
}
