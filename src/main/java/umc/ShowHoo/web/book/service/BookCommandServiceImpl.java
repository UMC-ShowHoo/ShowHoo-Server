package umc.ShowHoo.web.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import umc.ShowHoo.apiPayload.exception.handler.AudienceHandler;
import umc.ShowHoo.web.Shows.entity.Shows;
import umc.ShowHoo.web.Shows.handler.ShowsHandler;
import umc.ShowHoo.web.Shows.repository.ShowsRepository;
import umc.ShowHoo.web.audience.entity.Audience;
import umc.ShowHoo.web.audience.repository.AudienceRepository;
import umc.ShowHoo.web.book.converter.BookConverter;
import umc.ShowHoo.web.book.dto.BookRequestDTO;
import umc.ShowHoo.web.book.entity.Book;
import umc.ShowHoo.web.book.repository.BookRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookCommandServiceImpl implements BookCommandService {

    private final BookRepository bookRepository;

    private final AudienceRepository audienceRepository;

    private final ShowsRepository showsRepository;

    public Book postBook(BookRequestDTO.postDTO request) {
        Audience audience = audienceRepository.findById(request.getAudienceId())
                .orElseThrow(()-> new AudienceHandler(ErrorStatus.AUDIENCE_NOT_FOUND));

        Shows shows = showsRepository.findById(request.getShowsId())
                .orElseThrow(()->new ShowsHandler(ErrorStatus.SHOW_NOT_FOUND));

        //동일한 audience & show에 대한 예매 내역이 존재하는지 확인
        List<Book> bookList = bookRepository.findAllByAudienceAndShows(audience, shows);

        //(기존 예매 내역의 ticketNum의 합산 + request의 ticketNum) > PerMaxTicket일 경우, "예매 가능 매수 초과" 메세지 전달
        if (!bookList.isEmpty()) {
            Integer num = bookList.stream()
                    .mapToInt(Book::getTicketNum)
                    .sum();

            if( num + request.getTicketNum() > shows.getPerMaxticket()){
                return null;
            }

        }

        return bookRepository.save(BookConverter.toBook(audience, shows, request));
    }
}
