package umc.ShowHoo.web.cancelBook.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.ShowHoo.web.book.entity.Book;
import umc.ShowHoo.web.common.BaseEntity;
import umc.ShowHoo.web.performer.entity.Performer;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CancelBook extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String bankName;

    private String account;

    private String reason;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "performer_id")
    private Performer performer;
}
