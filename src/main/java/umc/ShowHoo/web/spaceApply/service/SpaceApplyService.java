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
import umc.ShowHoo.web.performer.handler.PerformerHandler;
import umc.ShowHoo.web.performer.repository.PerformerRepository;
import umc.ShowHoo.web.performerProfile.dto.PerformerProfileRequestDTO;
import umc.ShowHoo.web.performerProfile.entity.PerformerProfile;
import umc.ShowHoo.web.performerProfile.entity.ProfileImage;
import umc.ShowHoo.web.performerProfile.repository.PerformerProfileRepository;
import umc.ShowHoo.web.selectedAdditionalService.dto.SelectedAdditionalDTO;
import umc.ShowHoo.web.selectedAdditionalService.entity.SelectedAdditionalService;
import umc.ShowHoo.web.selectedAdditionalService.repository.SelectedAdditionalServiceRepository;
import umc.ShowHoo.web.shows.dto.ShowsResponseDTO;
import umc.ShowHoo.web.shows.entity.Shows;
import umc.ShowHoo.web.shows.repository.ShowsRepository;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.space.exception.handler.SpaceHandler;
import umc.ShowHoo.web.space.repository.SpaceRepository;
import umc.ShowHoo.web.spaceAdditionalService.entity.SpaceAdditionalService;
import umc.ShowHoo.web.spaceAdditionalService.repository.SpaceAdditionalServiceRepository;
import umc.ShowHoo.web.spaceApply.converter.SpaceApplyConverter;
import umc.ShowHoo.web.spaceApply.dto.SpaceApplyRequestDTO;
import umc.ShowHoo.web.spaceApply.dto.SpaceApplyResponseDTO;
import umc.ShowHoo.web.spaceApply.entity.SpaceApply;
import umc.ShowHoo.web.spaceApply.exception.handler.SpaceApplyHandler;
import umc.ShowHoo.web.spaceApply.repository.SpaceApplyRepository;

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
    private final SpaceAdditionalServiceRepository spaceAdditionalServiceRepository;

    private final PerformerProfileRepository performerProfileRepository;
    private final ShowsRepository showsRepository;



    public SpaceApply createSpaceApply(Long spaceId, Long performerId, SpaceApplyRequestDTO.RegisterDTO registerDTO) {
        Performer performer = performerRepository.findById(performerId)
                .orElseThrow(() -> new PerformerHandler(ErrorStatus.PERFORMER_NOT_FOUND));

        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new SpaceHandler(ErrorStatus.SPACE_NOT_FOUND));

        SpaceApply spaceApply = spaceApplyConverter.toCreateSpaceApply(registerDTO, performer, space);

        spaceApplyRepository.save(spaceApply);

        for (Long serviceId : registerDTO.getSelectedAdditionalServices()) {
            SpaceAdditionalService spaceAdditionalService = spaceAdditionalServiceRepository.findById(serviceId)
                    .orElseThrow(() -> new SpaceHandler(ErrorStatus._BAD_REQUEST));
            System.out.println("spaceAdditionalService = " + spaceAdditionalService);



            SelectedAdditionalService additionalService = SelectedAdditionalService.builder()
                    .serviceId(serviceId)
                    .spaceAdditionalService(spaceAdditionalService)
                    .spaceApply(spaceApply)
                    .build();
            selectedAdditionalServiceRepository.save(additionalService);
        }

        notificationService.createSpaceApplyNotification(space, registerDTO); // 알림 생성

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
        if(status == 1){
            notificationService.createSpaceCancelNotification(spaceApply); // 알림 생성
        }

        spaceApplyRepository.delete(spaceApply);
    }

    @Transactional
    public void setSpaceApply(Long spaceApplyId) {

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
    public List<SpaceApplyResponseDTO.SpaceApplyWitProfilesDTO> getSpaceAppliesBySpaceAndDate(Long spaceId) {
        List<SpaceApply> spaceApplyList = spaceApplyRepository.findBySpaceId(spaceId);
        System.out.println("SpaceApply List : " + spaceApplyList);

        if (spaceApplyList.isEmpty()) {
            System.out.println("No spaceApply found");
            return null;
        }

        List<SpaceApplyResponseDTO.SpaceApplyWitProfilesDTO> dtoList = new ArrayList<>();

        for (SpaceApply spaceApply : spaceApplyList) {
            List<Shows> showsList = showsRepository.findBySpaceApply(spaceApply);
            System.out.println("Shows List for SpaceApply ID " + spaceApply.getId() + ": " + showsList);

            List<ShowsResponseDTO.ShowTitleAndPosterDTO> showDTOList = new ArrayList<>();
            for (Shows show : showsList) {
                ShowsResponseDTO.ShowTitleAndPosterDTO showDTO = ShowsResponseDTO.ShowTitleAndPosterDTO.builder()
                        .title(show.getName())
                        .poster(show.getPoster())
                        .build();
                showDTOList.add(showDTO);
            }

            // SpaceApplyWithShowsDTO 생성
            SpaceApplyResponseDTO.SpaceApplyWitProfilesDTO dto = SpaceApplyResponseDTO.SpaceApplyWitProfilesDTO.builder()
                    .id(spaceApply.getId())
                    .date(spaceApply.getDate())
                    .status(spaceApply.getStatus())
                    .audienceMin(spaceApply.getAudienceMin())
                    .audienceMax(spaceApply.getAudienceMax())
                    .rentalSum(spaceApply.getRentalSum())
                    .spaceName(spaceApply.getSpace().getName())
                    .spaceLocation(spaceApply.getSpace().getLocation())
                    .shows(showDTOList) // Shows 목록 추가
                    .build();

            dtoList.add(dto);

            }

        System.out.println("Final DTO List: " + dtoList);

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

    public SpaceApplyResponseDTO.ReceiptDTO getReceiptDTOBySpaceApplyId(Long spaceApplyId) {
        //영수증 조회 method
        SpaceApply spaceApply = spaceApplyRepository.findById(spaceApplyId)
                .orElseThrow(() -> new SpaceApplyHandler(ErrorStatus.SPACE_APPLY_NOT_FOUND));

        List<SelectedAdditionalService> selectedServices = selectedAdditionalServiceRepository.findBySpaceApply(spaceApply);

        List<SelectedAdditionalDTO> selectedAdditionalDTOList = selectedServices.stream()
                .map(spaceApplyConverter::ConverToSelectedAdditionalDTO)
                .collect(Collectors.toList());

        return SpaceApplyResponseDTO.ReceiptDTO.builder()
                .date(spaceApply.getDate())
                .rentalFee(spaceApply.getRentalFee())
                .rentalSum(spaceApply.getRentalSum())
                .selected(selectedAdditionalDTOList)
                .build();



    }




}
