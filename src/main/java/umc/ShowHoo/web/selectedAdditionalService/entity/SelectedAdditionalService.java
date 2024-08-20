package umc.ShowHoo.web.selectedAdditionalService.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.ShowHoo.web.space.entity.Space;
import umc.ShowHoo.web.spaceAdditionalService.entity.SpaceAdditionalService;
import umc.ShowHoo.web.spaceApply.entity.SpaceApply;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SelectedAdditionalService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long serviceId; // 선택된 추가서비스 아이디 값

    @ManyToOne
    @JoinColumn(name = "spaceApply_id")
    private SpaceApply spaceApply;

    @ManyToOne
    @JoinColumn(name = "spaceadditional_service_id")
    private SpaceAdditionalService spaceAdditionalService;

}
