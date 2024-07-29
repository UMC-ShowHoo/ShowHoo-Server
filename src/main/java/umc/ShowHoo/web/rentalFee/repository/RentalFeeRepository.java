package umc.ShowHoo.web.rentalFee.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.ShowHoo.web.rentalFee.entity.RentalFee;
import umc.ShowHoo.web.rentalFee.entity.DayOfWeek;

@Repository
public interface RentalFeeRepository extends JpaRepository<RentalFee, Long> {
    RentalFee findBySpaceIdAndDayOfWeek(Long spaceUserId, DayOfWeek dayOfWeek);
}
