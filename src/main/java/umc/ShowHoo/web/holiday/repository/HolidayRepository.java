package umc.ShowHoo.web.holiday.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.ShowHoo.web.holiday.dto.HolidayDTO;
import umc.ShowHoo.web.holiday.entity.Holiday;

import java.util.List;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {
    List<HolidayDTO> findBySpaceId(Long spaceId);
}
