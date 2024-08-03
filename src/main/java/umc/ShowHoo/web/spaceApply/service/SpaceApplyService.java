package umc.ShowHoo.web.spaceApply.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import umc.ShowHoo.web.member.handler.MemberHandler;
import umc.ShowHoo.web.notification.dto.NotificationRequestDTO;
import umc.ShowHoo.web.notification.entity.NotificationType;
import umc.ShowHoo.web.notification.service.NotificationService;
import umc.ShowHoo.web.performer.entity.Performer;
import umc.ShowHoo.web.performer.handler.PerformerHandler;
import umc.ShowHoo.web.performer.repository.PerformerRepository;
import umc.ShowHoo.web.performerProfile.entity.PerformerProfile;
import umc.ShowHoo.web.performerProfile.repository.PerformerProfileRepository;
import umc.ShowHoo.web.selectedAdditionalService.entity.SelectedAdditionalService;
import umc.ShowHoo.web.selectedAdditionalService.repository.SelectedAdditionalServiceRepository;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.space.repository.SpaceRepository;
import umc.ShowHoo.web.spaceApply.converter.SpaceApplyConverter;
import umc.ShowHoo.web.spaceApply.dto.SpaceApplyRequestDTO;
import umc.ShowHoo.web.spaceApply.dto.SpaceApplyResponseDTO;
import umc.ShowHoo.web.spaceApply.entity.SpaceApply;
import umc.ShowHoo.web.spaceApply.exception.handler.SpaceApplyHandler;
import umc.ShowHoo.web.spaceApply.repository.SpaceApplyRepository;
import umc.ShowHoo.web.spaceUser.repository.SpaceUserRepository;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SpaceApplyService {

    private final SpaceApplyRepository spaceApplyRepository;
    private final PerformerRepository performerRepository;
    private final SpaceRepository spaceRepository;
    private final PerformerProfileRepository performerProfileRepository;
    private final SelectedAdditionalServiceRepository selectedAdditionalServiceRepository;
    private final SpaceApplyConverter spaceApplyConverter;
    private final NotificationService notificationService;
    private final SpaceUserRepository spaceUserRepository;


    public SpaceApply createSpaceApply(Long spaceUserId, Long performerId, SpaceApplyRequestDTO.RegisterDTO registerDTO) {
        Performer performer = performerRepository.findById(performerId)
                .orElseThrow(() -> new SpaceApplyHandler(ErrorStatus.SPACE_APPLY_NOT_FOUND));

        Space space = spaceRepository.findById(spaceUserId)
                .orElseThrow(() -> new SpaceApplyHandler(ErrorStatus.SPACE_NOT_FOUND));

        SpaceApply spaceApply = spaceApplyConverter.toCreateSpaceApply(registerDTO, performer, space);

        spaceApplyRepository.save(spaceApply);

        for (Long serviceId : registerDTO.getSelectedAdditionalServices()) {
            SelectedAdditionalService additionalService = SelectedAdditionalService.builder()
                    .serviceId(serviceId)
                    .spaceApply(spaceApply)
                    .build();
            selectedAdditionalServiceRepository.save(additionalService);
        }

        // 알림 생성
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

        notificationService.createNotification(notificationRequest);

        return spaceApply;
    }

    @Transactional
    public List<SpaceApplyResponseDTO.SpaceApplyDetailDTO> getSpaceAppliesByPerformerId(Long performerId) {
        List<SpaceApply> spaceApplies = spaceApplyRepository.findByPerformerId(performerId);
        return spaceApplies.stream()
                .map(spaceApplyConverter::toGetSpaceApply)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteSpaceApply(Long spaceApplyId) {
        SpaceApply spaceApply = spaceApplyRepository.findById(spaceApplyId)
                .orElseThrow(() -> new SpaceApplyHandler(ErrorStatus.SPACE_APPLY_NOT_FOUND));
        spaceApplyRepository.delete(spaceApply);
    }
}
