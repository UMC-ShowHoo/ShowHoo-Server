package umc.ShowHoo.web.Show.converter;

import umc.ShowHoo.web.Show.dto.ShowsRequestDTO;
import umc.ShowHoo.web.Show.dto.ShowsResponseDTO;
import umc.ShowHoo.web.Show.entity.Shows;

public class ShowsConverter {

    public static Shows toEntity(ShowsRequestDTO dto){
        return Shows.builder()
                .requirement(dto.getRequirement())
                .name(dto.getName())
                .showAge(dto.getShowAge())
                .date(dto.getDate())
                .description(dto.getDescription())
                .poster(dto.getPoster())
                .runningTime(dto.getRunningTime())
                .time(dto.getTime())
                .perMaxticket(dto.getPerMaxticket())
                .ticketPrice(dto.getTicketPrice())
                .bank(dto.getBank())
                .accountHolder(dto.getAccountHolder())
                .accountNum(dto.getAccountNum())
                .build();
    }


    public static ShowsResponseDTO.ShowinfoDTO toShowsDTO(Shows shows) {
        return ShowsResponseDTO.ShowinfoDTO.builder()
                .shows_id(shows.getId())
                .name(shows.getName())
                .showAge(shows.getShowAge())
                .date(shows.getDate())
                .description(shows.getDescription())
                .poster(shows.getPoster())
                .runningTime(shows.getRunningTime())
                .time(shows.getTime())
                .ticketPrice(shows.getTicketPrice())
                .perMaxticket(shows.getPerMaxticket())
                .bank(shows.getBank())
                .accountHolder(shows.getAccountHolder())
                .accountNum(shows.getAccountNum())
                .build();
        }

        public static ShowsResponseDTO.ShowRequirementDTO torequirementDTO(Shows shows){
        return ShowsResponseDTO.ShowRequirementDTO.builder()
                .requirement(shows.getRequirement())
                .build();
        }
    }


