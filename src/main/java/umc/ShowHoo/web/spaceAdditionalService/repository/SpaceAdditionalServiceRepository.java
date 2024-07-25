package umc.ShowHoo.web.spaceAdditionalService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.spaceAdditionalService.entity.SpaceAdditionalService;

import java.util.Optional;

public interface SpaceAdditionalServiceRepository extends JpaRepository<SpaceAdditionalService, Long> {
    Optional<SpaceAdditionalService> findBySpaceAndIsSelected(Space space, Boolean isSelected);
}
