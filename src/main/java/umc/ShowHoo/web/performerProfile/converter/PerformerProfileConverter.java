package umc.ShowHoo.web.performerProfile.converter;

import umc.ShowHoo.web.performerProfile.dto.PerformerProfileRequestDTO;
import umc.ShowHoo.web.performerProfile.entity.PerformerProfile;
import umc.ShowHoo.web.performerProfile.entity.ProfileImage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PerformerProfileConverter {
    public static PerformerProfile toCreateProfile(PerformerProfileRequestDTO.CreateProfileDTO dto) {
        PerformerProfile performerProfile = PerformerProfile.builder()
                .team(dto.getTeam())
                .introduction(dto.getIntroduction())
                .build();

        List<ProfileImage> profileImages = (dto.getPerformerProfileImages() != null ? dto.getPerformerProfileImages() : new ArrayList<>())
                .stream()
                .map(imageDTO -> ProfileImage.builder()
                        .profileImageUrl(null)  // URL은 후에 설정
                        .performerProfile(performerProfile)
                        .build())
                .collect(Collectors.toList());

        performerProfile.setProfileImages(profileImages);

        return performerProfile;
    }

    public static PerformerProfile toUpdateProfile(PerformerProfileRequestDTO.UpdateProfileDTO dto, PerformerProfile existingProfile) {
        existingProfile.setTeam(dto.getTeam());
        existingProfile.setIntroduction(dto.getIntroduction());

        // 기존 이미지 목록은 비워줍니다. 새 이미지로 대체할 것이기 때문입니다.
        existingProfile.getProfileImages().clear();

        List<ProfileImage> profileImages = (dto.getPerformerProfileImages() != null ? dto.getPerformerProfileImages() : new ArrayList<>())
                .stream()
                .map(image -> ProfileImage.builder()
                        .profileImageUrl("") // 나중에 S3에 업로드 후 URL을 설정
                        .performerProfile(existingProfile)
                        .build())
                .collect(Collectors.toList());

        existingProfile.setProfileImages(profileImages);

        return existingProfile;
    }

}
