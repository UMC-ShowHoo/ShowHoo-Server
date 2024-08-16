package umc.ShowHoo.web.rentalFile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.ShowHoo.web.rentalFile.entity.RentalFile;

@Repository
public interface RentalFileRepository extends JpaRepository<RentalFile, Long> {
}
