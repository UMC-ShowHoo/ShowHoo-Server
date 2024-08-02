package umc.ShowHoo.web.cancelBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.ShowHoo.web.cancelBook.entity.CancelBook;

public interface CancelBookRepository extends JpaRepository<CancelBook, Long> {
}
