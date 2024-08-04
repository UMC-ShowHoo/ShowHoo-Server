package umc.ShowHoo.web.showsDescription.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.ShowHoo.web.Shows.entity.Shows;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ShowsDescription {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private String img; //멀티파트

    @OneToOne @JoinColumn(name = "shows_id")
    private Shows shows;
}
