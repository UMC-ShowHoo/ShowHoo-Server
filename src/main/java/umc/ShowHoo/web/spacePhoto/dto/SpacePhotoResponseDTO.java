package umc.ShowHoo.web.spacePhoto.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class SpacePhotoResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class urlDTO{
        private List<String> photoUrl;
    }
}
