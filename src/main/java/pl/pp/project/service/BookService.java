package pl.pp.project.service;

import org.springframework.stereotype.Component;
import pl.pp.project.data.models.Author;
import pl.pp.project.data.models.Book;
import pl.pp.project.data.payloads.request.CreateBookRequest;
import pl.pp.project.data.payloads.response.MessageResponse;
import pl.pp.project.dto.BookDto;

import java.util.List;
import java.util.Set;

@Component
public interface BookService {
    MessageResponse createBook(CreateBookRequest createBookRequest);
    MessageResponse updateBook(Integer bookId, CreateBookRequest createBookRequest);
    void deleteBook(Integer bookId);
    Book getASingleBook(Integer bookId);
    List<BookDto> getAllBooks();
    Set<Book> getBooksOfAuthor(Author author);
}
