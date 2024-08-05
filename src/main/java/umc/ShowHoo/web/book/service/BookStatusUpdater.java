package umc.ShowHoo.web.book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import umc.ShowHoo.web.shows.entity.Shows;
import umc.ShowHoo.web.shows.repository.ShowsRepository;
import umc.ShowHoo.web.book.entity.Book;
import umc.ShowHoo.web.book.entity.BookDetail;
import umc.ShowHoo.web.book.entity.BookStatus;
import umc.ShowHoo.web.book.repository.BookRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
//shows에도 status를 추가해야 쿼리가 줄 듯
public class BookStatusUpdater {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ShowsRepository showsRepository;

    @Scheduled(fixedRate = 60000) //60초마다 실행
    @Transactional
    public void updateBookStatusWatched(){
        List<Shows> showsList = showsRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        for(Shows shows : showsList){
            String dateTimeString = shows.getDate() + " " + shows.getTime();
            LocalDateTime showTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            if(showTime.isBefore(now)){
                for(Book book : shows.getBookList()){
                    if(book.getDetail() == BookDetail.CONFIRMED){
                        book.setStatus(BookStatus.WATCHED);
                        book.setDetail(BookDetail.WATCHED);
                        bookRepository.save(book);
                    }
                }
            }
        }
    }
}
