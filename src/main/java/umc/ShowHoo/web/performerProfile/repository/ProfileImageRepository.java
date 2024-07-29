package umc.ShowHoo.web.performerProfile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.ShowHoo.web.performerProfile.entity.PerformerProfile;
import umc.ShowHoo.web.performerProfile.entity.ProfileImage;

import java.util.Optional;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
    Optional<ProfileImage> findByIdAndPerformerProfile(Long id, PerformerProfile performerProfile);

    Optional<ProfileImage> findByProfileImageUrl(String profileImageUrl);
}
