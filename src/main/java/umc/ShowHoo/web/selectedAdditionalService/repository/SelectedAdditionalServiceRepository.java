package umc.ShowHoo.web.selectedAdditionalService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.ShowHoo.web.selectedAdditionalService.entity.SelectedAdditionalService;
import umc.ShowHoo.web.spaceApply.entity.SpaceApply;

import java.util.List;

@Repository
public interface SelectedAdditionalServiceRepository extends JpaRepository<SelectedAdditionalService, Long> {
    List<SelectedAdditionalService> findBySpaceApply(SpaceApply spaceApply);
}
