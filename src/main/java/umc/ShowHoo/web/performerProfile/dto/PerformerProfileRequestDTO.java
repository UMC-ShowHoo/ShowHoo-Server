package umc.ShowHoo.web.performerProfile.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import umc.ShowHoo.web.spacePhoto.dto.SpacePhotoRequestDTO;

import java.util.ArrayList;
import java.util.List;

public class PerformerProfileRequestDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateProfileDTO{
        //소속, 소개, 프로필 사진
        private String team;
        private String introduction;

        private final List<PerformerProfileImageDTO> performerProfileImages = new ArrayList<>();
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PerformerProfileImageDTO{
        private MultipartFile profileImage;
    }

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateProfileTextDTO {
        private String team;
        private String introduction;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteProfileImageDTO {
        private String profileImageUrl;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class AddProfileImageDTO {
        private MultipartFile profileImage;
    }


}
