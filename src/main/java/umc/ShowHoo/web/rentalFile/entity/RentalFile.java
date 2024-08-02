package umc.ShowHoo.web.rentalFile.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.ShowHoo.web.Shows.entity.Shows;
import umc.ShowHoo.web.space.entity.Space;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RentalFile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //공연장이 올리는 파일
    private String setListForm; //셋리스트 양식
    private String rentalTimeForm; //대관시간 양식
    private String addOrderForm; //추가 주문사항 양식

    //공연자가 올리는 파일
    private String setList; //셋리스트
    private String rentalTime; //대관시간
    private String addOrder; //추가 주문사항

    @OneToOne @JoinColumn(name = "shows_id")
    private Shows shows;

    @OneToOne @JoinColumn(name = "space_id")
    private Space space;
}
