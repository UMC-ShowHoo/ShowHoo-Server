package umc.ShowHoo.web.cancelBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.ShowHoo.web.book.entity.Book;
import umc.ShowHoo.web.cancelBook.entity.CancelBook;

import java.util.List;

public interface CancelBookRepository extends JpaRepository<CancelBook, Long> {
    List<CancelBook> findAllByBookIn(List<Book> bookList);
}
