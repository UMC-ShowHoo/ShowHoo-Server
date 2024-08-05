package umc.ShowHoo.web.showsPrefer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import umc.ShowHoo.apiPayload.exception.handler.AudienceHandler;
import umc.ShowHoo.web.shows.entity.Shows;
import umc.ShowHoo.web.shows.handler.ShowsHandler;
import umc.ShowHoo.web.shows.repository.ShowsRepository;
import umc.ShowHoo.web.audience.entity.Audience;
import umc.ShowHoo.web.audience.repository.AudienceRepository;
import umc.ShowHoo.web.showsPrefer.converter.ShowsPreferConverter;
import umc.ShowHoo.web.showsPrefer.dto.ShowsPreferRequestDTO;
import umc.ShowHoo.web.showsPrefer.dto.ShowsPreferResponseDTO;
import umc.ShowHoo.web.showsPrefer.entity.ShowsPrefer;
import umc.ShowHoo.web.showsPrefer.handler.ShowsPreferHandler;
import umc.ShowHoo.web.showsPrefer.repository.ShowsPreferRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ShowsPreferCommandServiceImpl implements ShowsPreferCommandService{

    private final AudienceRepository audienceRepository;

    private final ShowsRepository showsRepository;

    private final ShowsPreferRepository showsPreferRepository;

    @Override
    public ShowsPreferResponseDTO.createDTO createShowsPrefer(ShowsPreferRequestDTO.createDTO request){
        Shows shows = showsRepository.findById(request.getShowsId())
                .orElseThrow(()->new ShowsHandler(ErrorStatus.SHOW_NOT_FOUND));

        Audience audience = audienceRepository.findById(request.getAudienceId())
                .orElseThrow(()->new AudienceHandler(ErrorStatus.AUDIENCE_NOT_FOUND));

        ShowsPrefer showsPrefer = showsPreferRepository.findByAudienceAndShows(audience, shows)
                .orElse(null);

        if(showsPrefer != null){
            return ShowsPreferConverter.toCreateDTO(showsPrefer, "이미 관심있는 공연으로 등록되어있습니다.");
        }
        else {
            ShowsPrefer newShowsPrefer = ShowsPreferConverter.toShowsPrefer(shows, audience);
            showsPreferRepository.save(newShowsPrefer);
            return ShowsPreferConverter.toCreateDTO(newShowsPrefer, "관심있는 공연으로 등록되었습니다.");
        }
    }

    @Override
    public String deleteShowsPrefer(Long id){
        ShowsPrefer showsPrefer = showsPreferRepository.findById(id)
                .orElseThrow(()->new ShowsPreferHandler(ErrorStatus.SHOWS_PREFER_NOT_FOUND));

        showsPreferRepository.delete(showsPrefer);
        return "Shows prefer deleted";
    }
}
