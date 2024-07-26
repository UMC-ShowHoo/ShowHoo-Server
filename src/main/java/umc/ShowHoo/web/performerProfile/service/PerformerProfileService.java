package umc.ShowHoo.web.performerProfile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.ShowHoo.aws.s3.AmazonS3Manager;
import umc.ShowHoo.aws.s3.Uuid;
import umc.ShowHoo.web.performer.entity.Performer;
import umc.ShowHoo.web.performerProfile.dto.PerformerProfileRequestDTO;
import umc.ShowHoo.web.performerProfile.entity.PerformerProfile;
import umc.ShowHoo.web.performerProfile.entity.ProfileImage;
import umc.ShowHoo.web.performerProfile.repository.PerformerProfileRepository;
import umc.ShowHoo.web.performer.repository.PerformerRepository;
import umc.ShowHoo.aws.s3.UuidRepository;
import umc.ShowHoo.web.performerProfile.converter.PerformerProfileConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PerformerProfileService {
    private final AmazonS3Manager amazonS3Manager;
    private final PerformerProfileRepository performerProfileRepository;
    private final PerformerRepository performerRepository;
    private final UuidRepository uuidRepository;

    public PerformerProfile createProfile(Long performerUserId, PerformerProfileRequestDTO.CreateProfileDTO profileDTO) {
        Performer performer = performerRepository.findById(performerUserId)
                .orElseThrow(() -> new IllegalArgumentException("Performer not found"));

        PerformerProfile performerProfile = PerformerProfileConverter.toCreateProfile(profileDTO);
        performerProfile.setPerformer(performer);

        List<ProfileImage> profileImages = new ArrayList<>();
        for (PerformerProfileRequestDTO.PerformerProfileImageDTO imageDTO : profileDTO.getPerformerProfileImages()) {
            String uuid = UUID.randomUUID().toString();
            Uuid savedUuid = uuidRepository.save(Uuid.builder().uuid(uuid).build());

            String keyName = amazonS3Manager.generatePerformerProfileImageKeyName(savedUuid);
            String imageUrl = amazonS3Manager.uploadFile(keyName, imageDTO.getProfileImage());

            ProfileImage profileImage = ProfileImage.builder()
                    .profileImageUrl(imageUrl)
                    .performerProfile(performerProfile)
                    .build();
            profileImages.add(profileImage);
        }
        performerProfile.setProfileImages(profileImages);

        return performerProfileRepository.save(performerProfile);
    }
}
