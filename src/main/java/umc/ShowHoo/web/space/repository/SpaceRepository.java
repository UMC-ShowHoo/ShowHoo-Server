package umc.ShowHoo.web.space.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.ShowHoo.web.space.entity.Space;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Long> {
}
