package umc.ShowHoo.web.spaceReviewAnswer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.ShowHoo.web.notification.service.NotificationService;
import umc.ShowHoo.web.spaceReview.entity.SpaceReview;
import umc.ShowHoo.web.spaceReview.repository.SpaceReviewRepository;
import umc.ShowHoo.web.spaceReviewAnswer.dto.SpaceReviewAnswerRequestDTO;
import umc.ShowHoo.web.spaceReviewAnswer.entity.SpaceReviewAnswer;
import umc.ShowHoo.web.spaceReviewAnswer.repository.SpaceReviewAnswerRepository;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SpaceReviewAnswerService {

    private final SpaceReviewAnswerRepository spaceReviewAnswerRepository;
    private final SpaceReviewRepository spaceReviewRepository;
    private final NotificationService notificationService;

    public void createSpaceReviewAnswer(Long spaceId, Long reviewId, SpaceReviewAnswerRequestDTO.AnswerContentDTO answerContentDTO) {
        SpaceReview spaceReview = spaceReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        if (!spaceReview.getSpace().getId().equals(spaceId)) {
            throw new IllegalArgumentException("Review does not belong to the specified space");
        }

        SpaceReviewAnswer spaceReviewAnswer = SpaceReviewAnswer.builder()
                .content(answerContentDTO.getContent())
                .spaceReview(spaceReview)
                .build();

        notificationService.createSpaceReviewCommentNotification(spaceReview); // 알림 생성

        spaceReviewAnswerRepository.save(spaceReviewAnswer);
    }

    public void deleteSpaceReviewAnswer(Long reviewAnswerId) {
        SpaceReviewAnswer spaceReviewAnswer = spaceReviewAnswerRepository.findById(reviewAnswerId)
                .orElseThrow(() -> new IllegalArgumentException("Review answer not found"));

        spaceReviewAnswerRepository.delete(spaceReviewAnswer);
    }
}
