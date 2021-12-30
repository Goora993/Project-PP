package pl.pp.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pp.project.data.models.Book;
import pl.pp.project.data.models.BorrowedBook;
import pl.pp.project.data.models.User;
import pl.pp.project.data.payloads.request.ReturnBookRequest;
import pl.pp.project.data.payloads.response.MessageResponse;
import pl.pp.project.data.repository.BookRepository;
import pl.pp.project.data.repository.BorrowedBookRepository;
import pl.pp.project.data.repository.UserRepository;
import pl.pp.project.exception.BookAlreadyReturnedException;
import pl.pp.project.exception.BorrowedBookNotFoundException;
import pl.pp.project.exception.BorrowedBookWrongUserId;
import pl.pp.project.exception.ResourceNotFoundException;

import java.util.Optional;

@Service
public class ReturnBookServiceImpl implements ReturnBookService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BorrowedBookRepository borrowedBookRepository;

    @Override
    public MessageResponse returnBook(ReturnBookRequest returnBookRequest) {
        Integer userIdRelatedToBorrowedBook;
        Integer userId = returnBookRequest.getUserId();
        Integer bookId = returnBookRequest.getBookId();
        Optional<User> user = userRepository.findById(userId);
        Optional<Book> book = bookRepository.findById(bookId);
        Optional<BorrowedBook> borrowedBook = borrowedBookRepository.findById(bookId);


        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User", "id", userId);
        } else if (book.isEmpty()) {
            throw new ResourceNotFoundException("Book", "id", bookId);
        } else if (borrowedBook.isEmpty()) {
            throw new BorrowedBookNotFoundException(bookId);
        } else {
            userIdRelatedToBorrowedBook = borrowedBook.get().getUserId();
            if (userIdRelatedToBorrowedBook.equals(userId)) {
                if(borrowedBook.get().isActive()){
                    book.get().setBorrowed(false);
                    book.get().setUser(null);
                    borrowedBook.get().setActive(false);
                    bookRepository.save(book.get());
                    borrowedBookRepository.save(borrowedBook.get());
                    return new MessageResponse("Book was returned successfully");
                } else {
                    throw new BookAlreadyReturnedException(bookId);
                }
            } else {
                throw new BorrowedBookWrongUserId(userId, bookId);
            }
        }
    }
}
