package umc.ShowHoo.web.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.ShowHoo.web.book.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
