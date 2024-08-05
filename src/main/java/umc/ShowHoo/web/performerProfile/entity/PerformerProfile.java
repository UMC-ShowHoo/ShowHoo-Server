package umc.ShowHoo.web.performerProfile.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.ShowHoo.web.Shows.entity.Shows;
import umc.ShowHoo.web.common.BaseEntity;
import umc.ShowHoo.web.performer.entity.Performer;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PerformerProfile extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String team;

    private String phoneNumber;

    private String introduction;

    @OneToMany(mappedBy = "performerProfile", cascade = CascadeType.ALL)
    private List<Shows> showsList;

    @OneToMany(mappedBy = "performerProfile",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfileImage> profileImages;

    @ManyToOne @JoinColumn(name = "performer_id")
    private Performer performer;
}
