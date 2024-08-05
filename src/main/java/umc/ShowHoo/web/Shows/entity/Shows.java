package umc.ShowHoo.web.Shows.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.ShowHoo.web.book.entity.Book;
import umc.ShowHoo.web.performer.entity.Performer;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import umc.ShowHoo.web.showsDescription.entity.ShowsDescription;
import umc.ShowHoo.web.showsPrefer.entity.ShowsPrefer;

import umc.ShowHoo.web.rentalFile.entity.RentalFile;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Shows {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String requirement;
    private String poster; //포스터 사진
    private String name; //공연 이름
    private String date; //공연 날짜
    private String time; //공연 시간
    private String runningTime; //러닝 타임
    private Integer showAge; //관람연령

    private String ticketPrice; //티켓 가격
    private Integer ticketNum; //티켓 발행 매수
    private Integer perMaxticket; //티켓 인당 구매 제한
    private String bank; //은행명
    private String accountHolder; //예금주
    private String accountNum; //계좌번호



    @ManyToOne @JoinColumn(name = "performer_id")
    private Performer performer;

    @OneToOne(mappedBy = "shows")
    private RentalFile rentalFile;

    @OneToMany(mappedBy = "shows", cascade = CascadeType.ALL)
    private List<Book> bookList;

    @OneToMany(mappedBy = "shows", cascade = CascadeType.ALL)
    private List<ShowsPrefer> preferList;

    @OneToOne(mappedBy = "shows")
    private ShowsDescription showsDescription;
}
