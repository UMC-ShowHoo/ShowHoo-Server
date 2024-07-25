package umc.ShowHoo.web.performerProfile.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import umc.ShowHoo.web.performer.entity.Performer;

import java.util.List;

@Getter@Setter
@Entity
@Builder
public class PerformerProfile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String team;

    private String introduction;

    @OneToMany(mappedBy = "performerProfile",cascade = CascadeType.ALL)
    private List<PerformerPoster> posters;

    @ManyToOne @JoinColumn(name = "performer_id")
    private Performer performer;
}
