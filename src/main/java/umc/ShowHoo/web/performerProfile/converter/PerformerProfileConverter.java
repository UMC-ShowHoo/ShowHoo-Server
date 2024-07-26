package umc.ShowHoo.web.performerProfile.converter;

import umc.ShowHoo.web.performerProfile.dto.PerformerProfileRequestDTO;
import umc.ShowHoo.web.performerProfile.entity.PerformerProfile;
import umc.ShowHoo.web.performerProfile.entity.ProfileImage;

import java.util.List;
import java.util.stream.Collectors;

public class PerformerProfileConverter {
    public static PerformerProfile toCreateProfile(PerformerProfileRequestDTO.CreateProfileDTO dto) {
        PerformerProfile performerProfile = PerformerProfile.builder()
                .team(dto.getTeam())
                .introduction(dto.getIntroduction())
                .build();

        List<ProfileImage> profileImages = dto.getPerformerProfileImages().stream()
                .map(imageDTO -> ProfileImage.builder()
                        .profileImageUrl(null)  // URL은 후에 설정
                        .performerProfile(performerProfile)
                        .build())
                .collect(Collectors.toList());

        performerProfile.setProfileImages(profileImages);

        return performerProfile;
    }

}
