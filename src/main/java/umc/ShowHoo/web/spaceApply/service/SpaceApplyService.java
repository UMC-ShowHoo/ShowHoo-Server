package umc.ShowHoo.web.spaceApply.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import umc.ShowHoo.web.holiday.dto.HolidayDTO;
import umc.ShowHoo.web.holiday.entity.Holiday;
import umc.ShowHoo.web.holiday.repository.HolidayRepository;
import umc.ShowHoo.web.notification.service.NotificationService;
import umc.ShowHoo.web.performer.entity.Performer;
import umc.ShowHoo.web.performer.handler.PerformerHandler;
import umc.ShowHoo.web.performer.repository.PerformerRepository;
import umc.ShowHoo.web.performerProfile.dto.PerformerProfileRequestDTO;
import umc.ShowHoo.web.performerProfile.entity.PerformerProfile;
import umc.ShowHoo.web.performerProfile.entity.ProfileImage;
import umc.ShowHoo.web.performerProfile.repository.PerformerProfileRepository;
import umc.ShowHoo.web.rentalFee.service.RentalFeeService;
import umc.ShowHoo.web.selectedAdditionalService.entity.SelectedAdditionalService;
import umc.ShowHoo.web.selectedAdditionalService.repository.SelectedAdditionalServiceRepository;
import umc.ShowHoo.web.shows.entity.Shows;
import umc.ShowHoo.web.shows.repository.ShowsRepository;
import umc.ShowHoo.web.space.dto.SpaceResponseDTO;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.space.exception.handler.SpaceHandler;
import umc.ShowHoo.web.space.repository.SpaceRepository;
import umc.ShowHoo.web.spaceApply.converter.SpaceApplyConverter;
import umc.ShowHoo.web.spaceApply.dto.SpaceApplyRequestDTO;
import umc.ShowHoo.web.spaceApply.dto.SpaceApplyResponseDTO;
import umc.ShowHoo.web.spaceApply.entity.SpaceApply;
import umc.ShowHoo.web.spaceApply.exception.handler.SpaceApplyHandler;
import umc.ShowHoo.web.spaceApply.repository.SpaceApplyRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.*;
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

    private final PerformerProfileRepository performerProfileRepository;
    private final ShowsRepository showsRepository;
    private final RentalFeeService rentalFeeService;


    public SpaceApply createSpaceApply(Long spaceId, Long performerId, SpaceApplyRequestDTO.RegisterDTO registerDTO) {
        Performer performer = performerRepository.findById(performerId)
                .orElseThrow(() -> new PerformerHandler(ErrorStatus.PERFORMER_NOT_FOUND));

        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new SpaceHandler(ErrorStatus.SPACE_NOT_FOUND));

        SpaceApply spaceApply = spaceApplyConverter.toCreateSpaceApply(registerDTO, performer, space);

        spaceApplyRepository.save(spaceApply);

        for (Long serviceId : registerDTO.getSelectedAdditionalServices()) {
            SelectedAdditionalService additionalService = SelectedAdditionalService.builder()
                    .serviceId(serviceId)
                    .spaceApply(spaceApply)
                    .build();
            selectedAdditionalServiceRepository.save(additionalService);
        }

        notificationService.createSpaceApplyNotification(spaceId, registerDTO); // 알림 생성

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
    public void deleteSpaceApply(Long spaceApplyId, Long status) {
        SpaceApply spaceApply = spaceApplyRepository.findById(spaceApplyId)
                .orElseThrow(() -> new SpaceApplyHandler(ErrorStatus.SPACE_APPLY_NOT_FOUND));
        spaceApplyRepository.delete(spaceApply);

        if(status == 1){
            notificationService.createSpaceCancleNotification(spaceApply); // 알림 생성
        }
    }

    @Transactional
    public void setSpaceApply(Long spaceId, Long spaceApplyId) {
        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new SpaceHandler(ErrorStatus.SPACE_NOT_FOUND));


        SpaceApply spaceApply = spaceApplyRepository.findById(spaceApplyId)
                .orElseThrow(() -> new SpaceApplyHandler(ErrorStatus.SPACE_APPLY_NOT_FOUND));
        spaceApply.setStatus(1);

        notificationService.createSpaceConfirmNotification(spaceApply); // 알림 생성
    }

    @Transactional
    public List<Object> getSpaceApplyInfo(Long spaceId) {
        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new SpaceApplyHandler(ErrorStatus.SPACE_NOT_FOUND));

        List<Object> spaceInfo = new ArrayList<>();

        List<SpaceApply> spaceApplies = spaceApplyRepository.findBySpaceIdAndStatusIn(spaceId, Arrays.asList(0, 1));
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


    @Transactional
    public List<SpaceApplyResponseDTO.SpaceApplyWitProfilesDTO> getSpaceAppliesByPSpaceAndDate(Long spaceId) {
        List<SpaceApply> spaceApplyList = spaceApplyRepository.findBySpaceId(spaceId);

        if (spaceApplyList.isEmpty()) {
            throw new SpaceApplyHandler(ErrorStatus.SPACE_APPLY_IS_EMPTY);
        }

        List<SpaceApplyResponseDTO.SpaceApplyWitProfilesDTO> dtoList = new ArrayList<>();

        for (SpaceApply spaceApply : spaceApplyList) {
            List<Shows> showsList = showsRepository.findBySpaceApply(spaceApply);

            for (Shows show : showsList) {
                SpaceApplyResponseDTO.SpaceApplyWitProfilesDTO dto = SpaceApplyResponseDTO.SpaceApplyWitProfilesDTO.builder()
                        .id(spaceApply.getId())
                        .date(spaceApply.getDate())
                        .status(spaceApply.getStatus())
                        .audienceMin(spaceApply.getAudienceMin())
                        .audienceMax(spaceApply.getAudienceMax())
                        .rentalSum(spaceApply.getRentalSum())
                        .spaceName(spaceApply.getSpace().getName())
                        .spaceLocation(spaceApply.getSpace().getLocation())
                        .title(show.getName())
                        .poster(show.getPoster())
                        .build();

                dtoList.add(dto);
            }
        }

        return dtoList;
    }

    /*
    만일을 대비한 코드
     */

    public List<SelectedAdditionalService> getAllSelectedServicesBySpaceApply(Long spaceApplyId) {
        SpaceApply spaceApply = spaceApplyRepository.findById(spaceApplyId)
                .orElseThrow(() -> new SpaceApplyHandler(ErrorStatus.SPACE_APPLY_NOT_FOUND));

        return selectedAdditionalServiceRepository.findBySpaceApply(spaceApply);

    }

    public PerformerProfileRequestDTO.CreateProfileDTO getProfileDTOBySpaceAppId(Long spaceApplyId) {
        // spaceApplyId를 사용하여 SpaceApply 엔티티를 조회
        SpaceApply spaceApply = spaceApplyRepository.findById(spaceApplyId)
                .orElseThrow(() -> new SpaceApplyHandler(ErrorStatus.SPACE_APPLY_NOT_FOUND));

        // 해당 SpaceApply에서 PerformerProfile ID를 통해 PerformerProfile 엔티티를 조회
        PerformerProfile performerProfile = performerProfileRepository.findById(spaceApply.getPerformerProfileId())
                .orElseThrow(() -> new PerformerHandler(ErrorStatus.PERFORMER_NOT_FOUND));

        // PerformerProfile에서 이미지 URL들을 가져옴
        List<String> profileImageUrls = performerProfile.getProfileImages().stream()
                .map(ProfileImage::getProfileImageUrl)
                .collect(Collectors.toList());

        // CreateProfileDTO로 매핑
        return PerformerProfileRequestDTO.CreateProfileDTO.builder()
                .team(performerProfile.getTeam())
                .name(performerProfile.getName())
                .introduction(performerProfile.getIntroduction())
                .phoneNumber(performerProfile.getPhoneNumber())
                .profileImageUrls(profileImageUrls)
                .build();
    }

    //영수증 용 : 대관료 + 추가 서비스(이름과 가격) + 합계
    @Transactional(readOnly = true)
    public List<SpaceResponseDTO.SpaceAdditionalServiceDTO> getSelectedAdditionalServices(Long spaceApplyId) {
        SpaceApply spaceApply = spaceApplyRepository.findById(spaceApplyId)
                .orElseThrow(() -> new SpaceApplyHandler(ErrorStatus.SPACE_APPLY_NOT_FOUND));

        // 선택된 추가 서비스 정보 목록 생성
        List<SpaceResponseDTO.SpaceAdditionalServiceDTO> selectedAdditionalServices = selectedAdditionalServiceRepository.findBySpaceApply(spaceApply).stream()
                .map(service -> new SpaceResponseDTO.SpaceAdditionalServiceDTO(
                        service.getSpaceAdditionalService().getId(),
                        service.getSpaceAdditionalService().getTitle(),
                        service.getSpaceAdditionalService().getPrice()
                ))
                .collect(Collectors.toList());

        // 대관료 및 추가 서비스 가격 계산
        SpaceResponseDTO.SpacePriceResponseDTO spacePriceResponse = rentalFeeService.getSpaceDate(
                spaceApply.getSpace().getId(), spaceApply.getDate(),
                selectedAdditionalServices.stream().map(SpaceResponseDTO.SpaceAdditionalServiceDTO::getTitle).collect(Collectors.toList())
        );

        // 추가 서비스 리스트에 대관료와 총 가격을 추가
        selectedAdditionalServices.add(new SpaceResponseDTO.SpaceAdditionalServiceDTO(
                null, "Base Rental Fee", String.valueOf(spacePriceResponse.getBasePrice())
        ));
        selectedAdditionalServices.add(new SpaceResponseDTO.SpaceAdditionalServiceDTO(
                null, "Total Price", String.valueOf(spacePriceResponse.getTotalPrice())
        ));

        return selectedAdditionalServices;
    }
}
