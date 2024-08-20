package umc.ShowHoo.web.shows.converter;

import umc.ShowHoo.web.shows.dto.ShowsRequestDTO;
import umc.ShowHoo.web.shows.dto.ShowsResponseDTO;
import umc.ShowHoo.web.shows.entity.Shows;
import umc.ShowHoo.web.spaceApply.entity.SpaceApply;

public class ShowsConverter {

    public static Shows toShowsSpaceApply(SpaceApply spaceApply){
        return Shows.builder()
                .spaceApply(spaceApply)
                .build();
    }

    public static ShowsResponseDTO.postShowDTO toPostShowDTO(Shows shows){
        return ShowsResponseDTO.postShowDTO.builder()
                .showId(shows.getId())
                .build();
    }

    public static Shows toShowInfo(ShowsRequestDTO.ShowInfoDTO dto,Shows shows){
        shows.setName(dto.getName());
        shows.setShowAge(dto.getShowAge());
        shows.setDate(dto.getDate());
        shows.setPoster(dto.getPosterUrl());
        shows.setRunningTime(dto.getRunningTime());
        shows.setTime(dto.getTime());
        shows.setCancelDate(dto.getCancelDate());
        shows.setCancelTime(dto.getCancelTime());
        return shows;

    }

    public static Shows toTicketInfo(ShowsRequestDTO.ticketInfoDTO dto,Shows shows){
        shows.setBank(dto.getBank());
        shows.setAccountHolder(dto.getAccountHolder());
        shows.setAccountNum(dto.getAccountNum());
        shows.setTicketNum(dto.getTicketNum());
        shows.setRemainTicketNum(dto.getTicketNum());
        shows.setTicketPrice(dto.getTicketPrice());
        shows.setPerMaxticket(dto.getPerMaxticket());

        return shows;
    }

    public static Shows toRequirement(ShowsRequestDTO.requirementDTO dto,Shows shows){
        shows.setRequirement(dto.getRequirement());

        return shows;
    }


    public static ShowsResponseDTO.ShowinfoDTO toShowsDTO(Shows shows) {
        return ShowsResponseDTO.ShowinfoDTO.builder()
                .shows_id(shows.getId())
                .name(shows.getName())
                .showAge(shows.getShowAge())
                .date(shows.getDate())
                .runningTime(shows.getRunningTime())
                .time(shows.getTime())
                .ticketPrice(shows.getTicketPrice())
                .perMaxticket(shows.getPerMaxticket())
                .bank(shows.getBank())
                .accountHolder(shows.getAccountHolder())
                .accountNum(shows.getAccountNum())
                .build();
        }

    public static ShowsResponseDTO.ShowPosterDTO toshowPosterDTO(Shows shows){
        return ShowsResponseDTO.ShowPosterDTO.builder()
                .poster(shows.getPoster())
                .build();
    }

    public static ShowsResponseDTO.ShowRequirementDTO torequirementDTO(Shows shows){
        return ShowsResponseDTO.ShowRequirementDTO.builder()
                .requirement(shows.getRequirement())
                .build();
    }

    public static ShowsResponseDTO.ShowDateDTO toShowDateDTO(String showDate, String dDay){
        return ShowsResponseDTO.ShowDateDTO.builder()
                .date(showDate)
                .dDay(dDay)
                .build();
    }

}


