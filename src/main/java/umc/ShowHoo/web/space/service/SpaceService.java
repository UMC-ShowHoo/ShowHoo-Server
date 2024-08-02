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
import umc.ShowHoo.web.peakSeasonRentalFee.entity.PeakSeasonRentalFee;
import umc.ShowHoo.web.peakSeasonRentalFee.repository.PeakSeasonRentalFeeRepository;
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
import umc.ShowHoo.web.spaceUser.entity.SpaceUser;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpaceService {
    private final SpaceRepository spaceRepository;
    private final SpacePhotoRepository spacePhotoRepository;
    private final RentalFeeRepository rentalFeeRepository;
    private final PeakSeasonRentalFeeRepository peakSeasonRentalFeeRepository;
    private final SpaceAdditionalServiceRepository spaceAdditionalServiceRepository;
    private final AmazonS3Manager amazonS3Manager;

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


        Space space = SpaceConverter.toEntity(dto, soundEquipmentUrl, lightingEquipmentUrl, stageMachineryUrl, spaceDrawingUrl, spaceStaffUrl, spaceSeatUrl, spaceUser);
        Space savedSpace = spaceRepository.save(space);

        if (dto.getPhotoUrls() != null) {
            List<SpacePhoto> photos = dto.getPhotoUrls().stream()
                    .map(url -> SpacePhoto.builder().photoUrl(url).space(savedSpace).build())
                    .collect(Collectors.toList());
            spacePhotoRepository.saveAll(photos);
        }

        if (space.getAdditionalServices() != null) {
            for (SpaceAdditionalService service : space.getAdditionalServices()) {
                service.setSpace(savedSpace);
                spaceAdditionalServiceRepository.save(service);
            }
        }

        if (space.getRentalFees() != null) {
            for (RentalFee rentalFee : space.getRentalFees()) {
                rentalFee.setSpace(savedSpace);
                rentalFeeRepository.save(rentalFee);
            }
        }

        if (space.getPeakSeasonRentalFees() != null){
            for (PeakSeasonRentalFee peakSeasonRentalFee : space.getPeakSeasonRentalFees()){
                peakSeasonRentalFee.setSpace(savedSpace);
                peakSeasonRentalFeeRepository.save(peakSeasonRentalFee);
            }
        }

        return savedSpace;
    }

    @Transactional
    public SpaceResponseDTO.SpaceDescriptionDTO getSpaceDescriptionBySpaceUserId(Long spaceUserId) {
        Space space = spaceRepository.findById(spaceUserId)
                .orElseThrow(() -> new SpaceHandler(ErrorStatus.SPACE_NOT_FOUND));
        return SpaceConverter.toSpaceDescriptionDTO(space);
    }

    public SpaceResponseDTO.SpaceNoticeDTO getSpaceNotice(Long spaceUserId) {
        Space space = spaceRepository.findById(spaceUserId)
                .orElseThrow(() -> new SpaceHandler(ErrorStatus.SPACE_NOT_FOUND));
        return SpaceConverter.toSpaceNoticeDTO(space);
    }

    public SpaceResponseDTO.SpaceFileDTO getSpaceFile(Long spaceUserId) {
        Space space = spaceRepository.findById(spaceUserId)
                .orElseThrow(() -> new SpaceHandler(ErrorStatus.SPACE_NOT_FOUND));
        return SpaceConverter.toSpaceFileDTO(space);
    }

    @Transactional
    public SpaceResponseDTO.SpaceListDTO getTopSpacesWithPreference(Long performerId) {
        Pageable pageable = PageRequest.of(0, 8);
        List<Space> spacePreferList = spaceRepository.findTopBySpacePreferOrderByCountDesc(pageable);
        List<Space> gradeList = spaceRepository.findTopByOrderByGradeDesc(pageable);

        return spaceConverter.toTopSpaceListDTO(spacePreferList, gradeList, performerId);
    }

    @Transactional
    public SpaceResponseDTO.SpaceFilteredListDTO searchSpaces(SpaceRequestDTO.SpaceSearchRequestDTO searchRequest, Long performerId) {
        Pageable pageable = PageRequest.of(searchRequest.getPage(), searchRequest.getSize());
        String namePattern = searchRequest.getName() != null ? "%" + searchRequest.getName() + "%" : null;
        String locationPattern = searchRequest.getLocation() != null ? "%" + searchRequest.getLocation() + "%" : null;

        List<Space> spaces = spaceRepository.searchSpaces(
                namePattern,
                locationPattern,
                searchRequest.getDate(),
                searchRequest.getType(),
                searchRequest.getMinPrice(),
                searchRequest.getMaxPrice(),
                searchRequest.getMinCapacity(),
                searchRequest.getMaxCapacity(),
                pageable
        );

        return spaceConverter.toSpaceListDTO(spaces, performerId);
    }

    public Space saveSpaceName(SpaceRequestDTO.SpaceNameDTO spaceNameDTO, SpaceUser spaceUser) {
        Space space = SpaceConverter.toCreateSpaceName(spaceNameDTO, spaceUser);
        return spaceRepository.save(space);
    }

}

