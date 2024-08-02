package umc.ShowHoo.web.showsPrefer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import umc.ShowHoo.web.showsPrefer.entity.ShowsPrefer;

public interface ShowsPreferQueryService {

    Page<ShowsPrefer> getPreferList(Long audienceId, Integer page);
}
