package umc.ShowHoo.web.spaceReview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.ShowHoo.web.spaceReview.entity.SpaceReviewImage;

@Repository
public interface SpaceReviewImageRepository extends JpaRepository<SpaceReviewImage, Long> {
}
