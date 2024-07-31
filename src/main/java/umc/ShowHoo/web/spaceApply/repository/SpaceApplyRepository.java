package umc.ShowHoo.web.spaceApply.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.ShowHoo.web.spaceApply.entity.SpaceApply;

import java.util.List;

public interface SpaceApplyRepository extends JpaRepository<SpaceApply, Long> {
    List<SpaceApply> findByPerformerId(Long performerId);
}
