package umc.ShowHoo.web.selectedAdditionalService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.ShowHoo.web.selectedAdditionalService.entity.SelectedAdditionalService;

@Repository
public interface SelectedAdditionalServiceRepository extends JpaRepository<SelectedAdditionalService, Long> {
}
