package umc.ShowHoo.web.performerProfile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.ShowHoo.web.performerProfile.entity.PerformerProfile;

public interface PerformerProfileRepository extends JpaRepository<PerformerProfile, Long> {
}
