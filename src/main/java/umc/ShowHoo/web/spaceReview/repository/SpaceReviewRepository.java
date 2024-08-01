package umc.ShowHoo.web.spaceReview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.ShowHoo.web.spaceReview.entity.SpaceReview;

import java.util.List;

@Repository
public interface SpaceReviewRepository extends JpaRepository<SpaceReview, Long> {

    List<SpaceReview> findByPerformerId(Long performerId);
}
