package umc.ShowHoo.web.spacePrefer.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.ShowHoo.web.performer.entity.Performer;
import umc.ShowHoo.web.space.entity.Space;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SpacePrefer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "space_id")
    private Space space;

    @ManyToOne
    @JoinColumn(name = "performer_id")
    private Performer performer;
}
