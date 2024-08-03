package umc.ShowHoo.web.notification.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.ShowHoo.web.member.entity.Member;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private LocalDateTime createdAt;
    private NotificationType type;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
