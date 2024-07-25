package umc.ShowHoo.web.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
}
