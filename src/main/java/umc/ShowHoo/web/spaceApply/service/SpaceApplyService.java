package umc.ShowHoo.web.spaceApply.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import umc.ShowHoo.web.performer.entity.Performer;
import umc.ShowHoo.web.performer.repository.PerformerRepository;
import umc.ShowHoo.web.selectedAdditionalService.entity.SelectedAdditionalService;
import umc.ShowHoo.web.selectedAdditionalService.repository.SelectedAdditionalServiceRepository;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.space.repository.SpaceRepository;
import umc.ShowHoo.web.spaceApply.converter.SpaceApplyConverter;
//import umc.ShowHoo.web.spaceApply.dto.SpaceApplyDTO;
import umc.ShowHoo.web.spaceApply.dto.SpaceApplyRequestDTO;
import umc.ShowHoo.web.spaceApply.dto.SpaceApplyResponseDTO;
import umc.ShowHoo.web.spaceApply.entity.SpaceApply;
import umc.ShowHoo.web.spaceApply.exception.handler.SpaceApplyHandler;
import umc.ShowHoo.web.spaceApply.repository.SpaceApplyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SpaceApplyService {

    private final SpaceApplyRepository spaceApplyRepository;
    private final PerformerRepository performerRepository;
    private final SpaceRepository spaceRepository;
    private final SelectedAdditionalServiceRepository selectedAdditionalServiceRepository;
    private final SpaceApplyConverter spaceApplyConverter;


    public SpaceApply createSpaceApply(Long spaceUserId, Long performerId, SpaceApplyRequestDTO.RegisterDTO registerDTO) {
        Performer performer = performerRepository.findById(performerId)
                .orElseThrow(() -> new SpaceApplyHandler(ErrorStatus.SPACE_APPLY_NOT_FOUND));

        Space space = spaceRepository.findById(spaceUserId)
                .orElseThrow(() -> new SpaceApplyHandler(ErrorStatus.SPACE_NOT_FOUND));


        SpaceApply spaceApply = spaceApplyConverter.toCreateSpaceApply(registerDTO, performer, space);

        spaceApplyRepository.save(spaceApply);

        for (Long serviceId : registerDTO.getSelectedAdditionalServices()) {
            SelectedAdditionalService additionalService = SelectedAdditionalService.builder()
                    .serviceId(serviceId)
                    .spaceApply(spaceApply)
                    .build();
            selectedAdditionalServiceRepository.save(additionalService);
        }

        return spaceApply;
    }


    @Transactional
    public List<SpaceApplyResponseDTO.SpaceApplyDetailDTO> getSpaceAppliesByPerformerId(Long performerId) {
        List<SpaceApply> spaceApplies = spaceApplyRepository.findByPerformer(performerId);
        return spaceApplies.stream()
                .map(spaceApplyConverter::toGetSpaceApply)
                .collect(Collectors.toList());
    }



    @Transactional
    public void deleteSpaceApply(Long spaceApplyId) {
        SpaceApply spaceApply = spaceApplyRepository.findById(spaceApplyId)
                .orElseThrow(() -> new SpaceApplyHandler(ErrorStatus.SPACE_APPLY_NOT_FOUND));
        spaceApplyRepository.delete(spaceApply);
    }


    @Transactional
    public void setSpaceApply(Long spaceUserId, Long spaceApplyId, int status) {
        Space space = spaceRepository.findById(spaceUserId)
                .orElseThrow(() -> new SpaceApplyHandler(ErrorStatus.SPACE_NOT_FOUND));

        SpaceApply spaceApply = spaceApplyRepository.findById(spaceApplyId)
                .orElseThrow(() -> new SpaceApplyHandler(ErrorStatus.SPACE_NOT_FOUND));
        spaceApply.setStatus(status);
    }




}
