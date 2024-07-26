package umc.ShowHoo.web.Shows.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.net.URL;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Shows {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private URL poster; //포스터 사진
    private String name; //공연 이름
    private String description;//공연 소개
    private String date; //공연 날짜
    private String time; //공연 시간
    private String runningTime; //러닝 타임
    private int showAge; //관람연령
}
