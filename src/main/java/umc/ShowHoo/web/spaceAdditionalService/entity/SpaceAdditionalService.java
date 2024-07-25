package umc.ShowHoo.web.spaceAdditionalService.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.ShowHoo.web.space.entity.Space;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpaceAdditionalService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; //서비스명

    @ManyToOne
    @JoinColumn(name = "space_id")
    private Space space;
}