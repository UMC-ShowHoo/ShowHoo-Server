package umc.ShowHoo.web.spaceUser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import umc.ShowHoo.web.spaceUser.entity.SpaceUser;

import java.util.Optional;

@Repository
public interface SpaceUserRepository extends JpaRepository<SpaceUser, Long> {
    Optional<SpaceUser> findById(Long id);
    Optional<SpaceUser> findByMemberId(Long memberId);

    @Query("SELECT s.member.id FROM SpaceUser s WHERE s.id = :id")
    Optional<Long> findMemberIdById(Long id);
}
