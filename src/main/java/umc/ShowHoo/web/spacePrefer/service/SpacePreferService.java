package umc.ShowHoo.web.spacePrefer.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import umc.ShowHoo.web.performer.entity.Performer;
import umc.ShowHoo.web.performer.handler.PerformerHandler;
import umc.ShowHoo.web.performer.repository.PerformerRepository;
import umc.ShowHoo.web.space.entity.Space;
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
        Space space = spaceRepository.findById(request.getSpaceId())
                .orElseThrow(() -> new SpaceHandler(ErrorStatus.SPACE_NOT_FOUND));
        Performer performer = performerRepository.findById(request.getPerformerId())
                .orElseThrow(() -> new PerformerHandler(ErrorStatus.PERFORMER_NOT_FOUND));
        SpacePrefer spacePrefer = spacePreferConverter.toEntity(space, performer);
        SpacePrefer savedSpacePrefer = spacePreferRepository.save(spacePrefer);
        return spacePreferConverter.toResultDTO(savedSpacePrefer);
    }

    @Transactional
    public void deleteSpacePrefer(Long id) {
        if (!spacePreferRepository.existsById(id)) {
            throw new SpacePreferHandler(ErrorStatus.SPACE_PREFER_NOT_FOUND);
        }
        spacePreferRepository.deleteById(id);
    }

    @Transactional
    public Boolean checkSpacePreference(Long spaceId, Long performerId){
        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new SpaceHandler(ErrorStatus.SPACE_NOT_FOUND));
        Performer performer = performerRepository.findById(performerId)
                .orElseThrow(() -> new PerformerHandler(ErrorStatus.PERFORMER_NOT_FOUND));
        return spacePreferRepository.existsBySpaceAndPerformer(space, performer);
    }
}
