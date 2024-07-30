package umc.ShowHoo.web.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import umc.ShowHoo.apiPayload.exception.handler.AudienceHandler;
import umc.ShowHoo.web.audience.entity.Audience;
import umc.ShowHoo.web.audience.repository.AudienceRepository;
import umc.ShowHoo.web.book.entity.Book;
import umc.ShowHoo.web.book.entity.BookStatus;
import umc.ShowHoo.web.book.repository.BookRepository;

@Service
@RequiredArgsConstructor
public class BookQueryServiceImpl implements BookQueryService{

    private final AudienceRepository audienceRepository;

    private final BookRepository bookRepository;

    @Override
    public Page<Book> getTickets(Long audienceId, Integer page){
        Audience audience = audienceRepository.findById(audienceId)
                .orElseThrow(()->new AudienceHandler(ErrorStatus.AUDIENCE_NOT_FOUND));
        return bookRepository.findAllByAudienceAndStatus(audience, BookStatus.BOOK, PageRequest.of(page, 3));
    }

    @Override
    public Page<Book> getWatchedTickets(Long audienceId, Integer page){
        Audience audience = audienceRepository.findById(audienceId)
                .orElseThrow(()->new AudienceHandler(ErrorStatus.AUDIENCE_NOT_FOUND));
        return bookRepository.findAllByAudienceAndStatus(audience, BookStatus.WATCHED, PageRequest.of(page, 3));
    }

    //detail >> CONFIRMED 중 하나
    @Override
    public Book getNextBook(Long audienceId){
        return null;
    }
}
