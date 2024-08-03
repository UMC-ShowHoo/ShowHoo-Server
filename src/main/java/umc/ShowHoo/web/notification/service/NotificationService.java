package umc.ShowHoo.web.notification.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import umc.ShowHoo.apiPayload.exception.handler.AudienceHandler;
import umc.ShowHoo.web.audience.repository.AudienceRepository;
import umc.ShowHoo.web.member.handler.MemberHandler;
import umc.ShowHoo.web.notification.converter.NotificationConverter;
import umc.ShowHoo.web.notification.dto.NotificationRequestDTO;
import umc.ShowHoo.web.notification.dto.NotificationResponseDTO;
import umc.ShowHoo.web.notification.entity.Notification;
import umc.ShowHoo.web.notification.entity.NotificationType;
import umc.ShowHoo.web.notification.repository.NotificationRepository;
import umc.ShowHoo.web.performer.entity.Performer;
import umc.ShowHoo.web.performer.handler.PerformerHandler;
import umc.ShowHoo.web.performer.repository.PerformerRepository;
import umc.ShowHoo.web.performerProfile.entity.PerformerProfile;
import umc.ShowHoo.web.performerProfile.repository.PerformerProfileRepository;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.spaceApply.dto.SpaceApplyRequestDTO;
import umc.ShowHoo.web.spaceReview.dto.SpaceReviewRequestDTO;
import umc.ShowHoo.web.spaceUser.handler.SpaceUserHandler;
import umc.ShowHoo.web.spaceUser.repository.SpaceUserRepository;

import java.time.format.DateTimeFormatter;
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
    private final PerformerProfileRepository performerProfileRepository;

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

    @Transactional
    public void createApplyNotification(Long spaceUserId, SpaceApplyRequestDTO.RegisterDTO registerDTO){
        // spaceUser의 memberId 가져오기
        Long memberId = spaceUserRepository.findMemberIdById(spaceUserId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        // LocalDate type -> string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = registerDTO.getDate().format(formatter);
        // profile 받아오기
        PerformerProfile perfomerProfile = performerProfileRepository.findById(registerDTO.getPerformerProfileId())
                .orElseThrow(() -> new PerformerHandler(ErrorStatus.PERFORMER_PROFILE_NOT_FOUND));
        // 알림 메시지
        String message = String.format("%s이 %s로 대관 신청을 했습니다", perfomerProfile.getName(), date);

        NotificationRequestDTO.createNotificationDTO notificationRequest = NotificationRequestDTO.createNotificationDTO.builder()
                .memberId(memberId)
                .message(message)
                .type(NotificationType.SPACEUSER)
                .build();

        createNotification(notificationRequest);
    }
}
