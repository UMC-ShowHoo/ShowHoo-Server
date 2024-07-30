package umc.ShowHoo.web.book.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.ShowHoo.web.audience.entity.Audience;
import umc.ShowHoo.web.book.entity.Book;
import umc.ShowHoo.web.book.entity.BookStatus;

public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findAllByAudience(Audience audience, PageRequest pageRequest);
    Page<Book> findAllByAudienceAndStatus(Audience audience, BookStatus status, PageRequest pageRequest);
}
