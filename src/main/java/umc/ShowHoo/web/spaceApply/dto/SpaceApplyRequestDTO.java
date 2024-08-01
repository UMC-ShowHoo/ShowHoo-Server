package umc.ShowHoo.web.spaceApply.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

public class SpaceApplyRequestDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterDTO{
        private LocalDate date; //대관날짜
        private int audienceMin; //관람객 최소인원
        private int audienceMax; //관람객 최대인원
        private Long performerProfileId; //공연자 프로필 id
        private Integer rentalFee; //대관비 날짜에 따른 대관비
        private Integer rentalSum; // 대관비 + 추가서비스 비용 총 합계
        private List<Long> selectedAdditionalServices; // 선택된 추가 서비스 아이디 목록
    }
}
