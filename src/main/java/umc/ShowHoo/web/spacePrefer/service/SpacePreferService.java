package umc.ShowHoo.web.spacePrefer.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import umc.ShowHoo.web.performer.handler.PerformerHandler;
import umc.ShowHoo.web.performer.repository.PerformerRepository;
import umc.ShowHoo.web.space.exception.handler.SpaceHandler;
import umc.ShowHoo.web.space.repository.SpaceRepository;
import umc.ShowHoo.web.spacePrefer.converter.SpacePreferConverter;
import umc.ShowHoo.web.spacePrefer.dto.SpacePreferRequestDTO;
import umc.ShowHoo.web.spacePrefer.dto.SpacePreferResponseDTO;
import umc.ShowHoo.web.spacePrefer.entity.SpacePrefer;
import umc.ShowHoo.web.spacePrefer.handler.SpacePreferHandler;
import umc.ShowHoo.web.spacePrefer.repository.SpacePreferRepository;

@Service
@RequiredArgsConstructor
public class SpacePreferService {
    private final SpacePreferRepository spacePreferRepository;
    private final SpacePreferConverter spacePreferConverter;
    private final SpaceRepository spaceRepository;
    private final PerformerRepository performerRepository;

    @Transactional
    public SpacePreferResponseDTO.ResultDTO saveSpacePrefer(SpacePreferRequestDTO request){
        if (!spaceRepository.existsById(request.getSpaceId())) {
            throw new SpaceHandler(ErrorStatus.SPACE_NOT_FOUND);
        }
        if (!performerRepository.existsById(request.getPerformerId())) {
            throw new PerformerHandler(ErrorStatus.PERFORMER_NOT_FOUND);
        }
        SpacePrefer spacePrefer = spacePreferConverter.toEntity(request);
        SpacePrefer savedSpacePrefer = spacePreferRepository.save(spacePrefer);
        return spacePreferConverter.toResultDTO(savedSpacePrefer);
    }

    @Transactional
    public void deleteSpacePrefer(Long id) {
        spacePreferRepository.findById(id).orElseThrow(() -> new SpacePreferHandler(ErrorStatus.SPACE_PREFER_NOT_FOUND));
        if (!spacePreferRepository.existsById(id)) {
            throw new SpacePreferHandler(ErrorStatus.SPACE_PREFER_NOT_FOUND);
        }
        spacePreferRepository.deleteById(id);
    }
}
