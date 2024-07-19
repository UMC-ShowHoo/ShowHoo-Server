package umc.ShowHoo.web.spacePhoto.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import umc.ShowHoo.web.space.entity.Space;

import java.net.URL;

@Entity
@Getter
@Setter
@Builder
public class SpacePhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private URL photoUrl;

    @ManyToOne
    @JoinColumn(name = "space_id")
    private Space space;
}
