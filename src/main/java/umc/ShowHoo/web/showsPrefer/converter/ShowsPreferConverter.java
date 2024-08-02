package umc.ShowHoo.web.showsPrefer.converter;

import umc.ShowHoo.web.Shows.entity.Shows;
import umc.ShowHoo.web.audience.entity.Audience;
import umc.ShowHoo.web.showsPrefer.dto.ShowsPreferResponseDTO;
import umc.ShowHoo.web.showsPrefer.entity.ShowsPrefer;

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

}
