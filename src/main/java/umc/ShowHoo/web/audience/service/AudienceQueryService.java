package umc.ShowHoo.web.audience.service;

import umc.ShowHoo.web.shows.entity.Shows;
import umc.ShowHoo.web.audience.dto.AudienceResponseDTO;

import java.util.List;

public interface AudienceQueryService {

    List<Shows> getShowsList();

    AudienceResponseDTO.getShowsListDTO getLikedShowsList(Long id);

    List<Shows> getSearchedShowsList(String request);

    AudienceResponseDTO.getShowsListDTO getSearchedLikedShowsList(Long id, String request);

    Shows getShowsDetail(Long showsId);

    Boolean isDescriptionExist(Shows shows);
}
