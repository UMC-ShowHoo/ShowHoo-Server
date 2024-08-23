package umc.ShowHoo.web.space.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.space.entity.SpaceType;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Long> {
    @Query("SELECT s FROM Space s LEFT JOIN s.spacePrefers sp GROUP BY s.id ORDER BY COUNT(sp.id) DESC")
    List<Space> findTopBySpacePreferOrderByCountDesc(Pageable pageable);
    @Query("SELECT s FROM Space s ORDER BY s.grade DESC")
    List<Space> findTopByOrderByGradeDesc(Pageable pageable);

    @Query("SELECT s FROM Space s WHERE " +
            "(:name IS NULL OR s.name LIKE %:name%) AND " +
            "(:city IS NULL OR s.location LIKE %:city%) AND " +
            "(:district IS NULL OR s.location LIKE %:district%) AND " +
            "NOT EXISTS (SELECT 1 FROM SpaceApply sa WHERE sa.space.id = s.id AND sa.date = :date AND sa.status = 1) AND " +
            "(:type IS NULL OR s.spaceType = :type) AND " +
            "(:minPrice IS NULL OR (SELECT MIN(rf.fee) FROM RentalFee rf WHERE rf.space = s) >= :minPrice) AND " +
            "(:maxPrice IS NULL OR (SELECT MAX(rf.fee) FROM RentalFee rf WHERE rf.space = s) <= :maxPrice) AND " +
            "(:minCapacity IS NULL OR (s.seatingCapacity + s.standingCapacity) >= :minCapacity) AND " +
            "(:maxCapacity IS NULL OR (s.seatingCapacity + s.standingCapacity) <= :maxCapacity)")
    List<Space> searchSpaces(String name, String city, String district, LocalDate date, SpaceType type, Integer minPrice, Integer maxPrice, Integer minCapacity, Integer maxCapacity, Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(sp) > 0 THEN TRUE ELSE FALSE END FROM SpacePrefer sp WHERE sp.space.id = :spaceId AND sp.performer.id = :performerId")
    Boolean isSpacePreferredByUser(Long spaceId, Long performerId);
}
