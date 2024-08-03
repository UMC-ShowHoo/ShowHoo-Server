package umc.ShowHoo.web.audience.service;

import org.springframework.data.domain.Page;
import umc.ShowHoo.web.Shows.entity.Shows;

public interface AudienceQueryService {

    Page<Shows> getShowsList(Integer page);

    Page<Shows> getSearchedShowsList(Integer page, String request);

    Shows getShowsDetail(Long showsId);
}
