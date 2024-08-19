package umc.ShowHoo.web.spaceApply.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.ShowHoo.web.spaceApply.entity.SpaceApply;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SpaceApplyRepository extends JpaRepository<SpaceApply, Long> {
    List<SpaceApply> findByPerformerId(Long performerId);
    List<SpaceApply> findBySpaceIdAndStatusIn(Long spaceId, List<Integer> statuses);
    Optional<SpaceApply> findBySpaceIdAndDate(Long spaceId, LocalDate date);
    boolean existsBySpaceIdAndPerformerId(Long spaceId, Long performerId);
}
