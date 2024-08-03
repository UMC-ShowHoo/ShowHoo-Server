package umc.ShowHoo.web.audience.service;

import org.springframework.data.domain.Page;
import umc.ShowHoo.web.Shows.entity.Shows;
import umc.ShowHoo.web.audience.dto.AudienceResponseDTO;

import java.util.List;

public interface AudienceQueryService {

    Page<Shows> getShowsList(Integer page);

    AudienceResponseDTO.getShowsListDTO getLikedShowsList(Long id, Integer page);

    Page<Shows> getSearchedShowsList(Integer page, String request);

    AudienceResponseDTO.getShowsListDTO getSearchedLikedShowsList(Long id, Integer page, String request);

    Shows getShowsDetail(Long showsId);
}
