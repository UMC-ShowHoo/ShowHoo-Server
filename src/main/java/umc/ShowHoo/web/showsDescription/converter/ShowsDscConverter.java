package umc.ShowHoo.web.showsDescription.converter;

import umc.ShowHoo.web.shows.entity.Shows;
import umc.ShowHoo.web.showsDescription.dto.ShowsDscRequestDTO;
import umc.ShowHoo.web.showsDescription.dto.ShowsDscResponseDTO;
import umc.ShowHoo.web.showsDescription.entity.ShowsDescription;

public class ShowsDscConverter {

    public static ShowsDscResponseDTO.PostDscDTO toPostDscDTO(ShowsDescription showsDescription){
        return ShowsDscResponseDTO.PostDscDTO.builder()
                .showId(showsDescription.getShows().getId())
                .build();
    }

    public static ShowsDescription toShowDsc(ShowsDscRequestDTO.DescriptionDTO dto, String imgUrl, Shows shows){
        return ShowsDescription.builder()
                .shows(shows)
                .text(dto.getText())
                .img(imgUrl)
                .build();
    }
}
