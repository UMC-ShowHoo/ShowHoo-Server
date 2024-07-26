package umc.ShowHoo.web.spacePrefer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SpacePreferRequestDTO {
    private Long spaceId;
    private Long performerId;
}
