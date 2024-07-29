package umc.ShowHoo.web.Shows.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.ShowHoo.web.Shows.entity.Shows;

@Repository
public interface ShowsRepository extends JpaRepository<Shows, Long> {
}
