package umc.ShowHoo.web.showsDescription.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.ShowHoo.web.showsDescription.entity.ShowsDescription;

@Repository
public interface ShowsDscRepository extends JpaRepository<ShowsDescription, Long> {
}
