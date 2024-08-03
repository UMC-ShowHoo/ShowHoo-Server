package umc.ShowHoo.web.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.ShowHoo.web.notification.entity.Notification;
import umc.ShowHoo.web.notification.entity.NotificationType;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByMemberIdAndType(Long memberId, NotificationType type);
}
