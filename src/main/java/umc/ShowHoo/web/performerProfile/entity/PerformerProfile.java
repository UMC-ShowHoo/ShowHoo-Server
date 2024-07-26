package umc.ShowHoo.web.performerProfile.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.ShowHoo.web.performer.entity.Performer;

import java.util.List;

@Getter@Setter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PerformerProfile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String team;

    private String introduction;

    private String posterImage;

    @ManyToOne @JoinColumn(name = "performer_id")
    private Performer performer;
}
