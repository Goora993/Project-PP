package pl.pp.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pp.project.data.models.Author;
import pl.pp.project.data.models.Book;
import pl.pp.project.data.payloads.request.CreateBookRequest;
import pl.pp.project.data.payloads.response.MessageResponse;
import pl.pp.project.data.repository.AuthorRepository;
import pl.pp.project.data.repository.BookRepository;
import pl.pp.project.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public MessageResponse createBook(CreateBookRequest createBookRequest) {
        Book newBook = new Book();
        Optional<Author> authorById = authorRepository.findById(createBookRequest.getAuthorId());
        if(authorById.isPresent()){
            newBook.setIsbn(createBookRequest.getIsbn());
            newBook.setPublicationYear(createBookRequest.getPublicationYear());
            newBook.setName(createBookRequest.getName());
            newBook.setAuthorId(createBookRequest.getAuthorId());
            bookRepository.save(newBook);
            return new MessageResponse("New book was added successfully");
        } else {
            throw new ResourceNotFoundException("Author", "id", createBookRequest.getAuthorId());
        }
    }

    @Override
    public MessageResponse updateBook(Integer bookId, CreateBookRequest createBookRequest) throws ResourceNotFoundException {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty()) {
            throw new ResourceNotFoundException("Book", "id", bookId);
        } else {
            book.get().setName(createBookRequest.getName());
            book.get().setAuthorId(createBookRequest.getAuthorId());
            book.get().setIsbn(createBookRequest.getIsbn());
            book.get().setPublicationYear(createBookRequest.getPublicationYear());
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
        return bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
