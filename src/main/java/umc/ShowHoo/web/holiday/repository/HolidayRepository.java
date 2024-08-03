package umc.ShowHoo.web.holiday.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.ShowHoo.web.holiday.entity.Holiday;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {
}
