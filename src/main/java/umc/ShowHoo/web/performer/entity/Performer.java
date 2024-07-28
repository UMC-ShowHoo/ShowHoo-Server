package umc.ShowHoo.web.performer.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.ShowHoo.web.Show.entity.Shows;
import umc.ShowHoo.web.performerProfile.entity.PerformerProfile;

import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Performer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "performer",cascade = CascadeType.ALL)
    private List<PerformerProfile> profiles;

    @OneToMany(mappedBy = "performer", cascade = CascadeType.ALL)
    private List<Shows> showsList;
}
