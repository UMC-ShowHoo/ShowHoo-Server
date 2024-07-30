package umc.ShowHoo.web.spaceApply.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.ShowHoo.web.performer.entity.Performer;
import umc.ShowHoo.web.space.entity.Space;

import java.time.LocalDate;

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
    private boolean status; //대관승인상태

    @ManyToOne
    @JoinColumn(name = "performer_id")
    private Performer performer;

    @ManyToOne
    @JoinColumn(name = "space_id")
    private Space space;
}
