package umc.ShowHoo.web.audience.converter;

import org.springframework.data.domain.Page;
import umc.ShowHoo.web.Shows.entity.Shows;
import umc.ShowHoo.web.audience.dto.AudienceResponseDTO;

import java.util.List;

public class AudienceConverter {

    public static AudienceResponseDTO.getShowsListDTO toGetShowsListDTO(Page<Shows> showsList){
        List<AudienceResponseDTO.getShowsDTO> getShowsDTOList = showsList.stream()
                .map(AudienceConverter::toGetShowsDTO).toList();

        return AudienceResponseDTO.getShowsListDTO.builder()
                .isFirst(showsList.isFirst())
                .isLast(showsList.isLast())
                .totalElements(showsList.getTotalElements())
                .totalPages(showsList.getTotalPages())
                .showsList(getShowsDTOList)
                .listSize(getShowsDTOList.size())
                .build();
    }

    public static AudienceResponseDTO.getShowsDTO toGetShowsDTO(Shows shows){
        return AudienceResponseDTO.getShowsDTO.builder()
                .showsId(shows.getId())
                .name(shows.getName())
                .poster(shows.getPoster())
                .date(shows.getDate())
                .time(shows.getTime())
                .build();
    }

    public static AudienceResponseDTO.ShowsDetailDTO toGetShowsDetailDTO(Shows shows){
        return AudienceResponseDTO.ShowsDetailDTO.builder()
                .showsId(shows.getId())
                .name(shows.getName())
                .poster(shows.getPoster())
                .date(shows.getDate())
                .time(shows.getTime())
                .description(shows.getDescription())
                .runningTime(shows.getRunningTime())
                .showAge(shows.getShowAge())
                .ticketPrice(shows.getTicketPrice())
                .perMaxticket(shows.getPerMaxticket())
                .bank(shows.getBank())
                .accountHolder(shows.getAccountHolder())
                .accountNum(shows.getAccountNum())
                .build();
    }
}
