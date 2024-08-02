package umc.ShowHoo.web.audience.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.ShowHoo.web.book.entity.Book;
import umc.ShowHoo.web.member.entity.Member;
import umc.ShowHoo.web.showsPrefer.entity.ShowsPrefer;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Audience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "audience", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Book> bookList = new ArrayList<>();

    @OneToMany(mappedBy = "audience", cascade = CascadeType.ALL)
    private List<ShowsPrefer> preferList;
}
