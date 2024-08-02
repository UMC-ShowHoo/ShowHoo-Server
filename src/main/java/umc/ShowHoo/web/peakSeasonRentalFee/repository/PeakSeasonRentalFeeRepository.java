package umc.ShowHoo.web.peakSeasonRentalFee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.ShowHoo.web.peakSeasonRentalFee.entity.PeakSeasonRentalFee;

@Repository
public interface PeakSeasonRentalFeeRepository extends JpaRepository<PeakSeasonRentalFee, Long> {
}
