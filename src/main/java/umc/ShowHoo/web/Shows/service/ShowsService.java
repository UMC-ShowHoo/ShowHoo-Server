package umc.ShowHoo.web.Shows.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import umc.ShowHoo.aws.s3.AmazonS3Manager;
import umc.ShowHoo.web.Shows.converter.ShowsConverter;
import umc.ShowHoo.web.Shows.dto.ShowsRequestDTO;
import umc.ShowHoo.web.Shows.dto.ShowsResponseDTO;
import umc.ShowHoo.web.Shows.entity.Shows;
import umc.ShowHoo.web.Shows.handler.ShowsHandler;
import umc.ShowHoo.web.Shows.repository.ShowsRepository;
import umc.ShowHoo.web.performer.entity.Performer;
import umc.ShowHoo.web.performer.handler.PerformerHandler;
import umc.ShowHoo.web.performer.repository.PerformerRepository;
import umc.ShowHoo.web.performerProfile.entity.PerformerProfile;
import umc.ShowHoo.web.performerProfile.repository.PerformerProfileRepository;
import umc.ShowHoo.web.showsDescription.converter.ShowsDscConverter;
import umc.ShowHoo.web.showsDescription.dto.ShowsDscRequestDTO;
import umc.ShowHoo.web.showsDescription.entity.ShowsDescription;
import umc.ShowHoo.web.showsDescription.repository.ShowsDscRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShowsService {
    private final ShowsRepository showsRepository;
    private final PerformerProfileRepository performerProfileRepository;
    private final AmazonS3Manager amazonS3Manager;
    private final ShowsDscRepository showsDscRepository;

    public Shows createShows(ShowsRequestDTO.ShowInfoDTO requestDTO, MultipartFile poster,Long performerId){
        String posterUrl=poster != null ? amazonS3Manager.uploadFile("showRegister/"+ UUID.randomUUID().toString(),poster) : null;
        PerformerProfile performer = performerProfileRepository.findById(performerId)
            .orElseThrow(()->new PerformerHandler(ErrorStatus.PERFORMER_NOT_FOUND));

        Shows shows=ShowsConverter.toShowInfo(requestDTO,posterUrl);
        shows.setPerformerProfile(performer);

        return showsRepository.save(shows);
    }

    public Shows createShowsTicket(ShowsRequestDTO.ticketInfoDTO ticketInfoDTO,Long showId){
        Shows shows=showsRepository.findById(showId)
                .orElseThrow(()->new ShowsHandler(ErrorStatus.SHOW_NOT_FOUND));

        shows=ShowsConverter.toTicketInfo(ticketInfoDTO,shows);

        return showsRepository.save(shows);
    }



    public ShowsDescription createShowDsc(ShowsDscRequestDTO.DescriptionDTO dto, MultipartFile img, Long showId){
        String imgUrl=img != null ? amazonS3Manager.uploadFile("showRegister/" + UUID.randomUUID().toString(),img) : null;
        Shows shows=showsRepository.findById(showId)
                .orElseThrow(()-> new ShowsHandler(ErrorStatus.SHOW_NOT_FOUND));

        ShowsDescription showsDescription= ShowsDscConverter.toShowDsc(dto,imgUrl,shows);

        return showsDscRepository.save(showsDescription);
    }

    public Shows createShowsReq(ShowsRequestDTO.requirementDTO requirementDTO,Long showId){
        Shows shows=showsRepository.findById(showId)
                .orElseThrow(()->new ShowsHandler(ErrorStatus.SHOW_NOT_FOUND));

        shows=ShowsConverter.toRequirement(requirementDTO,shows);

        return showsRepository.save(shows);
    }

    public ShowsResponseDTO.ShowPosterDTO getShowPoster(Long showId){
        Shows shows=showsRepository.findById(showId)
                .orElseThrow(()-> new ShowsHandler(ErrorStatus.SHOW_NOT_FOUND));
        return ShowsConverter.toshowPosterDTO(shows);
    }

    public ShowsResponseDTO.ShowRequirementDTO getShowRequirement(Long showId){
        Shows shows=showsRepository.findById(showId)
                .orElseThrow(()->new ShowsHandler(ErrorStatus.SHOW_NOT_FOUND));
        return ShowsConverter.torequirementDTO(shows);
    }
}
