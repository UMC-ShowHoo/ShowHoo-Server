package umc.ShowHoo.web.notification.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import umc.ShowHoo.web.member.entity.Member;
import umc.ShowHoo.web.member.handler.MemberHandler;
import umc.ShowHoo.web.member.repository.MemberRepository;
import umc.ShowHoo.web.notification.dto.NotificationRequestDTO;
import umc.ShowHoo.web.notification.dto.NotificationResponseDTO;
import umc.ShowHoo.web.notification.entity.Notification;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class NotificationConverter {
    private final MemberRepository memberRepository;

    public Notification toEntity(NotificationRequestDTO.createNotificationDTO dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        return Notification.builder()
                .member(member)
                .message(dto.getMessage())
                .createdAt(LocalDateTime.now())
                .type(dto.getType())
                .build();
    }

    public NotificationResponseDTO.NotificationDTO toDTO(Notification notification) {
        return NotificationResponseDTO.NotificationDTO.builder()
                .id(notification.getId())
                .memberId(notification.getMember().getId())
                .message(notification.getMessage())
                .createdAt(notification.getCreatedAt())
                .type(notification.getType())
                .build();
    }

}
