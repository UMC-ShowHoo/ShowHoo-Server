package umc.ShowHoo.web.audience.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.ShowHoo.web.audience.entity.Audience;

import java.util.Optional;

@Repository
public interface AudienceRepository extends JpaRepository<Audience,Long> {
    Optional<Audience> findByMemberId(Long memberId);
}
