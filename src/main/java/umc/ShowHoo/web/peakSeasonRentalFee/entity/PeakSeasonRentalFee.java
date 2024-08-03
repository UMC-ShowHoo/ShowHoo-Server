package umc.ShowHoo.web.peakSeasonRentalFee.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.ShowHoo.web.rentalFee.entity.DayOfWeek;
import umc.ShowHoo.web.space.entity.Space;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PeakSeasonRentalFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    private Integer fee;

    @ManyToOne
    @JoinColumn(name = "space_id")
    private Space space;
}
