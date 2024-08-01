package umc.ShowHoo.web.rentalFile.converter;

import org.springframework.stereotype.Component;
import umc.ShowHoo.web.rentalFile.dto.RentalFileResponseDTO;
import umc.ShowHoo.web.rentalFile.entity.RentalFile;

@Component
public class RentalFileConverter {

//    public static RentalFile toEntity((RentalFileRequestDTO.PerformerPostDTO pdto, RentalFileRequestDTO.SpaceUserPostDTO sdto)

    public static RentalFile toEntity(String setListForm, String rentalTimeForm, String addOrderForm,
                                      String setList, String rentalTime, String addOrder){
        return RentalFile.builder()
                .setListForm(setListForm)
                .rentalTimeForm(rentalTimeForm)
                .addOrderForm(addOrderForm)
                .setList(setList)
                .rentalTime(rentalTime)
                .addOrder(addOrder)
                .build();
    }


    public static RentalFileResponseDTO.PerformerSaveDTO toPerformerSaveDTO(RentalFile rentalFile){
        return RentalFileResponseDTO.PerformerSaveDTO.builder()
                .id(rentalFile.getId())
                .setList(rentalFile.getSetList())
                .rentalTime(rentalFile.getRentalTime())
                .addOrder(rentalFile.getAddOrder())
                .build();
    }

    public static RentalFileResponseDTO.SpaceUserSaveDTO toSpaceUserSaveDTO(RentalFile rentalFile){
        return RentalFileResponseDTO.SpaceUserSaveDTO.builder()
                .setListForm(rentalFile.getSetListForm())
                .rentalTimeForm(rentalFile.getRentalTimeForm())
                .addOrderForm(rentalFile.getAddOrderForm())
                .build();
    }
}
