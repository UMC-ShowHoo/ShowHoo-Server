package umc.ShowHoo.web.spaceAdditionalService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import umc.ShowHoo.web.space.entity.Space;

@Entity
@Getter
@Setter
public class SpaceAdditionalService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; //서비스명
    private Boolean isSelected; // 선택여부

    @ManyToOne
    @JoinColumn(name = "space_id")
    private Space space;
}