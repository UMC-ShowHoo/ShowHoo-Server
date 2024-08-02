package umc.ShowHoo.web.spaceApply.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.ShowHoo.web.rentalFee.entity.DayOfWeek;
import umc.ShowHoo.web.rentalFee.entity.RentalFee;
import umc.ShowHoo.web.spaceApply.entity.SpaceApply;

import java.util.List;
import java.util.Optional;

public interface SpaceApplyRepository extends JpaRepository<SpaceApply, Long> {


    List<SpaceApply> findByPerformer(long performanceId);

    Optional<SpaceApply> findBySpaceAndPerformerId(Long spaceId, Long performerId);
}
