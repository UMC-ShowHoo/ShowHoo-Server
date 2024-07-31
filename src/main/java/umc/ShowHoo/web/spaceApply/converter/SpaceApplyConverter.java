package umc.ShowHoo.web.spaceApply.converter;

import org.springframework.stereotype.Component;
import umc.ShowHoo.web.performer.entity.Performer;
import umc.ShowHoo.web.selectedAdditionalService.entity.SelectedAdditionalService;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.spaceApply.dto.SpaceApplyRequestDTO;
import umc.ShowHoo.web.spaceApply.entity.SpaceApply;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SpaceApplyConverter {

    public SpaceApply toCreateSpaceApply(SpaceApplyRequestDTO.RegisterDTO requestDto, Performer performer, Space space) {
        return SpaceApply.builder()
                .date(requestDto.getDate())
                .audienceMin(requestDto.getAudienceMin())
                .audienceMax(requestDto.getAudienceMax())
                .rentalFee(requestDto.getRentalFee())
                .rentalSum(requestDto.getRentalSum())
                .performerProfileId(requestDto.getPerformerProfileId())
                .status(0) // 기본값 설정
                .performer(performer)
                .space(space)
                .build();
    }
}
