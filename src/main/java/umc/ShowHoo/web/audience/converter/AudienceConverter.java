package umc.ShowHoo.web.audience.converter;

import org.springframework.data.domain.Page;
import umc.ShowHoo.web.shows.entity.Shows;
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

    public static AudienceResponseDTO.getShowsListDTO toGetLikedShowsListDTO(List<AudienceResponseDTO.getShowsDTO> showsList,Page<Shows> shows){
        return AudienceResponseDTO.getShowsListDTO.builder()
                .isFirst(shows.isFirst())
                .isLast(shows.isLast())
                .totalElements(shows.getTotalElements())
                .totalPages(shows.getTotalPages())
                .showsList(showsList)
                .listSize(showsList.size())
                .build();
    }

    public static AudienceResponseDTO.getShowsDTO toGetShowsDTO(Shows shows){
        return AudienceResponseDTO.getShowsDTO.builder()
                .showsId(shows.getId())
                .name(shows.getName())
                .poster(shows.getPoster())
                .date(shows.getDate())
                .time(shows.getTime())
                .isPreferred(false)
                .build();
    }

    public static AudienceResponseDTO.getShowsDTO toGetLikedShowsDTO(Shows shows, Boolean isPreferred){
        return AudienceResponseDTO.getShowsDTO.builder()
                .showsId(shows.getId())
                .name(shows.getName())
                .poster(shows.getPoster())
                .date(shows.getDate())
                .time(shows.getTime())
                .isPreferred(isPreferred)
                .build();
    }

    public static AudienceResponseDTO.ShowsDetailDTO toGetShowsDetailDTO(Shows shows, Boolean isExist){
        return AudienceResponseDTO.ShowsDetailDTO.builder()
                .showsId(shows.getId())
                .host(shows.getPerformerProfile().getTeam())
                .name(shows.getName())
                .poster(shows.getPoster())
                .date(shows.getDate())
                .time(shows.getTime())
                .descriptionImg(isExist?shows.getShowsDescription().getImg():null)
                .description(isExist?shows.getShowsDescription().getText():null)
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
