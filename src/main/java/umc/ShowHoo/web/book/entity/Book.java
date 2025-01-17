package umc.ShowHoo.web.book.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import umc.ShowHoo.web.shows.entity.Shows;
import umc.ShowHoo.web.audience.entity.Audience;
import umc.ShowHoo.web.cancelBook.entity.CancelBook;
import umc.ShowHoo.common.BaseEntity;

@Entity
@Getter
@Setter
@Builder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Book extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phoneNum;

    private Integer ticketNum;

    private String payment;

    private Boolean entrance;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(columnDefinition = "VARCHAR(15) DEFAULT 'BOOK'")
    private BookStatus status = BookStatus.BOOK;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(columnDefinition = "VARCHAR(15) DEFAULT 'CONFIRMING'")
    private BookDetail detail = BookDetail.CONFIRMING;

    //공연 정보 : ManyToOne
    @ManyToOne    @JoinColumn(name = "shows_id")
    private Shows shows;

    //예매자 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "audience_id")
    private Audience audience;

    //취소 정보
    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL)
    CancelBook cancelBooks;
}
