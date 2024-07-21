package umc.ShowHoo.web.space.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import umc.ShowHoo.web.rentalFee.entity.RentalFee;
import umc.ShowHoo.web.rentalFee.repository.RentalFeeRepository;
import umc.ShowHoo.web.space.converter.SpaceConverter;
import umc.ShowHoo.web.space.dto.SpaceResponseDTO;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.space.exception.handler.SpaceHandler;
import umc.ShowHoo.web.space.repository.SpaceRepository;
import umc.ShowHoo.web.spacePhoto.entity.SpacePhoto;
import umc.ShowHoo.web.spacePhoto.repository.SpacePhotoRepository;

@Service
@RequiredArgsConstructor
public class SpaceService {
    private final SpaceRepository spaceRepository;
    private final SpacePhotoRepository spacePhotoRepository;
    private final RentalFeeRepository rentalFeeRepository;

    @Transactional
    public Space saveSpace(Space space) {
        Space savedSpace = spaceRepository.save(space);

        if (space.getPhotos() != null) {
            for (SpacePhoto photo : space.getPhotos()) {
                photo.setSpace(savedSpace);
                spacePhotoRepository.save(photo);
            }
        }

        if (space.getRentalFees() != null) {
            for (RentalFee rentalFee : space.getRentalFees()) {
                rentalFee.setSpace(savedSpace);
                rentalFeeRepository.save(rentalFee);
            }
        }

        return savedSpace;
    }

    public SpaceResponseDTO.SpaceDescriptionDTO getSpaceDescriptionBySpaceUserId(Long spaceUserId) {
        Space space = spaceRepository.findById(spaceUserId)
                .orElseThrow(() -> new SpaceHandler(ErrorStatus.SPACE_NOT_FOUND));
        return SpaceConverter.toSpaceDescriptionDTO(space);
    }
}

