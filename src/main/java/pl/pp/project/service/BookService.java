package pl.pp.project.service;

import org.springframework.stereotype.Component;
import pl.pp.project.data.models.Book;
import pl.pp.project.data.payloads.request.AddBookRequest;
import pl.pp.project.data.payloads.response.MessageResponse;

import java.util.List;

@Component
public interface BookService {
    MessageResponse addBook(AddBookRequest addBookRequest);
    MessageResponse updateBook(Integer bookId, AddBookRequest addBookRequest);
    void deleteBook(Integer bookId);
    Book getASingleBook(Integer bookId);
    List<Book> getAllBooks();
}
