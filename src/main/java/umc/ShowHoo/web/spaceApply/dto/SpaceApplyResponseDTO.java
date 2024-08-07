package umc.ShowHoo.web.spaceApply.dto;

import jdk.dynalink.linker.LinkerServices;
import lombok.*;
import org.w3c.dom.stylesheets.LinkStyle;
import umc.ShowHoo.web.holiday.dto.HolidayDTO;

import java.time.LocalDate;
import java.util.List;

public class SpaceApplyResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpaceApplyDetailDTO {
        private Long id;
        private LocalDate date;
        private int status;
        private int audienceMin;
        private int audienceMax;
        private Integer rentalSum;
        private String spaceName;
        private String spaceLocation;
        private String spacePhotoUrl;
    }

    @Builder
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpaceApplySimpleDTO {
        private Long id;
        private LocalDate date;
        private int status;
        private List<HolidayDTO> holidays;
    }
}
