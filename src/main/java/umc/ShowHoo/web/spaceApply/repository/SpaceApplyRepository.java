package umc.ShowHoo.web.spaceApply.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.ShowHoo.web.spaceApply.entity.SpaceApply;

import java.util.List;

@Repository
public interface SpaceApplyRepository extends JpaRepository<SpaceApply, Long> {
    List<SpaceApply> findByPerformerId(Long performerId);

    boolean existsBySpaceIdAndPerformerId(Long spaceId, Long performerId);
}
