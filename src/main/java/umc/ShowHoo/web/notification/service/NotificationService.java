package umc.ShowHoo.web.notification.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import umc.ShowHoo.apiPayload.exception.handler.AudienceHandler;
import umc.ShowHoo.web.audience.repository.AudienceRepository;
import umc.ShowHoo.web.notification.converter.NotificationConverter;
import umc.ShowHoo.web.notification.dto.NotificationRequestDTO;
import umc.ShowHoo.web.notification.dto.NotificationResponseDTO;
import umc.ShowHoo.web.notification.entity.Notification;
import umc.ShowHoo.web.notification.entity.NotificationType;
import umc.ShowHoo.web.notification.repository.NotificationRepository;
import umc.ShowHoo.web.performer.handler.PerformerHandler;
import umc.ShowHoo.web.performer.repository.PerformerRepository;
import umc.ShowHoo.web.spaceUser.handler.SpaceUserHandler;
import umc.ShowHoo.web.spaceUser.repository.SpaceUserRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationConverter notificationConverter;
    private final PerformerRepository performerRepository;
    private final SpaceUserRepository spaceUserRepository;
    private final AudienceRepository audienceRepository;

    @Transactional
    public NotificationResponseDTO.NotificationDTO createNotification(NotificationRequestDTO.createNotificationDTO request){
        Notification notification = notificationConverter.toEntity(request);
        Notification savedNotification = notificationRepository.save(notification);
        return notificationConverter.toDTO(savedNotification);
    }

    @Transactional
    public List<NotificationResponseDTO.NotificationDTO> getNotifications(Long memberId, NotificationType type) {
        switch (type) {
            case PERFORMER:
                performerRepository.findByMemberId(memberId)
                        .orElseThrow(() -> new PerformerHandler(ErrorStatus.PERFORMER_NOT_FOUND));
                break;
            case SPACEUSER:
                spaceUserRepository.findByMemberId(memberId)
                        .orElseThrow(() -> new SpaceUserHandler(ErrorStatus.SPACEUSER_NOT_FOUND));
                break;
            case AUDIENCE:
                audienceRepository.findByMemberId(memberId)
                        .orElseThrow(() -> new AudienceHandler(ErrorStatus.PERFORMER_NOT_FOUND));
                break;
        }
        List<Notification> notifications = notificationRepository.findByMemberIdAndType(memberId, type);
        return notifications.stream()
                .map(notificationConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }
}
