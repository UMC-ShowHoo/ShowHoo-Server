package umc.ShowHoo.web.book.service;

import umc.ShowHoo.web.book.dto.BookRequestDTO;
import umc.ShowHoo.web.book.entity.Book;
import umc.ShowHoo.web.cancelBook.entity.CancelBook;

public interface BookCommandService {

    Book postBook(BookRequestDTO.postDTO request);

    CancelBook requestCancel(Long bookId, BookRequestDTO.deleteBookDTO request);
}
