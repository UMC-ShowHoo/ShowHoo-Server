package umc.ShowHoo.web.book.service;

import umc.ShowHoo.web.book.dto.BookRequestDTO;
import umc.ShowHoo.web.book.entity.Book;

public interface BookCommandService {

    Book postBook(BookRequestDTO.postDTO request);
}
