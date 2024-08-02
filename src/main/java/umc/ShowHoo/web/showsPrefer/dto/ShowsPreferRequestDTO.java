package umc.ShowHoo.web.showsPrefer.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class ShowsPreferRequestDTO {

    @Getter
    public static class createDTO{
        @NotNull
        Long showsId;
        @NotNull
        Long audienceId;
    }
}
