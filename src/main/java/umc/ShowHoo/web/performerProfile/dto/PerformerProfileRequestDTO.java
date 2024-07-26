package umc.ShowHoo.web.performerProfile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import umc.ShowHoo.web.spacePhoto.dto.SpacePhotoRequestDTO;

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

        private List<PerformerProfileImageDTO> performerProfileImages;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PerformerProfileImageDTO{
        private MultipartFile profileImage;
    }

}
