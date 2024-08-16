package umc.ShowHoo.web.audience.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import umc.ShowHoo.web.audience.handler.AudienceHandler;
import umc.ShowHoo.web.shows.entity.Shows;
import umc.ShowHoo.web.shows.handler.ShowsHandler;
import umc.ShowHoo.web.shows.repository.ShowsRepository;
import umc.ShowHoo.web.audience.converter.AudienceConverter;
import umc.ShowHoo.web.audience.dto.AudienceResponseDTO;
import umc.ShowHoo.web.audience.entity.Audience;
import umc.ShowHoo.web.audience.repository.AudienceRepository;
import umc.ShowHoo.web.showsDescription.repository.ShowsDscRepository;
import umc.ShowHoo.web.showsPrefer.repository.ShowsPreferRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AudienceQueryServiceImpl implements AudienceQueryService{

    private final ShowsRepository showsRepository;

    private final AudienceRepository audienceRepository;

    private final ShowsPreferRepository showsPreferRepository;

    private final ShowsDscRepository showsDscRepository;

    @Override
    public Page<Shows> getShowsList(Integer page){
        return showsRepository.findAll(PageRequest.of(page, 8));
    }

    @Override
    public AudienceResponseDTO.getShowsListDTO getLikedShowsList(Long id, Integer page){
        Audience audience = audienceRepository.findById(id)
                .orElseThrow(()->new AudienceHandler(ErrorStatus.AUDIENCE_NOT_FOUND));

        Page<Shows> showsList = showsRepository.findAll(PageRequest.of(page, 8));
        List<AudienceResponseDTO.getShowsDTO> DTOs = new ArrayList<>();

        for (Shows shows : showsList) {
            boolean isPreferred = showsPreferRepository.existsByAudienceAndShows(audience, shows);
            DTOs.add(AudienceConverter.toGetLikedShowsDTO(shows, isPreferred));
        }

        return AudienceConverter.toGetLikedShowsListDTO(DTOs, showsList);
    }

    @Override
    public Page<Shows> getSearchedShowsList(Integer page, String request){
        return showsRepository.findByNameContaining(request, PageRequest.of(page, 8));
    }

    @Override
    public AudienceResponseDTO.getShowsListDTO getSearchedLikedShowsList(Long id, Integer page, String request){
        Audience audience = audienceRepository.findById(id)
            .orElseThrow(()->new AudienceHandler(ErrorStatus.AUDIENCE_NOT_FOUND));

        Page<Shows> showsList = showsRepository.findByNameContaining(request, PageRequest.of(page, 8));
        List<AudienceResponseDTO.getShowsDTO> DTOs = new ArrayList<>();

        for (Shows shows : showsList) {
            boolean isPreferred = showsPreferRepository.existsByAudienceAndShows(audience, shows);
            DTOs.add(AudienceConverter.toGetLikedShowsDTO(shows, isPreferred));
        }

        return AudienceConverter.toGetLikedShowsListDTO(DTOs, showsList);
    }

    @Override
    public Shows getShowsDetail(Long showsId){
        return showsRepository.findById(showsId)
                .orElseThrow(()->new ShowsHandler(ErrorStatus.SHOW_NOT_FOUND));
    }

    @Override
    public Boolean isDescriptionExist(Shows shows){
        return showsDscRepository.existsByShows(shows);
    }
}
