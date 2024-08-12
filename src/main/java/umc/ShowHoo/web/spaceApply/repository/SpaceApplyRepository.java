package umc.ShowHoo.web.spaceApply.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import umc.ShowHoo.web.spaceApply.dto.SpaceApplyResponseDTO;
import umc.ShowHoo.web.spaceApply.entity.SpaceApply;

import java.util.List;

@Repository
public interface SpaceApplyRepository extends JpaRepository<SpaceApply, Long> {
    List<SpaceApply> findByPerformerId(Long performerId);
    List<SpaceApply> findBySpaceIdAndStatusIn(Long spaceId, List<Integer> statuses);
    boolean existsBySpaceIdAndPerformerId(Long spaceId, Long performerId);
}
