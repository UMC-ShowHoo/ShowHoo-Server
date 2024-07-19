package umc.ShowHoo.web.rentalFee.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import umc.ShowHoo.web.space.entity.Space;

@Entity
@Getter
@Setter
@Builder
public class RentalFee {
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
