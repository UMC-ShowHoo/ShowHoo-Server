package umc.ShowHoo.web.performerProfile.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Builder
public class PerformerPoster {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String posterUrl;

    @ManyToOne @JoinColumn(name = "performerProfile_id")
    private PerformerProfile performerProfile;
}
