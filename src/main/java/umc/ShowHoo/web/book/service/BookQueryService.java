package umc.ShowHoo.web.book.service;

import org.springframework.data.domain.Page;
import umc.ShowHoo.web.book.entity.Book;

public interface BookQueryService {

    Page<Book> getTickets(Long audienceId, Integer page);

    Page<Book> getWatchedTickets(Long audienceId, Integer page);

    Book getNextBook(Long audienceId);
}
