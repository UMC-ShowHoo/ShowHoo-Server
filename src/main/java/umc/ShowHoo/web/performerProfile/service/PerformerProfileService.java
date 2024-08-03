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
import umc.ShowHoo.web.performerProfile.dto.PerformerProfileResponseDTO;
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
import java.util.stream.Collectors;

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

    public PerformerProfile createProfile(Long performerUserId, PerformerProfileRequestDTO.CreateProfileDTO profileDTO) {
        Performer performer = performerRepository.findById(performerUserId)
                .orElseThrow(() -> new IllegalArgumentException("Performer not found"));

        PerformerProfile performerProfile = PerformerProfileConverter.toCreateProfile(profileDTO);
        performerProfile.setPerformer(performer);

        if (profileDTO.getProfileImageUrls() != null && !profileDTO.getProfileImageUrls().isEmpty()) {
            List<ProfileImage> profileImageList = new ArrayList<>();
            for (String imageUrl : profileDTO.getProfileImageUrls()) {
                ProfileImage profileImage = ProfileImage.builder()
                        .profileImageUrl(imageUrl)
                        .performerProfile(performerProfile)
                        .build();
                profileImageList.add(profileImage);
            }
            performerProfile.setProfileImages(profileImageList);
        }

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

    public void addProfileImage(Long performerUserId, Long profileId, PerformerProfileRequestDTO.AddProfileImageDTO requestDTO) {
        Performer performer = performerRepository.findById(performerUserId)
                .orElseThrow(() -> new IllegalArgumentException("Performer not found"));

        PerformerProfile existingProfile = performerProfileRepository.findByIdAndPerformer(profileId, performer)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found or does not belong to the performer"));

        // UUID 생성
        String uuid = UUID.randomUUID().toString();
        Uuid savedUuid = uuidRepository.save(Uuid.builder().uuid(uuid).build());

        // s3 업로드 후 url 가져옴
        String imageUrl = amazonS3Manager.uploadFile(amazonS3Manager.generatePerformerProfileImageKeyName(savedUuid), requestDTO.getProfileImage());

        // db에 url 저장
        ProfileImage profileImage = ProfileImage.builder()
                .profileImageUrl(imageUrl)
                .performerProfile(existingProfile)
                .build();

        profileImageRepository.save(profileImage);
        log.info("Added new image to profile ID {}: {}", profileId, imageUrl);
    }

    public void deleteProfile(Long performerUserId, Long profileId) {
        Performer performer = performerRepository.findById(performerUserId)
                .orElseThrow(() -> new IllegalArgumentException("Performer not found"));

        PerformerProfile existingProfile = performerProfileRepository.findByIdAndPerformer(profileId, performer)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found or does not belong to the performer"));

        // S3에서 이미지 삭제
        existingProfile.getProfileImages().forEach(profileImage -> {
            amazonS3Manager.deleteImage(profileImage.getProfileImageUrl());
            log.info("Deleted image from S3: {}", profileImage.getProfileImageUrl());
        });

        // 데이터베이스에서 프로필과 연결된 이미지 삭제
        profileImageRepository.deleteAll(existingProfile.getProfileImages());
        log.info("Deleted profile images from DB for profile ID: {}", profileId);

        // 데이터베이스에서 프로필 삭제
        performerProfileRepository.delete(existingProfile);
        log.info("Deleted profile from DB: {}", profileId);
    }

    public List<PerformerProfileResponseDTO.ProfileDTO> getAllProfiles(Long performerUserId) {
        Performer performer = performerRepository.findById(performerUserId)
                .orElseThrow(() -> new IllegalArgumentException("Performer not found"));

        List<PerformerProfile> profiles = performerProfileRepository.findByPerformer(performer);

        return profiles.stream()
                .map(PerformerProfileConverter::toGetProfile)
                .collect(Collectors.toList());
    }

    public List<String> uploadProfileImages(List<MultipartFile> profileImages) {
        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile profileImage : profileImages) {
            String uuid = UUID.randomUUID().toString();
            Uuid savedUuid = uuidRepository.save(Uuid.builder().uuid(uuid).build());
            String keyName = amazonS3Manager.generatePerformerProfileImageKeyName(savedUuid);
            String imageUrl = amazonS3Manager.uploadFile(keyName, profileImage);
            imageUrls.add(imageUrl);
        }
        return imageUrls;
    }
}
