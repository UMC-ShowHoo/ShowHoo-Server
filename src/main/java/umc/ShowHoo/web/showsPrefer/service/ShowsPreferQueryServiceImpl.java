package umc.ShowHoo.web.showsPrefer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import umc.ShowHoo.web.audience.handler.AudienceHandler;
import umc.ShowHoo.web.audience.entity.Audience;
import umc.ShowHoo.web.audience.repository.AudienceRepository;
import umc.ShowHoo.web.showsPrefer.entity.ShowsPrefer;
import umc.ShowHoo.web.showsPrefer.repository.ShowsPreferRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ShowsPreferQueryServiceImpl implements ShowsPreferQueryService {

    private final ShowsPreferRepository showsPreferRepository;

    private final AudienceRepository audienceRepository;

    @Override
    public Page<ShowsPrefer> getPreferList(Long audienceId, Integer page) {
        Audience audience = audienceRepository.findById(audienceId)
                .orElseThrow(()->new AudienceHandler(ErrorStatus.AUDIENCE_NOT_FOUND));

        return showsPreferRepository.findAllByAudience(audience, PageRequest.of(page, 10));
    }
}
