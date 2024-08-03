package umc.ShowHoo.web.audience.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import umc.ShowHoo.web.Shows.entity.Shows;
import umc.ShowHoo.web.Shows.handler.ShowsHandler;
import umc.ShowHoo.web.Shows.repository.ShowsRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class AudienceQueryServiceImpl implements AudienceQueryService{

    private final ShowsRepository showsRepository;

    @Override
    public Page<Shows> getShowsList(Integer page){
        return showsRepository.findAll(PageRequest.of(page, 8));
    }

    @Override
    public Page<Shows> getSearchedShowsList(Integer page, String request){
        return showsRepository.findByNameContaining(request, PageRequest.of(page, 8));
    }

    @Override
    public Shows getShowsDetail(Long showsId){
        return showsRepository.findById(showsId)
                .orElseThrow(()->new ShowsHandler(ErrorStatus.SHOW_NOT_FOUND));
    }
}
