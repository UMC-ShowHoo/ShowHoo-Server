package umc.ShowHoo.web.spaceReview.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import umc.ShowHoo.aws.s3.AmazonS3Manager;
import umc.ShowHoo.aws.s3.Uuid;
import umc.ShowHoo.aws.s3.UuidRepository;
import umc.ShowHoo.web.member.entity.Member;
import umc.ShowHoo.web.member.repository.MemberRepository;
import umc.ShowHoo.web.notification.service.NotificationService;
import umc.ShowHoo.web.performer.entity.Performer;
import umc.ShowHoo.web.performer.repository.PerformerRepository;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.space.repository.SpaceRepository;
import umc.ShowHoo.web.space.service.SpaceService;
import umc.ShowHoo.web.spaceApply.exception.handler.SpaceApplyHandler;
import umc.ShowHoo.web.spaceApply.repository.SpaceApplyRepository;
import umc.ShowHoo.web.spaceReview.converter.SpaceReviewConverter;
import umc.ShowHoo.web.spaceReview.dto.SpaceReviewRequestDTO;
import umc.ShowHoo.web.spaceReview.dto.SpaceReviewResponseDTO;
import umc.ShowHoo.web.spaceReview.entity.SpaceReview;
import umc.ShowHoo.web.spaceReview.entity.SpaceReviewImage;
import umc.ShowHoo.web.spaceReview.exception.handler.SpaceReviewHandler;
import umc.ShowHoo.web.spaceReview.repository.SpaceReviewImageRepository;
import umc.ShowHoo.web.spaceReview.repository.SpaceReviewRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SpaceReviewService {
    private final SpaceReviewRepository spaceReviewRepository;
    private final SpaceApplyRepository spaceApplyRepository;
    private final PerformerRepository performerRepository;
    private final MemberRepository memberRepository;
    private final SpaceReviewImageRepository spaceReviewImageRepository;
    private final SpaceRepository spaceRepository;
    private final SpaceReviewConverter spaceReviewConverter;
    private final AmazonS3Manager amazonS3Manager;
    private final UuidRepository uuidRepository;
    private final NotificationService notificationService;
    private final SpaceService spaceService;

    @Transactional
    public void createSpaceReview(Long spaceId, Long performerId, SpaceReviewRequestDTO.ReviewRegisterDTO reviewRegisterDTO) {
        boolean hasApplied = spaceApplyRepository.existsBySpaceIdAndPerformerId(spaceId, performerId);
        if (!hasApplied) {
            throw new SpaceReviewHandler(ErrorStatus.SPACE_REVIEW_PERMISSION_NOT_FOUND);
        }
        // Space 조회
        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new SpaceApplyHandler(ErrorStatus.SPACE_NOT_FOUND));

        // performerId == memberId이므로 performerId로 Member 조회
        Member member = memberRepository.findById(performerId)
                .orElseThrow(() -> new SpaceApplyHandler(ErrorStatus.MEMBER_NOT_FOUND));

        // performerId로 Performer도 조회
        Performer performer = performerRepository.findById(performerId)
                .orElseThrow(() -> new SpaceApplyHandler(ErrorStatus.PERFORMER_NOT_FOUND));

        // 리뷰 생성 및 저장
        SpaceReview spaceReview = spaceReviewConverter.toCreateSpaceReview(reviewRegisterDTO, space, performer);

        // Member 정보에서 memberName과 memberUrl 설정
        spaceReview.setMemberName(member.getName());
        spaceReview.setMemberUrl(member.getProfileimage());

        spaceReviewRepository.save(spaceReview);

        if (reviewRegisterDTO.getImageUrls() != null && !reviewRegisterDTO.getImageUrls().isEmpty()) {
            List<SpaceReviewImage> reviewImagesList = new ArrayList<>();
            for (String imageUrl : reviewRegisterDTO.getImageUrls()) {
                SpaceReviewImage reviewImage = spaceReviewConverter.toSpaceReviewImage(imageUrl, spaceReview);
                reviewImagesList.add(reviewImage);
            }
            spaceReviewImageRepository.saveAll(reviewImagesList);
        }

        notificationService.createSpaceReviewNotification(space, performer);// 알림 생성
        spaceService.updateSpaceGrade(spaceReview.getSpace()); // grade update
    }

    public void deleteSpaceReview(Long reviewId) {
        SpaceReview spaceReview = spaceReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        spaceReviewRepository.delete(spaceReview);
    }

    public List<SpaceReviewResponseDTO.ReviewPerformerDTO> getReviewsByPerformerId(Long performerId) {
        List<SpaceReview> reviews = spaceReviewRepository.findByPerformerId(performerId);

        return reviews.stream()
                .map(spaceReviewConverter::toGetPerformerReview)
                .collect(Collectors.toList());
    }

    public List<SpaceReviewResponseDTO.ReviewSpaceDTO> getReviewsBySpaceId(Long spaceId) {
        List<SpaceReview> reviews = spaceReviewRepository.findBySpaceId(spaceId);

        return reviews.stream()
                .map(spaceReviewConverter::toGetSpaceReview)
                .collect(Collectors.toList());
    }

    public List<String> uploadReviewImages(List<MultipartFile> reviewImages) {
        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile image : reviewImages) {
            String uuid = UUID.randomUUID().toString();
            Uuid savedUuid = uuidRepository.save(Uuid.builder().uuid(uuid).build());
            String keyName = amazonS3Manager.generateReviewKeyName(savedUuid);
            String imageUrl = amazonS3Manager.uploadFile(keyName, image);
            imageUrls.add(imageUrl);
        }
        return imageUrls;
    }
}
