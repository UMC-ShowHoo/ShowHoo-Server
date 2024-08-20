package umc.ShowHoo.web.showsDescription.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import umc.ShowHoo.web.shows.entity.Shows;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ShowsDescription {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(max=200)
    private String text;

    private String img; //멀티파트

    @OneToOne @JoinColumn(name = "shows_id")
    private Shows shows;
}
