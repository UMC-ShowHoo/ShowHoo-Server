package umc.ShowHoo.web.space.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.ShowHoo.web.rentalFee.entity.RentalFee;
import umc.ShowHoo.web.rentalFee.repository.RentalFeeRepository;
import umc.ShowHoo.web.space.dto.SpaceResponseDTO;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.space.repository.SpaceRepository;
import umc.ShowHoo.web.spaceAdditionalService.entity.SpaceAdditionalService;
import umc.ShowHoo.web.spaceAdditionalService.repository.SpaceAdditionalServiceRepository;
import umc.ShowHoo.web.spacePhoto.entity.SpacePhoto;
import umc.ShowHoo.web.spacePhoto.repository.SpacePhotoRepository;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpaceService {
    private final SpaceRepository spaceRepository;
    private final SpacePhotoRepository spacePhotoRepository;
    private final RentalFeeRepository rentalFeeRepository;
    private final SpaceAdditionalServiceRepository spaceAdditionalServiceRepository;

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

    @Transactional
    public SpaceResponseDTO.SpaceListDTO getAllSpaces() {
        List<Space> spaces = spaceRepository.findAll();

        return (SpaceResponseDTO.SpaceListDTO) spaces.stream().map(space -> {
            Integer totalCapacity = space.getSeatingCapacity() + space.getStandingCapacity();
            URL imageURL = space.getPhotos().isEmpty() ? null : space.getPhotos().get(0).getPhotoUrl();

            Integer minRentalFee = space.getRentalFees().stream()
                    .min(Comparator.comparingInt(RentalFee::getFee))
                    .map(RentalFee::getFee)
                    .orElse(null);

            String additionalService = null;
            SpaceAdditionalService selectedService = spaceAdditionalServiceRepository
                    .findBySpaceAndIsSelected(space, true)
                    .orElse(null);
            if (selectedService != null) { additionalService = selectedService.getTitle(); }

            return new SpaceResponseDTO.SpaceListDTO(
                    space.getName(),
                    space.getLocation(),
                    totalCapacity,
                    space.getArea(),
                    additionalService,
                    imageURL,
                    space.getGrade(),
                    minRentalFee
            );
        }).collect(Collectors.toList());
    }
}

