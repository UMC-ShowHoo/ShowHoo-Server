package umc.ShowHoo.web.spaceApply.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import umc.ShowHoo.web.holiday.dto.HolidayDTO;
import umc.ShowHoo.web.holiday.entity.Holiday;
import umc.ShowHoo.web.holiday.repository.HolidayRepository;
import umc.ShowHoo.web.notification.service.NotificationService;
import umc.ShowHoo.web.performer.entity.Performer;
import umc.ShowHoo.web.performer.repository.PerformerRepository;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SpaceApplyService {

    private final SpaceApplyRepository spaceApplyRepository;
    private final PerformerRepository performerRepository;
    private final SpaceRepository spaceRepository;
    private final SelectedAdditionalServiceRepository selectedAdditionalServiceRepository;
    private final SpaceApplyConverter spaceApplyConverter;
    private final NotificationService notificationService;
    private final HolidayRepository holidayRepository;


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

        notificationService.createSpaceApplyNotification(spaceUserId, registerDTO); // 알림 생성

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

        notificationService.createSpaceCancleNotification(spaceApply); // 알림 생성
    }

    @Transactional
    public void setSpaceApply(Long spaceId, Long spaceApplyId) {
        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new SpaceApplyHandler(ErrorStatus.SPACE_NOT_FOUND));

        SpaceApply spaceApply = spaceApplyRepository.findById(spaceApplyId)
                .orElseThrow(() -> new SpaceApplyHandler(ErrorStatus.SPACE_NOT_FOUND));
        spaceApply.setStatus(1);

        notificationService.createSpaceConfirmNotification(spaceApply); // 알림 생성
    }
    @Transactional
    public List<Object> getSpaceApplyInfo(Long spaceId) {
        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new SpaceApplyHandler(ErrorStatus.SPACE_NOT_FOUND));

        List<Object> spaceInfo = new ArrayList<>();

        List<SpaceApply> spaceApplies = spaceApplyRepository.findBySpaceIdAndStatusIn(spaceId, Arrays.asList(0,1));
        List<SpaceApplyResponseDTO.SpaceApplySimpleDTO> spaceApplySimpleDTOS = spaceApplies.stream()
                .map(spaceApply -> new SpaceApplyResponseDTO.SpaceApplySimpleDTO(spaceApply.getDate(), spaceApply.getStatus()))
                .collect(Collectors.toList());
        spaceInfo.add(spaceApplySimpleDTOS);

        List<Holiday> holidays = holidayRepository.findBySpaceId(spaceId);
        List<HolidayDTO> holidayDTOS = holidays.stream()
                .map(holiday -> new HolidayDTO(holiday.getDate()))
                .collect(Collectors.toList());
        spaceInfo.add(holidayDTOS);

        return spaceInfo;
    }
}
