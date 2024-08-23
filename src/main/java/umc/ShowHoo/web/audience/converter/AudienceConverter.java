package umc.ShowHoo.web.audience.converter;

import umc.ShowHoo.web.shows.entity.Shows;
import umc.ShowHoo.web.audience.dto.AudienceResponseDTO;

import java.util.List;

public class AudienceConverter {

    public static AudienceResponseDTO.getShowsListDTO toGetShowsListDTO(List<Shows> showsList){
        List<AudienceResponseDTO.getShowsDTO> getShowsDTOList = showsList.stream()
                .map(AudienceConverter::toGetShowsDTO).toList();

        return AudienceResponseDTO.getShowsListDTO.builder()
                .showsList(getShowsDTOList)
                .listSize(getShowsDTOList.size())
                .build();
    }

    public static AudienceResponseDTO.getShowsListDTO toGetLikedShowsListDTO(List<AudienceResponseDTO.getShowsDTO> showsList,List<Shows> shows){
        return AudienceResponseDTO.getShowsListDTO.builder()
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
                .host(shows.getPerformerProfile().getName())
                .place(shows.getSpaceApply().getSpace().getName())
                .name(shows.getName())
                .poster(shows.getPoster())
                .date(shows.getDate())
                .time(shows.getTime())
                .cancelDate(shows.getCancelDate())
                .cancelTime(shows.getCancelTime())
                .descriptionImg(isExist?shows.getShowsDescription().getImg():null)
                .description(isExist?shows.getShowsDescription().getText():null)
                .runningTime(shows.getRunningTime())
                .ticketPrice(shows.getTicketPrice())
                .remainTicketNum(shows.getRemainTicketNum())
                .perMaxticket(shows.getPerMaxticket())
                .bank(shows.getBank())
                .accountHolder(shows.getAccountHolder())
                .accountNum(shows.getAccountNum())
                .build();
    }
}
