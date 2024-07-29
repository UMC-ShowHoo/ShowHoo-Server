package umc.ShowHoo.web.performerProfile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.ShowHoo.web.performer.entity.Performer;
import umc.ShowHoo.web.performerProfile.entity.PerformerProfile;

import java.util.List;
import java.util.Optional;

public interface PerformerProfileRepository extends JpaRepository<PerformerProfile, Long> {
    Optional<PerformerProfile> findByIdAndPerformer(Long id, Performer performer);
    List<PerformerProfile> findByPerformer(Performer performer);
}
