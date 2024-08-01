package umc.ShowHoo.web.spaceReviewAnswer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.ShowHoo.web.spaceReviewAnswer.entity.SpaceReviewAnswer;

@Repository
public interface SpaceReviewAnswerRepository extends JpaRepository<SpaceReviewAnswer, Long> {
}
