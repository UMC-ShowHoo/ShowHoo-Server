package umc.ShowHoo.web.spaceApply.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.ShowHoo.web.performer.entity.Performer;
import umc.ShowHoo.web.selectedAdditionalService.entity.SelectedAdditionalService;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.spacePrefer.entity.SpacePrefer;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SpaceApply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date; //대관날짜
    private int audienceMin; //관람객 최소인원
    private int audienceMax; //관람객 최대인원
    private Long performerProfileId; //공연자 프로필 id
    private int status; //대관승인상태 - 대관승인상태가 대관신청, 대관완료, 공연완료, 대관신청거절 4가지

    @ManyToOne
    @JoinColumn(name = "performer_id")
    private Performer performer;

    @ManyToOne
    @JoinColumn(name = "space_id")
    private Space space;

    @OneToMany(mappedBy = "spaceApply", cascade = CascadeType.ALL)
    private List<SelectedAdditionalService> selectedAdditionalServices;
}
