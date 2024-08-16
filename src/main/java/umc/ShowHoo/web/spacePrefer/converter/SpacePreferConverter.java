package umc.ShowHoo.web.spacePrefer.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.ShowHoo.web.performer.entity.Performer;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.spacePrefer.dto.SpacePreferResponseDTO;
import umc.ShowHoo.web.spacePrefer.entity.SpacePrefer;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SpacePreferConverter {
    public SpacePrefer toEntity(Space space, Performer performer){
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
