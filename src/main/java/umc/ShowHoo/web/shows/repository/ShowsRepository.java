package umc.ShowHoo.web.shows.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.ShowHoo.web.shows.entity.Shows;

@Repository
public interface ShowsRepository extends JpaRepository<Shows, Long> {
    Page<Shows> findByNameContaining(String request, PageRequest pageRequest);
}