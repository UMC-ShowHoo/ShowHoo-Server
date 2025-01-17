package umc.ShowHoo.web.book.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import umc.ShowHoo.web.shows.entity.Shows;
import umc.ShowHoo.web.audience.entity.Audience;
import umc.ShowHoo.web.book.entity.Book;
import umc.ShowHoo.web.book.entity.BookDetail;
import umc.ShowHoo.web.book.entity.BookStatus;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findAllByAudienceAndStatus(Audience audience, BookStatus status, PageRequest pageRequest);

    @Query("SELECT b FROM Book b JOIN b.shows s WHERE b.audience = :audience AND b.detail = :detail ORDER BY s.date ASC")
    List<Book> findAllByAudienceAndDetailOrderByShowsDateAsc(@Param("audience") Audience audience, @Param("detail") BookDetail detail);

    List<Book> findAllByAudienceAndShows(Audience audience, Shows shows);

    List<Book> findAllByShowsAndDetail(Shows shows,BookDetail detail);

    List<Book> findAllByShows(Shows shows);
}
