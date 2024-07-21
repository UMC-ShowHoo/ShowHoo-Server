package umc.ShowHoo.web.spacePhoto.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.ShowHoo.web.space.entity.Space;

import java.net.URL;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SpacePhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private URL photoUrl;

    @ManyToOne
    @JoinColumn(name = "space_id")
    private Space space;
}
