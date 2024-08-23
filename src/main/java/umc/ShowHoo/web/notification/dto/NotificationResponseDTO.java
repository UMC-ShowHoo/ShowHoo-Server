package umc.ShowHoo.web.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.ShowHoo.web.notification.entity.NotificationType;

import java.time.LocalDateTime;

public class NotificationResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NotificationDTO {
        private Long id;
        private Long memberId;
        private String message;
        private LocalDateTime createdAt;
        private NotificationType type;
    }
}
