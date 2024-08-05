package umc.ShowHoo.web.audience.service;

import org.springframework.data.domain.Page;
import umc.ShowHoo.web.shows.entity.Shows;
import umc.ShowHoo.web.audience.dto.AudienceResponseDTO;

public interface AudienceQueryService {

    Page<Shows> getShowsList(Integer page);

    AudienceResponseDTO.getShowsListDTO getLikedShowsList(Long id, Integer page);

    Page<Shows> getSearchedShowsList(Integer page, String request);

    AudienceResponseDTO.getShowsListDTO getSearchedLikedShowsList(Long id, Integer page, String request);

    Shows getShowsDetail(Long showsId);
}
