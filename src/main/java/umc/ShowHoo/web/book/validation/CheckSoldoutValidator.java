package umc.ShowHoo.web.book.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.ShowHoo.apiPayload.code.status.ErrorStatus;
import umc.ShowHoo.web.book.dto.BookRequestDTO;
import umc.ShowHoo.web.book.entity.Book;
import umc.ShowHoo.web.book.entity.BookStatus;
import umc.ShowHoo.web.book.repository.BookRepository;
import umc.ShowHoo.web.shows.repository.ShowsRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CheckSoldoutValidator implements ConstraintValidator<CheckSoldout, BookRequestDTO.postDTO> {

    private final BookRepository bookRepository;

    private final ShowsRepository showsRepository;

    @Override
    public void initialize(CheckSoldout constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(BookRequestDTO.postDTO request, ConstraintValidatorContext context) {
        boolean isExist = showsRepository.existsById(request.getShowsId());
        boolean isValid = true;

        if(isExist){
            umc.ShowHoo.web.shows.entity.Shows shows = showsRepository.findById(request.getShowsId())
                    .orElseThrow(()->new umc.ShowHoo.web.shows.handler.ShowsHandler(ErrorStatus.SHOW_NOT_FOUND));

            List<Book> bookList = bookRepository.findAllByShows(shows);

            //(기존 예매 내역의 ticketNum의 합산 + request의 ticketNum) > Shows_ticketNum일 경우, "공연 매진" 메세지 전달
            if (!bookList.isEmpty()) {
                Integer num = bookList.stream()
                        .filter(book -> book.getStatus() == BookStatus.BOOK)
                        .mapToInt(Book::getTicketNum)
                        .sum();

                if( num + request.getTicketNum() > shows.getTicketNum()){
                    isValid = false;
                }
            }

            if(!isValid){
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(ErrorStatus.SHOW_ALREADY_SOLD_OUT.toString()).addConstraintViolation();
            }


        return isValid;
        }
        if(!isExist){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.SHOW_NOT_FOUND.toString()).addConstraintViolation();
        }

        return isExist;
    }
}
