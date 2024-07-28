package umc.ShowHoo.web.Show.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import umc.ShowHoo.web.Show.entity.Shows;

public interface ShowsRepository extends JpaRepository<Shows, Long> {
}
