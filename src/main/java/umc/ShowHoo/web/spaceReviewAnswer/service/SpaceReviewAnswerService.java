package umc.ShowHoo.web.spaceReviewAnswer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

        spaceReviewAnswerRepository.save(spaceReviewAnswer);
    }
}
