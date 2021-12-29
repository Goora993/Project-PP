package pl.pp.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pp.project.data.models.Book;
import pl.pp.project.data.payloads.request.AddBookRequest;
import pl.pp.project.data.payloads.response.MessageResponse;
import pl.pp.project.data.repository.BookRepository;
import pl.pp.project.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookRepository bookRepository;

    @Override
    public MessageResponse addBook(AddBookRequest addBookRequest) {
        Book newBook = new Book();
        newBook.setName(addBookRequest.getName());
        newBook.setAuthorId(addBookRequest.getAuthorId());
        newBook.setIsbn(addBookRequest.getIsbn());
        newBook.setPublicationYear(addBookRequest.getPublicationYear());
        bookRepository.save(newBook);
        return new MessageResponse("New book added successfully");
    }

    @Override
    public MessageResponse updateBook(Integer bookId, AddBookRequest addBookRequest) throws ResourceNotFoundException {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty()) {
            throw new ResourceNotFoundException("Book", "id", bookId);
        } else{
            book.get().setName(addBookRequest.getName());
            book.get().setAuthorId(addBookRequest.getAuthorId());
            book.get().setIsbn(addBookRequest.getIsbn());
            book.get().setPublicationYear(addBookRequest.getPublicationYear());
            bookRepository.save(book.get());
            return new MessageResponse("Book updated successfully");
        }
    }

    @Override
    public void deleteBook(Integer bookId) {
        if (bookRepository.getById(bookId).getId() == bookId) {
            bookRepository.deleteById(bookId);
        } else throw new ResourceNotFoundException("Book", "id", bookId);
    }

    @Override
    public Book getASingleBook(Integer bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Employee", "id", bookId));
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}