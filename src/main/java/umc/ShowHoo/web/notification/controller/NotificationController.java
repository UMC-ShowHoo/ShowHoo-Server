package umc.ShowHoo.web.notification.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.ShowHoo.apiPayload.ApiResponse;
import umc.ShowHoo.web.notification.dto.NotificationRequestDTO;
import umc.ShowHoo.web.notification.dto.NotificationResponseDTO;
import umc.ShowHoo.web.notification.entity.NotificationType;
import umc.ShowHoo.web.notification.service.NotificationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping
    @Operation(summary = "알림 생성 API", description = "알림을 생성할 때 필요한 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<NotificationResponseDTO.NotificationDTO> createNotification(@RequestBody NotificationRequestDTO.createNotificationDTO request) {
        NotificationResponseDTO.NotificationDTO notification = notificationService.createNotification(request);
        return ApiResponse.onSuccess(notification);
    }

    @GetMapping("/{type}/{memberId}")
    @Operation(summary = "알림 목록 조회 API", description = "알림 목록을 조회할 때 필요한 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<List<NotificationResponseDTO.NotificationDTO>> getNotifications(@PathVariable Long memberId, @PathVariable NotificationType type) {
        List<NotificationResponseDTO.NotificationDTO> notificationDTOS = notificationService.getNotifications(memberId, type);
        return ApiResponse.onSuccess(notificationDTOS);
    }

    @GetMapping("/count/{type}/{memberId}")
    @Operation(summary = "알림 개수 조회 API", description = "알림 목록 개수를 조회할 때 필요한 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<Integer> getNotificationCount(@PathVariable Long memberId, @PathVariable NotificationType type) {
        Integer count = notificationService.getNotificationCount(memberId, type);
        return ApiResponse.onSuccess(count);
    }

    @DeleteMapping("/{notificationId}")
    @Operation(summary = "알림 삭제 API", description = "알림을 삭제할 때 필요한 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OK, 성공"),
    })
    public ApiResponse<Void> deleteNotification(@PathVariable Long notificationId) {
        notificationService.deleteNotification(notificationId);
        return ApiResponse.onSuccess(null);
    }
}
