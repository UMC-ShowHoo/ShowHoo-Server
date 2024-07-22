package umc.ShowHoo.web.performer.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import umc.ShowHoo.web.performerProfile.entity.PerformerProfile;

import java.util.List;

@Getter
@Setter
@Builder
@Entity
public class Performer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "performer",cascade = CascadeType.ALL)
    private List<PerformerProfile> profiles;
}
