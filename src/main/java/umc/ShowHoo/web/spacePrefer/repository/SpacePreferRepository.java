package umc.ShowHoo.web.spacePrefer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.ShowHoo.web.performer.entity.Performer;
import umc.ShowHoo.web.spacePrefer.entity.SpacePrefer;

import java.util.List;

public interface SpacePreferRepository extends JpaRepository<SpacePrefer, Long> {
    List<SpacePrefer> findByPerformer(Performer performer);
}
