package umc.ShowHoo.web.audience.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.ShowHoo.web.book.entity.Book;
import umc.ShowHoo.web.member.entity.Member;

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
    Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    Member member;

    @OneToMany(mappedBy = "audience", cascade = CascadeType.ALL)
    @Builder.Default
    List<Book> bookList = new ArrayList<>();
}
