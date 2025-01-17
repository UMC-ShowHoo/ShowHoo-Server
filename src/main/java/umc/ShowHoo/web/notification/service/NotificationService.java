package umc.ShowHoo.web.notification.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import umc.ShowHoo.web.audience.handler.AudienceHandler;
import umc.ShowHoo.web.audience.repository.AudienceRepository;
import umc.ShowHoo.web.book.entity.Book;
import umc.ShowHoo.web.member.handler.MemberHandler;
import umc.ShowHoo.web.notification.converter.NotificationConverter;
import umc.ShowHoo.web.notification.dto.NotificationRequestDTO;
import umc.ShowHoo.web.notification.dto.NotificationResponseDTO;
import umc.ShowHoo.web.notification.entity.Notification;
import umc.ShowHoo.web.notification.entity.NotificationType;
import umc.ShowHoo.web.notification.handler.NotificationHandler;
import umc.ShowHoo.web.notification.repository.NotificationRepository;
import umc.ShowHoo.web.performer.entity.Performer;
import umc.ShowHoo.web.performer.handler.PerformerHandler;
import umc.ShowHoo.web.performer.repository.PerformerRepository;
import umc.ShowHoo.web.performerProfile.entity.PerformerProfile;
import umc.ShowHoo.web.performerProfile.repository.PerformerProfileRepository;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.spaceApply.dto.SpaceApplyRequestDTO;
import umc.ShowHoo.web.spaceApply.entity.SpaceApply;
import umc.ShowHoo.web.spaceReview.entity.SpaceReview;
import umc.ShowHoo.web.spaceUser.handler.SpaceUserHandler;
import umc.ShowHoo.web.spaceUser.repository.SpaceUserRepository;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
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
            case PERFORMER -> performerRepository.findByMemberId(memberId)
                    .orElseThrow(() -> new PerformerHandler(ErrorStatus.PERFORMER_NOT_FOUND));
            case SPACEUSER -> spaceUserRepository.findByMemberId(memberId)
                    .orElseThrow(() -> new SpaceUserHandler(ErrorStatus.SPACEUSER_NOT_FOUND));
            case AUDIENCE -> audienceRepository.findByMemberId(memberId)
                    .orElseThrow(() -> new AudienceHandler(ErrorStatus.PERFORMER_NOT_FOUND));
            default -> throw new NotificationHandler(ErrorStatus.NOTIFICATION_TYPE_NOT_FOUND);
        }
        List<Notification> notifications = notificationRepository.findByMemberIdAndType(memberId, type);
        return notifications.stream()
                .map(notificationConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Integer getNotificationCount(Long memberId, NotificationType type) {
        switch (type) {
            case PERFORMER -> performerRepository.findByMemberId(memberId)
                    .orElseThrow(() -> new PerformerHandler(ErrorStatus.PERFORMER_NOT_FOUND));
            case SPACEUSER -> spaceUserRepository.findByMemberId(memberId)
                    .orElseThrow(() -> new SpaceUserHandler(ErrorStatus.SPACEUSER_NOT_FOUND));
            case AUDIENCE -> audienceRepository.findByMemberId(memberId)
                    .orElseThrow(() -> new AudienceHandler(ErrorStatus.PERFORMER_NOT_FOUND));
            default -> throw new NotificationHandler(ErrorStatus.NOTIFICATION_TYPE_NOT_FOUND);
        }
        return notificationRepository.countByMemberIdAndType(memberId, type);
    }

    @Transactional
    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    // 공연장 알림 - 공연장 대관 요청
    @Transactional
    public void createSpaceApplyNotification(Space space, SpaceApplyRequestDTO.RegisterDTO registerDTO){
        // spaceUser의 memberId 가져오기
        Long memberId = spaceUserRepository.findMemberIdById(space.getSpaceUser().getId())
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        // LocalDate type -> string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = registerDTO.getDate().format(formatter);
        // profile 받아오기
        PerformerProfile perfomerProfile = performerProfileRepository.findById(registerDTO.getPerformerProfileId())
                .orElseThrow(() -> new PerformerHandler(ErrorStatus.PERFORMER_PROFILE_NOT_FOUND));
        // 알림 메시지
        String message = String.format("%s이 %s로 대관 신청을 했습니다", perfomerProfile.getName(), date);

        NotificationRequestDTO.createNotificationDTO notification = notificationConverter.toCreateDTO(memberId, message, NotificationType.SPACEUSER);

        createNotification(notification);
    }

    @Transactional
    public void createSpaceConfirmNotification(SpaceApply spaceApply){
        // spaceUser의 memberId 가져오기
        Long memberId = Optional.ofNullable(spaceApply.getPerformer().getMember().getId())
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        // 알림 메시지
        String message = String.format("%s이 회원님의 대관 신청을 승인하였습니다. 이제 공연 준비를 시작해보세요", spaceApply.getSpace().getName());

        NotificationRequestDTO.createNotificationDTO notification = notificationConverter.toCreateDTO(memberId, message, NotificationType.PERFORMER);

        createNotification(notification);
    }

    @Transactional
    public void createSpaceCancelNotification(SpaceApply spaceApply){
        // spaceUser의 memberId 가져오기
        Long memberId = Optional.ofNullable(spaceApply.getPerformer().getMember().getId())
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        // 알림 메시지
        String message = String.format("%s의 대관 신청이 거절 되었습니다. 자세한 사항은 해당 공연장에 문의 바랍니다.", spaceApply.getSpace().getName());

        NotificationRequestDTO.createNotificationDTO notification = notificationConverter.toCreateDTO(memberId, message, NotificationType.PERFORMER);

        createNotification(notification);
    }

    // 공연장 알림 - 공연장에 새로운 후기
    @Transactional
    public void createSpaceReviewNotification(Space space, Performer performer){
        // spaceUser의 memberId 가져오기
        Long memberId = spaceUserRepository.findMemberIdById(space.getSpaceUser().getId())
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        // 알림 메시지
        String message = String.format("%s님이 회원님의 공연장에 이용 후기를 남겼습니다.", performer.getMember().getName());

        NotificationRequestDTO.createNotificationDTO notification = notificationConverter.toCreateDTO(memberId, message, NotificationType.SPACEUSER);

        createNotification(notification);
    }

    // 공연자 알림 - 공연장 후기에 새로운 댓글
    @Transactional
    public void createSpaceReviewCommentNotification(SpaceReview spaceReview){
        // spaceUser의 memberId 가져오기
        Long memberId = Optional.ofNullable(spaceReview.getPerformer().getMember().getId())
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        // 알림 메시지
        String message = String.format("%s이 회원님의 후기에 댓글을 남겼습니다.", spaceReview.getSpace().getName());

        NotificationRequestDTO.createNotificationDTO notification = notificationConverter.toCreateDTO(memberId, message, NotificationType.PERFORMER);

        createNotification(notification);
    }

    // 관람자 알림 - 공연 승인
    @Transactional
    public void createBookConfirmNotification(Book book){
        // spaceUser의 memberId 가져오기
        Long memberId = Optional.ofNullable(book.getAudience().getId())
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        // 알림 메시지
        String message = String.format("%s이 회원님의 %s티켓을 승인하였습니다.", book.getShows().getPerformerProfile().getName(),book.getShows().getName());

        NotificationRequestDTO.createNotificationDTO notification = notificationConverter.toCreateDTO(memberId, message, NotificationType.AUDIENCE);

        createNotification(notification);
    }

    // 공연자 알림 - 공연 취소
    @Transactional
    public void createBookCancelNotification(Book book){
        // spaceUser의 memberId 가져오기
        Long memberId = Optional.ofNullable(book.getShows().getPerformerProfile().getPerformer().getMember().getId())
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        // 알림 메시지
        String message = String.format("%s님이 %s티켓을 환불 요청을 하셨습니다.", book.getAudience().getMember().getName(),book.getShows().getName());

        NotificationRequestDTO.createNotificationDTO notification = notificationConverter.toCreateDTO(memberId, message, NotificationType.PERFORMER);

        createNotification(notification);
    }
}
