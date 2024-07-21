package umc.ShowHoo.web.audience.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.ShowHoo.web.audience.entity.Audience;

public interface AudienceRepository extends JpaRepository<Audience,Long> {
}
