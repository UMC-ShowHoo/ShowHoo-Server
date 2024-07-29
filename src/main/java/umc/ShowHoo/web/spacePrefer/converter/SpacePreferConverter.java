package umc.ShowHoo.web.spacePrefer.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import umc.ShowHoo.web.performer.entity.Performer;
import umc.ShowHoo.web.performer.repository.PerformerRepository;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.space.exception.handler.SpaceHandler;
import umc.ShowHoo.web.space.repository.SpaceRepository;
import umc.ShowHoo.web.spacePrefer.dto.SpacePreferRequestDTO;
import umc.ShowHoo.web.spacePrefer.dto.SpacePreferResponseDTO;
import umc.ShowHoo.web.spacePrefer.entity.SpacePrefer;
import umc.ShowHoo.web.spacePrefer.handler.SpacePreferHandler;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SpacePreferConverter {
    private final SpaceRepository spaceRepository;
    private final PerformerRepository performerRepository;
    public SpacePrefer toEntity(SpacePreferRequestDTO request){
        Space space = spaceRepository.findById(request.getSpaceId())
                .orElseThrow(() -> new SpaceHandler(ErrorStatus.SPACE_NOT_FOUND));
        Performer performer = performerRepository.findById(request.getPerformerId())
                .orElseThrow(() -> new SpaceHandler(ErrorStatus.PERFORMER_NOT_FOUND));

        return SpacePrefer.builder()
                .space(space)
                .performer(performer)
                .build();
    }

    public static SpacePreferResponseDTO.ResultDTO toResultDTO(SpacePrefer spacePrefer){
        return SpacePreferResponseDTO.ResultDTO.builder()
                .spacePreferId(spacePrefer.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
