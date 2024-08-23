package umc.ShowHoo.web.showsPrefer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.ShowHoo.web.shows.entity.Shows;
import umc.ShowHoo.web.audience.entity.Audience;
import umc.ShowHoo.web.showsPrefer.entity.ShowsPrefer;

import java.util.Optional;

public interface ShowsPreferRepository extends JpaRepository<ShowsPrefer, Long> {
    Optional<ShowsPrefer> findByAudienceAndShows(Audience audience, Shows shows);

    Page<ShowsPrefer> findAllByAudience(Audience audience, PageRequest pageRequest);

    boolean existsByAudienceAndShows(Audience audience, Shows shows);
}
