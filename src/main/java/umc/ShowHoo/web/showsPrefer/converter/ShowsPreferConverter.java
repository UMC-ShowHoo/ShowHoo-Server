package umc.ShowHoo.web.showsPrefer.converter;

import org.springframework.data.domain.Page;
import umc.ShowHoo.web.shows.entity.Shows;
import umc.ShowHoo.web.audience.entity.Audience;
import umc.ShowHoo.web.showsPrefer.dto.ShowsPreferResponseDTO;
import umc.ShowHoo.web.showsPrefer.entity.ShowsPrefer;

import java.util.List;

public class ShowsPreferConverter {

    public static ShowsPrefer toShowsPrefer(Shows shows, Audience audience){
        return ShowsPrefer.builder()
                .shows(shows)
                .audience(audience)
                .build();
    }

    public static ShowsPreferResponseDTO.createDTO toCreateDTO(ShowsPrefer showsPrefer, String msg){
        return ShowsPreferResponseDTO.createDTO.builder()
                .showsPreferId(showsPrefer.getId())
                .alert(msg)
                .build();
    }

    public static ShowsPreferResponseDTO.getPreferListDTO toGetPreferListDTO(Page<ShowsPrefer> preferList){
        List<ShowsPreferResponseDTO.getShowsPreferDTO> getShowsPreferDTOList = preferList.stream()
                .map(ShowsPreferConverter::toGetShowsPreferDTO).toList();

        return ShowsPreferResponseDTO.getPreferListDTO.builder()
                .isFirst(preferList.isFirst())
                .isLast(preferList.isLast())
                .totalPages(preferList.getTotalPages())
                .totalElements(preferList.getTotalElements())
                .listSize(getShowsPreferDTOList.size())
                .getPreferList(getShowsPreferDTOList)
                .build();
    }

    public static ShowsPreferResponseDTO.getShowsPreferDTO toGetShowsPreferDTO(ShowsPrefer showsPrefer){
        return ShowsPreferResponseDTO.getShowsPreferDTO.builder()
                .showsId(showsPrefer.getId())
                .name(showsPrefer.getShows().getName())
                .poster(showsPrefer.getShows().getPoster())
                .date(showsPrefer.getShows().getDate())
                .time(showsPrefer.getShows().getTime())
                .isComplete(showsPrefer.getShows().getIsComplete())
                .build();
    }


}
