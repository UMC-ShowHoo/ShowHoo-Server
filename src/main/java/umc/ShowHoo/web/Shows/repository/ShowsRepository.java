package umc.ShowHoo.web.Shows.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import umc.ShowHoo.web.Shows.entity.Shows;

public interface ShowsRepository extends JpaRepository<Shows, Long> {
}
