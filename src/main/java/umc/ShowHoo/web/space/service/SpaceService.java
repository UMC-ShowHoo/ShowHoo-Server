package umc.ShowHoo.web.space.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import umc.ShowHoo.aws.s3.AmazonS3Manager;
import umc.ShowHoo.web.holiday.entity.Holiday;
import umc.ShowHoo.web.holiday.repository.HolidayRepository;
import umc.ShowHoo.web.peakSeasonRentalFee.entity.PeakSeasonRentalFee;
import umc.ShowHoo.web.peakSeasonRentalFee.repository.PeakSeasonRentalFeeRepository;
import umc.ShowHoo.web.performer.entity.Performer;
import umc.ShowHoo.web.performer.handler.PerformerHandler;
import umc.ShowHoo.web.performer.repository.PerformerRepository;
import umc.ShowHoo.web.rentalFee.entity.RentalFee;
import umc.ShowHoo.web.rentalFee.repository.RentalFeeRepository;
import umc.ShowHoo.web.space.converter.SpaceConverter;
import umc.ShowHoo.web.space.dto.SpaceRequestDTO;
import umc.ShowHoo.web.space.dto.SpaceResponseDTO;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.space.exception.handler.SpaceHandler;
import umc.ShowHoo.web.space.repository.SpaceRepository;
import umc.ShowHoo.web.spaceAdditionalService.entity.SpaceAdditionalService;
import umc.ShowHoo.web.spaceAdditionalService.repository.SpaceAdditionalServiceRepository;
import umc.ShowHoo.web.spacePhoto.entity.SpacePhoto;
import umc.ShowHoo.web.spacePhoto.repository.SpacePhotoRepository;
import umc.ShowHoo.web.spacePrefer.entity.SpacePrefer;
import umc.ShowHoo.web.spacePrefer.repository.SpacePreferRepository;
import umc.ShowHoo.web.spaceUser.entity.SpaceUser;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpaceService {
    private final SpaceRepository spaceRepository;
    private final SpacePhotoRepository spacePhotoRepository;
    private final HolidayRepository holidayRepository;
    private final RentalFeeRepository rentalFeeRepository;
    private final PeakSeasonRentalFeeRepository peakSeasonRentalFeeRepository;
    private final SpaceAdditionalServiceRepository spaceAdditionalServiceRepository;
    private final AmazonS3Manager amazonS3Manager;
    private final SpacePreferRepository spacePreferRepository;
    private final PerformerRepository performerRepository;

    @Autowired
    private SpaceConverter spaceConverter;

    @Transactional
    public Space saveSpace(
            SpaceRequestDTO.SpaceRegisterRequestDTO dto,
            SpaceUser spaceUser, MultipartFile soundEquipment,
            MultipartFile lightingEquipment,
            MultipartFile stageMachinery,
            MultipartFile spaceDrawing,
            MultipartFile spaceStaff,
            MultipartFile spaceSeat) {
        String soundEquipmentUrl = soundEquipment != null ? amazonS3Manager.uploadFile("spaceRegister/" + UUID.randomUUID().toString(), soundEquipment) : null;
        String lightingEquipmentUrl = lightingEquipment != null ? amazonS3Manager.uploadFile("spaceRegister/" + UUID.randomUUID().toString(), lightingEquipment) : null;
        String stageMachineryUrl = stageMachinery != null ? amazonS3Manager.uploadFile("spaceRegister/" + UUID.randomUUID().toString(), stageMachinery) : null;
        String spaceDrawingUrl = spaceDrawing != null ? amazonS3Manager.uploadFile("spaceRegister/" + UUID.randomUUID().toString(), spaceDrawing) : null;
        String spaceStaffUrl = spaceStaff != null ? amazonS3Manager.uploadFile("spaceRegister/" + UUID.randomUUID().toString(), spaceStaff) : null;
        String spaceSeatUrl = spaceSeat != null ? amazonS3Manager.uploadFile("spaceRegister/" + UUID.randomUUID().toString(), spaceSeat) : null;


        Space space = SpaceConverter.toEntity(dto, soundEquipmentUrl, lightingEquipmentUrl,
                stageMachineryUrl, spaceDrawingUrl,
                spaceStaffUrl, spaceSeatUrl, spaceUser);

        return spaceRepository.save(space);
    }

    @Transactional
    public SpaceResponseDTO.SpaceDescriptionDTO getSpaceDescriptionBySpaceUserId(Long spaceId) {
        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new SpaceHandler(ErrorStatus.SPACE_NOT_FOUND));
        return SpaceConverter.toSpaceDescriptionDTO(space);
    }

    public SpaceResponseDTO.SpaceNoticeDTO getSpaceNotice(Long spaceId) {
        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new SpaceHandler(ErrorStatus.SPACE_NOT_FOUND));
        return SpaceConverter.toSpaceNoticeDTO(space);
    }

    public SpaceResponseDTO.SpaceFileDTO getSpaceFile(Long spaceId) {
        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new SpaceHandler(ErrorStatus.SPACE_NOT_FOUND));
        return SpaceConverter.toSpaceFileDTO(space);
    }

    public SpaceResponseDTO.SpacePayDTO getSpacePay(Long spaceId) {
        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new SpaceHandler(ErrorStatus.SPACE_NOT_FOUND));
        return SpaceConverter.toSpacePayDTO(space);
    }
    @Transactional
    public SpaceResponseDTO.SpaceListDTO getTopSpacesWithPreference(Long performerId) {
        Pageable pageable = PageRequest.of(0, 8);
        List<Space> spacePreferList = spaceRepository.findTopBySpacePreferOrderByCountDesc(pageable); // prefer순 8개 조회
        List<Space> gradeList = spaceRepository.findTopByOrderByGradeDesc(pageable); // grade순 8개 조회

        if (performerId != null) {
            performerRepository.findById(performerId).orElseThrow(() -> new PerformerHandler(ErrorStatus.PERFORMER_NOT_FOUND));
            return spaceConverter.toTopSpaceListDTO(spacePreferList, gradeList, performerId);
        } else {
            return spaceConverter.toTopSpaceListWithNullDTO(spacePreferList, gradeList);
        }
    }

    @Transactional
    public SpaceResponseDTO.SpaceFilteredListDTO searchSpaces(SpaceRequestDTO.SpaceSearchRequestDTO searchRequest, Long performerId) {
        Pageable pageable = PageRequest.of(searchRequest.getPage(), searchRequest.getSize());

        List<Space> spaces = spaceRepository.searchSpaces(
                searchRequest.getName(),
                searchRequest.getCity(),
                searchRequest.getDistrict(),
                searchRequest.getDate(),
                searchRequest.getType(),
                searchRequest.getMinPrice(),
                searchRequest.getMaxPrice(),
                searchRequest.getMinCapacity(),
                searchRequest.getMaxCapacity(),
                pageable
        );

        if (performerId != null) {
            performerRepository.findById(performerId).orElseThrow(() -> new PerformerHandler(ErrorStatus.PERFORMER_NOT_FOUND));
            return spaceConverter.toSpaceListDTO(spaces, performerId);
        } else {
            return spaceConverter.toSpaceListWithNullDTO(spaces);
        }
    }

    @Transactional
    public SpaceResponseDTO.SpaceFilteredListDTO getPreferSpace(Long performerId) {
        Performer performer = performerRepository.findById(performerId)
                .orElseThrow(() -> new PerformerHandler(ErrorStatus.PERFORMER_NOT_FOUND));
        List<SpacePrefer> spacePreferList = spacePreferRepository.findByPerformer(performer);

        return spaceConverter.toSpaceByPreferListDTO(spacePreferList, performerId);
    }

    public Space saveSpaceName(SpaceRequestDTO.SpaceNameDTO spaceNameDTO, SpaceUser spaceUser) {
        Space space = SpaceConverter.toCreateSpaceName(spaceNameDTO, spaceUser);
        return spaceRepository.save(space);
    }

    @Transactional
    public void updateSpaceGrade(Space space) {
        space.updateGrade();
        spaceRepository.save(space);
    }

    public SpaceResponseDTO.SpacePreferDTO getSpacePrefer(Long spaceId, Long performerId) {

    }
}

