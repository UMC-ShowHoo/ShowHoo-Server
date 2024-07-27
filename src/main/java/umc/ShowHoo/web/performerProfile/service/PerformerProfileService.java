package umc.ShowHoo.web.performerProfile.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
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
import umc.ShowHoo.web.performerProfile.repository.ProfileImageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PerformerProfileService {
    private final AmazonS3Manager amazonS3Manager;
    private final PerformerProfileRepository performerProfileRepository;
    private final PerformerRepository performerRepository;
    private final UuidRepository uuidRepository;
    private final ProfileImageRepository profileImageRepository;

    public PerformerProfile createProfile(Long performerUserId, PerformerProfileRequestDTO.CreateProfileDTO profileDTO, List<MultipartFile> profileImages) {
        Performer performer = performerRepository.findById(performerUserId)
                .orElseThrow(() -> new IllegalArgumentException("Performer not found"));

        PerformerProfile performerProfile = PerformerProfileConverter.toCreateProfile(profileDTO);
        performerProfile.setPerformer(performer);

        List<ProfileImage> profileImageList = new ArrayList<>();
        for (MultipartFile image : profileImages) {
            String uuid = UUID.randomUUID().toString();
            Uuid savedUuid = uuidRepository.save(Uuid.builder().uuid(uuid).build());

            String keyName = amazonS3Manager.generatePerformerProfileImageKeyName(savedUuid);
            String imageUrl = amazonS3Manager.uploadFile(keyName, image);

            ProfileImage profileImage = ProfileImage.builder()
                    .profileImageUrl(imageUrl)
                    .performerProfile(performerProfile)
                    .build();
            profileImageList.add(profileImage);
        }
        performerProfile.setProfileImages(profileImageList);

        return performerProfileRepository.save(performerProfile);
    }


    public void updateProfileText(Long performerUserId, Long profileId, PerformerProfileRequestDTO.UpdateProfileTextDTO dto) {
        Performer performer = performerRepository.findById(performerUserId)
                .orElseThrow(() -> new IllegalArgumentException("Performer not found"));

        PerformerProfile existingProfile = performerProfileRepository.findByIdAndPerformer(profileId, performer)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found or does not belong to the performer"));

        PerformerProfile updatedProfile = PerformerProfileConverter.toUpdateProfileText(dto, existingProfile);

        performerProfileRepository.save(updatedProfile);
    }


    @Transactional
    public void deleteProfileImageByUrl(String profileImageUrl) {
        ProfileImage profileImage = profileImageRepository.findByProfileImageUrl(profileImageUrl)
                .orElseThrow(() -> new IllegalArgumentException("Profile image not found"));

        // S3에서 이미지 삭제
        amazonS3Manager.deleteImage(profileImage.getProfileImageUrl());

        // 데이터베이스에서 이미지 삭제
        profileImageRepository.delete(profileImage);
        log.info("Deleted image from DB: {}", profileImage.getId());
    }
}
