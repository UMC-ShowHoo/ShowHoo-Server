package umc.ShowHoo.web.spaceUser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.ShowHoo.web.member.entity.Member;
import umc.ShowHoo.web.spaceUser.entity.SpaceUser;

import java.util.Optional;

public interface SpaceUserRepository extends JpaRepository<SpaceUser, Long> {
    Optional<SpaceUser> findById(Long id);
}
