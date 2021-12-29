package pl.pp.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pp.project.data.models.Book;
import pl.pp.project.data.models.BorrowedBook;
import pl.pp.project.data.models.User;
import pl.pp.project.data.payloads.request.BorrowBookRequest;
import pl.pp.project.data.payloads.request.ReturnBookRequest;
import pl.pp.project.data.payloads.response.MessageResponse;
import pl.pp.project.data.repository.BookRepository;
import pl.pp.project.data.repository.BorrowedBookRepository;
import pl.pp.project.data.repository.UserRepository;
import pl.pp.project.exception.BookAlreadyBorrowedException;
import pl.pp.project.exception.ResourceNotFoundException;

import java.util.Optional;

@Service
public class BorrowBookServiceImpl implements BorrowBookService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BorrowedBookRepository borrowedBookRepository;

    @Override
    public MessageResponse borrowBook(BorrowBookRequest borrowBookRequest) {
        Integer userId = borrowBookRequest.getUserId();
        Integer bookId = borrowBookRequest.getBookId();
        Optional<User> user = userRepository.findById(userId);
        Optional<Book> book = bookRepository.findById(bookId);
        BorrowedBook borrowedBook = new BorrowedBook();

        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User", "id", userId);
        } else if (book.isEmpty()) {
            throw new ResourceNotFoundException("Book", "id", bookId);
        } else {
            if (book.get().isBorrowed()) {
                throw new BookAlreadyBorrowedException(bookId);
            } else {
                book.get().setBorrowed(true);
                borrowedBook.setUserId(userId);
                borrowedBook.setBookId(bookId);
                bookRepository.save(book.get());
                borrowedBookRepository.save(borrowedBook);
                return new MessageResponse("Book was borrowed successfully");
            }
        }
    }

}
