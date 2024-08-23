package umc.ShowHoo.web.peakSeasonRentalFee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.ShowHoo.web.peakSeasonRentalFee.entity.PeakSeasonRentalFee;
import umc.ShowHoo.web.rentalFee.entity.DayOfWeek;

@Repository
public interface PeakSeasonRentalFeeRepository extends JpaRepository<PeakSeasonRentalFee, Long> {
    PeakSeasonRentalFee findBySpaceIdAndDayOfWeek(Long spaceId, DayOfWeek dayOfWeek);
}
