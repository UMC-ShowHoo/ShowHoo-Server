package umc.ShowHoo.web.spaceApply.converter;

import org.springframework.stereotype.Component;
import umc.ShowHoo.web.performer.entity.Performer;
import umc.ShowHoo.web.selectedAdditionalService.entity.SelectedAdditionalService;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.spaceApply.dto.SpaceApplyRequestDTO;
import umc.ShowHoo.web.spaceApply.dto.SpaceApplyResponseDTO;
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

    public SpaceApplyResponseDTO.SpaceApplyDetailDTO toGetSpaceApply(SpaceApply spaceApply) {
        String spacePhotoUrl = spaceApply.getSpace().getPhotos().isEmpty()
                ? null
                : spaceApply.getSpace().getPhotos().get(0).getPhotoUrl(); // 첫 번째 사진 URL 가져오기

        return new SpaceApplyResponseDTO.SpaceApplyDetailDTO(
                spaceApply.getId(),
                spaceApply.getDate(),
                spaceApply.getStatus(),
                spaceApply.getAudienceMin(),
                spaceApply.getAudienceMax(),
                spaceApply.getRentalSum(),
                spaceApply.getSpace().getName(),
                spaceApply.getSpace().getLocation(),
                spacePhotoUrl
        );
    }


}
