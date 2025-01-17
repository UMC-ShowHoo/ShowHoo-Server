package umc.ShowHoo.web.performer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import umc.ShowHoo.web.member.entity.Member;
import umc.ShowHoo.web.performer.entity.Performer;

import java.util.Optional;

@Repository
public interface PerformerRepository extends JpaRepository<Performer, Long> {
    public Performer findById(long id);
    Optional<Performer> findByMemberId(Long memberId);

    @Query("SELECT p.member FROM Performer p WHERE p.id = :id")
    Optional<Member> findMemberIdById(Long id);
}
