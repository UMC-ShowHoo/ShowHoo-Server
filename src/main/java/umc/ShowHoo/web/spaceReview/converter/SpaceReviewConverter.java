package umc.ShowHoo.web.spaceReview.converter;

import org.springframework.stereotype.Component;
import umc.ShowHoo.web.performer.entity.Performer;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.spaceReview.dto.SpaceReviewRequestDTO;
import umc.ShowHoo.web.spaceReview.dto.SpaceReviewResponseDTO;
import umc.ShowHoo.web.spaceReview.entity.SpaceReview;
import umc.ShowHoo.web.spaceReview.entity.SpaceReviewImage;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SpaceReviewConverter {

    public SpaceReview toCreateSpaceReview(SpaceReviewRequestDTO.ReviewRegisterDTO reviewRegisterDTO, Space space, Performer performer) {
        return SpaceReview.builder()
                .grade(reviewRegisterDTO.getGrade())
                .content(reviewRegisterDTO.getContent())
                .space(space)
                .performer(performer)
                .build();
    }


    public SpaceReviewImage toSpaceReviewImage(String imageUrl, SpaceReview spaceReview) {
        return SpaceReviewImage.builder()
                .spaceReview(spaceReview)
                .imageUrl(imageUrl)
                .build();
    }

    public SpaceReviewResponseDTO.ReviewPerformerDTO toGetPerformerReview(SpaceReview spaceReview) {
        List<SpaceReviewResponseDTO.SpaceReviewAnswerDto> answers = spaceReview.getSpaceReviewAnswers().stream()
                .map(answer -> new SpaceReviewResponseDTO.SpaceReviewAnswerDto(
                        answer.getId(),
                        answer.getContent(),
                        answer.getUpdatedAt()))
                .collect(Collectors.toList());

        return new SpaceReviewResponseDTO.ReviewPerformerDTO(
                spaceReview.getId(),
                spaceReview.getGrade(),
                spaceReview.getContent(),
                answers
        );
    }

    public SpaceReviewResponseDTO.ReviewSpaceDTO toGetSpaceReview(SpaceReview spaceReview) {
        List<SpaceReviewResponseDTO.SpaceReviewAnswerDto> answers = spaceReview.getSpaceReviewAnswers().stream()
                .map(answer -> new SpaceReviewResponseDTO.SpaceReviewAnswerDto(
                        answer.getId(),
                        answer.getContent(),
                        answer.getUpdatedAt()
                ))
                .collect(Collectors.toList());

        // 이미지 URL 목록 생성
        List<String> imageUrls = spaceReview.getSpaceReviewImages().stream()
                .map(SpaceReviewImage::getImageUrl)  // SpaceReviewImage 엔티티에서 URL 추출
                .collect(Collectors.toList());


        return SpaceReviewResponseDTO.ReviewSpaceDTO.builder()
                .id(spaceReview.getId())
                .grade(spaceReview.getGrade())
                .content(spaceReview.getContent())
                .answers(answers)
                .imageUrls(imageUrls) //// 이미지 URL 추가
                .memberName(spaceReview.getMemberName()) // 리뷰 작성자 이름 추가
                .memberUrl(spaceReview.getMemberUrl()) // 리뷰 작성자 프로필 이미지 URL 추가
                .updatedAt(spaceReview.getUpdatedAt()) // 리뷰의 수정 시간 추가
                .build();


    }
}
