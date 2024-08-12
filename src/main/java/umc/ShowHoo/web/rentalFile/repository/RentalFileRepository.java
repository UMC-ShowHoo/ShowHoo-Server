package umc.ShowHoo.web.rentalFile.repository;

import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.ShowHoo.web.rentalFile.entity.RentalFile;
import umc.ShowHoo.web.shows.entity.Shows;

@Repository
public interface RentalFileRepository extends JpaRepository<RentalFile, Long> {

    RentalFile findByShows(Shows shows);
}
