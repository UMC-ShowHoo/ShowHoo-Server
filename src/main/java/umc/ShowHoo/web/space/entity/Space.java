package umc.ShowHoo.web.space.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.ShowHoo.common.BaseEntity;
import umc.ShowHoo.web.holiday.entity.Holiday;
import umc.ShowHoo.web.peakSeasonRentalFee.entity.PeakSeasonRentalFee;
import umc.ShowHoo.web.rentalFee.entity.RentalFee;
import umc.ShowHoo.web.rentalFile.entity.RentalFile;
import umc.ShowHoo.web.spaceAdditionalService.entity.SpaceAdditionalService;
import umc.ShowHoo.web.spaceApply.entity.SpaceApply;
import umc.ShowHoo.web.spacePhoto.entity.SpacePhoto;
import umc.ShowHoo.web.spacePrefer.entity.SpacePrefer;
import umc.ShowHoo.web.spaceReview.entity.SpaceReview;
import umc.ShowHoo.web.spaceUser.entity.SpaceUser;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Space extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name; // 공연장 이름
    @Column(nullable = false)
    private String description; // 공연장 소개
    @Column(nullable = false)
    private String rentalHours; // 대관 시간
    @Column(nullable = false)
    private String location; // 공연장 위치
    @Column(nullable = false)
    private String area; // 공연장 면적
    @Column(nullable = false)
    private Integer seatingCapacity; // 좌석 수용인원
    @Column(nullable = false)
    private Integer standingCapacity; // 입석 수용인원
    private String soundEquipment; //음향 장비 정보 - 사진
    private String lightingEquipment; //조명 장비 정보 -사진
    private String stageMachinery; //무대 장치 -사진
    private String spaceDrawing; //공연장 도면 -사진
    private String spaceStaff; //공연장 인력 가이드 -사진
    private String spaceSeat; //좌석 배치도 -사진
    private String bankName; //은행명
    private String bankAccount; //계좌번호
    private String bankOwner; //예금주
    private double latitude;
    private double longitude;
    @Column(nullable = false)
    private String notice; // 유의사항
    private Double grade; // 평점
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SpaceType spaceType; // 공연장 종류

    @ManyToOne
    @JoinColumn(name = "space_user_id")
    private SpaceUser spaceUser;


    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL)
    private List<SpacePhoto> photos;

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL)
    private List<RentalFee> rentalFees;

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL)
    private List<PeakSeasonRentalFee> peakSeasonRentalFees;

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL)
    private List<Holiday> holidays;

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL)
    private List<SpaceAdditionalService> AdditionalServices;

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL)
    private List<SpacePrefer> spacePrefers;

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL)
    private List<SpaceApply> spaceApplies;

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL)
    private List<SpaceReview> spaceReviews;

    public void updateGrade() {
        if (spaceReviews != null && !spaceReviews.isEmpty()) {
            this.grade = spaceReviews.stream()
                    .mapToDouble(SpaceReview::getGrade)
                    .average()
                    .orElse(0.0);
        } else {
            this.grade = 0.0;
        }
    }
}
