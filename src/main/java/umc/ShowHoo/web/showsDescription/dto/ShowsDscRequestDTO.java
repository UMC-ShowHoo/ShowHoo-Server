package umc.ShowHoo.web.showsDescription.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ShowsDscRequestDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DescriptionDTO{
        private Long showId;
        //private String img; 멀티파트
        private String text;
    }
}
