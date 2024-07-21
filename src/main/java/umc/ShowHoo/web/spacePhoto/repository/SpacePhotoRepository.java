package umc.ShowHoo.web.spacePhoto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.ShowHoo.web.spacePhoto.entity.SpacePhoto;

public interface SpacePhotoRepository extends JpaRepository<SpacePhoto, Long> {
}
