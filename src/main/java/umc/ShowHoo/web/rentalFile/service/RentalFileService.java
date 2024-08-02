package umc.ShowHoo.web.rentalFile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import umc.ShowHoo.aws.s3.AmazonS3Manager;
import umc.ShowHoo.web.Shows.repository.ShowsRepository;
import umc.ShowHoo.web.rentalFile.converter.RentalFileConverter;
import umc.ShowHoo.web.rentalFile.dto.RentalFileResponseDTO;
import umc.ShowHoo.web.rentalFile.entity.RentalFile;
import umc.ShowHoo.web.rentalFile.handler.RentalFileHandler;
import umc.ShowHoo.web.rentalFile.repository.RentalFileRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RentalFileService {
    private final RentalFileRepository rentalFileRepository;
    private final ShowsRepository showsRepository;
    private final AmazonS3Manager amazonS3Manager;

    public RentalFile createPerformerFile(MultipartFile setList, MultipartFile rentalTime, MultipartFile addOrder){
        String setListUrl=setList != null ? amazonS3Manager.uploadFile("rentalFile/"+ UUID.randomUUID().toString(),setList):null;
        String rentalTimeUrl=rentalTime != null ? amazonS3Manager.uploadFile("rentalFile/"+ UUID.randomUUID().toString(),rentalTime):null;
        String addOrderUrl=addOrder != null ? amazonS3Manager.uploadFile("rentalFile/"+ UUID.randomUUID().toString(),addOrder):null;

        RentalFile rentalFile= RentalFileConverter.toFileEntity(setListUrl,rentalTimeUrl,addOrderUrl);
        return rentalFileRepository.save(rentalFile);
    }

    public RentalFileResponseDTO.SpaceUserSaveDTO getFormFile(Long showsId){
        RentalFile rentalFile=rentalFileRepository.findById(showsId)
                .orElseThrow(()->new RentalFileHandler(ErrorStatus.RENTALFILE_FORM_NOT_FOUND));
        return RentalFileConverter.toSpaceUserSaveDTO(rentalFile);
    }


    public RentalFile createFormFile(MultipartFile setListForm, MultipartFile rentalTimeForm, MultipartFile addOrderForm){
        String setListFormUrl=setListForm != null ? amazonS3Manager.uploadFile("rentalFileForm/"+ UUID.randomUUID().toString(),setListForm):null;
        String rentalTimeFormUrl=rentalTimeForm != null ? amazonS3Manager.uploadFile("rentalFileForm/"+ UUID.randomUUID().toString(),rentalTimeForm):null;
        String addOrderFormUrl=addOrderForm != null ? amazonS3Manager.uploadFile("rentalFileForm/"+ UUID.randomUUID().toString(),addOrderForm):null;

        RentalFile rentalFile= RentalFileConverter.toFormEntity(setListFormUrl,rentalTimeFormUrl,addOrderFormUrl);
        return rentalFileRepository.save(rentalFile);
    }

    public RentalFileResponseDTO.PerformerSaveDTO getPerformerFile(Long showsId){
        RentalFile rentalFile=rentalFileRepository.findById(showsId)
                .orElseThrow(()->new RentalFileHandler(ErrorStatus.RENTALFILE_NOT_FOUND));
        return RentalFileConverter.toPerformerSaveDTO(rentalFile);
    }

}
