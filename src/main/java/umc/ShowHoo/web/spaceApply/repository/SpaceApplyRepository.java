package umc.ShowHoo.web.spaceApply.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.ShowHoo.web.performer.entity.Performer;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.spaceApply.entity.SpaceApply;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SpaceApplyRepository extends JpaRepository<SpaceApply, Long> {
    List<SpaceApply> findByPerformerId(Long performerId);
    List<SpaceApply> findBySpaceId(Long spaceId);


    Optional<SpaceApply> findBySpaceAndPerformer(Space space, Performer performer);

    List<SpaceApply> findBySpaceIdAndStatusIn(Long spaceId, List<Integer> statuses);
    List<SpaceApply> findBySpaceIdAndDate(Long spaceId, LocalDate date);

    boolean existsBySpaceIdAndPerformerId(Long spaceId, Long performerId);
}
