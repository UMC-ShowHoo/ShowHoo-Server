package umc.ShowHoo.web.book.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class BookRequestDTO {

    @Getter
    public static class postDTO{
        @NotNull
        String name;
        @NotNull
        String phoneNum;
    }
}
