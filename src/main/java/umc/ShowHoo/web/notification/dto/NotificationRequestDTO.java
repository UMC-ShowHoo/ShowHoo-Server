package umc.ShowHoo.web.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.ShowHoo.web.notification.entity.NotificationType;

public class NotificationRequestDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class createNotificationDTO {
        private Long memberId;
        private String message;
        private NotificationType type;
    }
}
