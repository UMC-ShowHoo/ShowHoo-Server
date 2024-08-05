package umc.ShowHoo.web.shows.converter;

import umc.ShowHoo.web.shows.dto.ShowsRequestDTO;
import umc.ShowHoo.web.shows.dto.ShowsResponseDTO;
import umc.ShowHoo.web.shows.entity.Shows;

public class ShowsConverter {

    public static ShowsResponseDTO.postShowDTO toPostShowDTO(Shows shows){
        return ShowsResponseDTO.postShowDTO.builder()
                .showId(shows.getId())
                .build();
    }

    public static Shows toShowInfo(ShowsRequestDTO.ShowInfoDTO dto, String poster){
        return Shows.builder()
                .name(dto.getName())
                .showAge(dto.getShowAge())
                .date(dto.getDate())
                .poster(poster)
                .runningTime(dto.getRunningTime())
                .time(dto.getTime())
                .build();
    }

    public static Shows toTicketInfo(ShowsRequestDTO.ticketInfoDTO dto,Shows shows){
        shows.setBank(dto.getBank());
        shows.setAccountHolder(dto.getAccountHolder());
        shows.setAccountNum(dto.getAccountNum());
        shows.setTicketNum(dto.getTicketNum());
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
    }

