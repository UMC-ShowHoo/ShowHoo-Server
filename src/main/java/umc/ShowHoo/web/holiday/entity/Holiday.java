package umc.ShowHoo.web.holiday.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.ShowHoo.web.space.entity.Space;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Holiday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "space_id")
    private Space space;
}
