package umc.ShowHoo.web.performerProfile.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class PerformerProfileResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateProfileDTO{
        LocalDateTime createdAt;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProfileDTO{
        private Long id;
        private String team;
        private String introduction;
        private List<ProfileImageDTO> profileImages;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProfileImageDTO {
        private Long id;
        private String profileImageUrl;
    }
}
