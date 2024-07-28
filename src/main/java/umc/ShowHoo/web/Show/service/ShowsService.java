package umc.ShowHoo.web.Show.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import umc.ShowHoo.web.Show.converter.ShowsConverter;
import umc.ShowHoo.web.Show.dto.ShowsRequestDTO;
import umc.ShowHoo.web.Show.dto.ShowsResponseDTO;
import umc.ShowHoo.web.Show.entity.Shows;
import umc.ShowHoo.web.Show.handler.ShowsHandler;
import umc.ShowHoo.web.Show.repository.ShowsRepository;
import umc.ShowHoo.web.performer.entity.Performer;
import umc.ShowHoo.web.performer.handler.PerformerHandler;
import umc.ShowHoo.web.performer.repository.PerformerRepository;

@Service
@RequiredArgsConstructor
public class ShowsService {
    private final ShowsRepository showsRepository;
    private final PerformerRepository performerRepository;

    public Shows createShows(ShowsRequestDTO requestDTO){
        Performer performer=performerRepository.findById(requestDTO.getPerformerId())
                .orElseThrow(()->new PerformerHandler(ErrorStatus.PERFORMER_NOT_FOUND));

        Shows shows=ShowsConverter.toEntity(requestDTO);
        shows.setPerformer(performer);

        return showsRepository.save(shows);
    }

    public ShowsResponseDTO.ShowRequirementDTO getShowRequirement(Long showId){
        Shows shows=showsRepository.findById(showId)
                .orElseThrow(()->new ShowsHandler(ErrorStatus.SHOW_NOT_FOUND));
        return ShowsConverter.torequirementDTO(shows);
    }
}
