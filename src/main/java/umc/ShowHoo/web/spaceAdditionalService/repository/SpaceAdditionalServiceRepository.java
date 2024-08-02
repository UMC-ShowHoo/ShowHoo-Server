package umc.ShowHoo.web.spaceAdditionalService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.ShowHoo.web.spaceAdditionalService.entity.SpaceAdditionalService;

public interface SpaceAdditionalServiceRepository extends JpaRepository<SpaceAdditionalService, Long> {
        SpaceAdditionalService findBySpaceIdAndTitle(Long spaceId, String title);
}
