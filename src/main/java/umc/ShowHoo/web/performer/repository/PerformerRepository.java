package umc.ShowHoo.web.performer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.ShowHoo.web.performer.entity.Performer;

public interface PerformerRepository extends JpaRepository<Performer, Long> {
}
