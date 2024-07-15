package umc.ShowHoo.converter;

import umc.ShowHoo.web.dto.TempDTO;

public class TempConverter {

    public static TempDTO.TempTestDTO toTempTestDTO(){
        return TempDTO.TempTestDTO.builder()
                .testString("Test")
                .build();
    }

    public static TempDTO.TempExceptionDTO toTempExceptionDTO(Integer flag){
        return TempDTO.TempExceptionDTO.builder()
                .flag(flag)
                .build();
    }
}
