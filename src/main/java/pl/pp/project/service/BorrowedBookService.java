package pl.pp.project.service;

import org.springframework.stereotype.Component;
import pl.pp.project.data.models.User;
import pl.pp.project.dto.BookDto;

import java.util.List;

@Component
public interface BorrowedBookService {
    User findByBookId(int bookId);
    List<BookDto> findBooksByUserId(int userId);
}
