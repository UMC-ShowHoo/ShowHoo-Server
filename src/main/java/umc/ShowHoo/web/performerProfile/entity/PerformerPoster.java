package umc.ShowHoo.web.performerProfile.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PerformerPoster {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String posterUrl;

    @ManyToOne @JoinColumn(name = "performerProfile_id")
    private PerformerProfile performerProfile;
}
