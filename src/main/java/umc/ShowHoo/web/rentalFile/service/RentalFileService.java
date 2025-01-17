package umc.ShowHoo.web.rentalFile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import umc.ShowHoo.aws.s3.AmazonS3Manager;
import umc.ShowHoo.web.shows.entity.Shows;
import umc.ShowHoo.web.shows.handler.ShowsHandler;
import umc.ShowHoo.web.shows.repository.ShowsRepository;
import umc.ShowHoo.web.rentalFile.converter.RentalFileConverter;
import umc.ShowHoo.web.rentalFile.dto.RentalFileResponseDTO;
import umc.ShowHoo.web.rentalFile.entity.RentalFile;
import umc.ShowHoo.web.rentalFile.handler.RentalFileHandler;
import umc.ShowHoo.web.rentalFile.repository.RentalFileRepository;
import umc.ShowHoo.web.shows.service.ShowsService;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.space.exception.handler.SpaceHandler;
import umc.ShowHoo.web.space.repository.SpaceRepository;
import umc.ShowHoo.web.spaceApply.entity.SpaceApply;
import umc.ShowHoo.web.spaceApply.exception.handler.SpaceApplyHandler;
import umc.ShowHoo.web.spaceApply.repository.SpaceApplyRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RentalFileService {
    private final SpaceRepository spaceRepository;
    private final RentalFileRepository rentalFileRepository;
    private final ShowsRepository showsRepository;
    private final AmazonS3Manager amazonS3Manager;
    private final SpaceApplyRepository spaceApplyRepository;
    private final ShowsService showsService;

    public RentalFile createPerformerFile(MultipartFile setList, MultipartFile rentalTime, MultipartFile addOrder,Long spaceApplyId){

        String setListUrl=setList != null ? amazonS3Manager.uploadFile("rentalFile/"+ UUID.randomUUID().toString(),setList):null;
        String rentalTimeUrl=rentalTime != null ? amazonS3Manager.uploadFile("rentalFile/"+ UUID.randomUUID().toString(),rentalTime):null;
        String addOrderUrl=addOrder != null ? amazonS3Manager.uploadFile("rentalFile/"+ UUID.randomUUID().toString(),addOrder):null;

/*        SpaceApply spaceApply=spaceApplyRepository.findById(spaceApplyId)
                .orElseThrow(()->new SpaceApplyHandler(ErrorStatus.SPACE_APPLY_NOT_FOUND));*/

        Shows shows=showsRepository.findBySpaceApplyId(spaceApplyId)
                .orElseThrow(()->new ShowsHandler(ErrorStatus.SHOW_NOT_FOUND));

        RentalFile rentalFile = rentalFileRepository.findByShows(shows);

        rentalFile.setSetList(setListUrl);
        rentalFile.setRentalTime(rentalTimeUrl);
        rentalFile.setAddOrder(addOrderUrl);

        return rentalFileRepository.save(rentalFile);
    }

    public RentalFileResponseDTO.SpaceUserSaveDTO getFormFile(Long spaceApplyId){
        Shows shows=showsRepository.findBySpaceApplyId(spaceApplyId)
                .orElseThrow(()->new ShowsHandler(ErrorStatus.SHOW_NOT_FOUND));

        RentalFile rentalFile=rentalFileRepository.findByShows(shows);

        return RentalFileConverter.toSpaceUserSaveDTO(rentalFile);
    }


    public RentalFile createFormFile(MultipartFile setListForm, MultipartFile rentalTimeForm, MultipartFile addOrderForm,Long spaceApplyId){
        String setListFormUrl=setListForm != null ? amazonS3Manager.uploadFile("rentalFileForm/"+ UUID.randomUUID().toString(),setListForm):null;
        String rentalTimeFormUrl=rentalTimeForm != null ? amazonS3Manager.uploadFile("rentalFileForm/"+ UUID.randomUUID().toString(),rentalTimeForm):null;
        String addOrderFormUrl=addOrderForm != null ? amazonS3Manager.uploadFile("rentalFileForm/"+ UUID.randomUUID().toString(),addOrderForm):null;

        SpaceApply spaceApply=spaceApplyRepository.findById(spaceApplyId)
                .orElseThrow(()-> new SpaceApplyHandler(ErrorStatus.SPACE_APPLY_NOT_FOUND));

        Shows shows=showsService.createShows(spaceApply);

        RentalFile rentalFile= RentalFileConverter.toFormEntity(setListFormUrl,rentalTimeFormUrl,addOrderFormUrl);
        rentalFile.setShows(shows);

        return rentalFileRepository.save(rentalFile);
    }

    public RentalFileResponseDTO.PerformerSaveDTO getPerformerFile(Long showId){
        Shows shows=showsRepository.findById(showId)
                .orElseThrow(()-> new ShowsHandler(ErrorStatus.SHOW_NOT_FOUND));

        RentalFile rentalFile=rentalFileRepository.findByShows(shows);

        if (rentalFile == null) {
            throw new RentalFileHandler(ErrorStatus.RENTALFILE_NOT_FOUND); // 새로운 예외 처리
        }

        return RentalFileConverter.toPerformerSaveDTO(rentalFile);
    }

}
