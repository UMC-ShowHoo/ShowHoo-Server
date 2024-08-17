package umc.ShowHoo.web.shows.service;

import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import umc.ShowHoo.aws.s3.AmazonS3Manager;
import umc.ShowHoo.web.shows.converter.ShowsConverter;
import umc.ShowHoo.web.shows.dto.ShowsRequestDTO;
import umc.ShowHoo.web.shows.dto.ShowsResponseDTO;
import umc.ShowHoo.web.shows.entity.Shows;
import umc.ShowHoo.web.shows.handler.ShowsHandler;
import umc.ShowHoo.web.shows.repository.ShowsRepository;
import umc.ShowHoo.web.performer.handler.PerformerHandler;
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

    public Shows createShows(ShowsRequestDTO.ShowInfoDTO requestDTO, String posterUrl,Long performerProfileId){
        PerformerProfile performer = performerProfileRepository.findById(performerProfileId)
            .orElseThrow(()->new PerformerHandler(ErrorStatus.PERFORMER_NOT_FOUND));

        Shows shows=ShowsConverter.toShowInfo(requestDTO,posterUrl);
        shows.setPerformerProfile(performer);
        shows.setIsComplete(false);

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

        boolean exists=showsDscRepository.existsById(showsDescription.getShows().getId());

        if(exists){
            throw new DuplicateRequestException("Show description already exists for shows_id: "+showsDescription.getShows().getId());
        }

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
