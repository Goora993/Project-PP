package pl.pp.project.service;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import pl.pp.project.data.models.Book;
import pl.pp.project.data.payloads.request.CreateBookRequest;
import pl.pp.project.data.payloads.response.MessageResponse;

import java.util.List;

@Component
public interface BookService {
    MessageResponse createBook(CreateBookRequest createBookRequest);
    MessageResponse updateBook(Integer bookId, CreateBookRequest createBookRequest);
    void deleteBook(Integer bookId);
    Book getASingleBook(Integer bookId);
    List<Book> getAllBooks();
}
