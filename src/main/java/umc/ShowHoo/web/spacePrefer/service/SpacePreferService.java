package umc.ShowHoo.web.spacePrefer.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.ShowHoo.web.spacePrefer.converter.SpacePreferConverter;
import umc.ShowHoo.web.spacePrefer.dto.SpacePreferRequestDTO;
import umc.ShowHoo.web.spacePrefer.dto.SpacePreferResponseDTO;
import umc.ShowHoo.web.spacePrefer.entity.SpacePrefer;
import umc.ShowHoo.web.spacePrefer.repository.SpacePreferRepository;

@Service
@RequiredArgsConstructor
public class SpacePreferService {
    private final SpacePreferRepository spacePreferRepository;
    private final SpacePreferConverter spacePreferConverter;

    @Transactional
    public SpacePreferResponseDTO.ResultDTO saveSpacePrefer(SpacePreferRequestDTO request){
        SpacePrefer spacePrefer = spacePreferConverter.toEntity(request);
        SpacePrefer savedSpacePrefer = spacePreferRepository.save(spacePrefer);
        return spacePreferConverter.toResultDTO(savedSpacePrefer);
    }

    @Transactional
    public void deleteSpacePrefer(Long id) {
        if (!spacePreferRepository.existsById(id)) {
            throw new IllegalArgumentException("Invalid space prefer ID: " + id);
        }
        spacePreferRepository.deleteById(id);
    }
}
