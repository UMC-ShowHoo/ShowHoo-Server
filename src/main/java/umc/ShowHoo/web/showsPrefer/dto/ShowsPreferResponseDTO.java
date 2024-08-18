package umc.ShowHoo.web.showsPrefer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ShowsPreferResponseDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class createDTO{
        Long showsPreferId;
        String alert;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class deleteDTO{
        String alert;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getPreferListDTO {
        List<ShowsPreferResponseDTO.getShowsPreferDTO> getPreferList;
        Integer listSize;
        Integer totalPages;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getShowsPreferDTO{
        Long showsId;
        String poster;
        String name;
        String date;
        String time;
        Boolean isComplete;
    }

}
