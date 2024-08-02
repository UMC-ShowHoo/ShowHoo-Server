package umc.ShowHoo.web.showsPrefer.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.ShowHoo.web.Shows.entity.Shows;
import umc.ShowHoo.web.audience.entity.Audience;
import umc.ShowHoo.web.common.BaseEntity;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ShowsPrefer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shows_id")
    private Shows shows;

    @ManyToOne
    @JoinColumn(name = "audience_id")
    private Audience audience;

}
