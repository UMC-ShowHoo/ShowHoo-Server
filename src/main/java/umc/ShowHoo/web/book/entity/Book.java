package umc.ShowHoo.web.book.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.ShowHoo.web.audience.entity.Audience;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(15) DEFAULT 'CONFIRMING'")
    BookStatus status;

    //공연 정보 : ManyToOne

    //예매자 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "audience_id")
    Audience audience;
}
