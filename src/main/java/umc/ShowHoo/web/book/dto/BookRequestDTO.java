package umc.ShowHoo.web.book.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class BookRequestDTO {

    @Getter
    public static class postDTO{
        @NotNull
        String name;
        @NotNull
        String phoneNum;
        @Min(1)
        Integer ticketNum;
        @NotNull
        Long audienceId;
        @NotNull
        Long showsId;
    }

    @Getter
    public static class deleteBookDTO{
        @NotNull
        String name;
        @NotNull
        String bankName;
        @NotNull
        String account;
        @NotNull
        String reason;
    }
}
