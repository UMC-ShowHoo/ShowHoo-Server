package umc.ShowHoo.web.performerProfile.converter;

import umc.ShowHoo.web.performerProfile.dto.PerformerProfileRequestDTO;
import umc.ShowHoo.web.performerProfile.dto.PerformerProfileResponseDTO;
import umc.ShowHoo.web.performerProfile.entity.PerformerProfile;
import umc.ShowHoo.web.performerProfile.entity.ProfileImage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PerformerProfileConverter {
    public static PerformerProfile toCreateProfile(PerformerProfileRequestDTO.CreateProfileDTO dto) {
        return PerformerProfile.builder()
                .team(dto.getTeam())
                .name(dto.getName())
                .phoneNumber(dto.getPhoneNumber())
                .introduction(dto.getIntroduction())
                .build();
    }

    public static PerformerProfile toUpdateProfileText(PerformerProfileRequestDTO.UpdateProfileTextDTO dto, PerformerProfile existingProfile) {
        existingProfile.setName(dto.getName());
        existingProfile.setTeam(dto.getTeam());
        existingProfile.setPhoneNumber(dto.getPhoneNumber());
        existingProfile.setIntroduction(dto.getIntroduction());
        return existingProfile;
    }

    public static PerformerProfileResponseDTO.ProfileDTO toGetProfile(PerformerProfile profile) {
        return PerformerProfileResponseDTO.ProfileDTO.builder()
                .id(profile.getId())
                .team(profile.getTeam())
                .introduction(profile.getIntroduction())
                .profileImages(profile.getProfileImages().stream()
                        .map(image -> PerformerProfileResponseDTO.ProfileImageDTO.builder()
                                .id(image.getId())
                                .profileImageUrl(image.getProfileImageUrl())
                                .build())
                        .collect(Collectors.toList()))
                .createdAt(profile.getCreatedAt())
                .build();
    }

}
