package pl.pp.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pp.project.data.models.Book;
import pl.pp.project.data.models.BorrowedBook;
import pl.pp.project.data.models.User;
import pl.pp.project.data.repository.BookRepository;
import pl.pp.project.data.repository.BorrowedBookRepository;
import pl.pp.project.data.repository.UserRepository;
import pl.pp.project.dto.BookDto;
import pl.pp.project.dto.mappers.BookMapper;
import pl.pp.project.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class BorrowedBookServiceImpl implements BorrowedBookService{

    @Autowired
    BorrowedBookRepository borrowedBookRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public User findByBookId(int bookId) {
        Optional<BorrowedBook> borrowedBook = borrowedBookRepository.findBorrowedBookByActiveAndBookId(true, bookId);
        int userId;
        if(borrowedBook.isPresent())
            userId = borrowedBook.get().getUserId();
        else
            throw new ResourceNotFoundException("BorrowedBook", "bookId", bookId);

        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent())
           return user.get();
        else
            throw new ResourceNotFoundException("User", "id", userId);
    }

    @Override
    public List<BookDto> findBooksByUserId(int userId) {
        List<Book> books = bookRepository.findBooksByUserIdAndIsBorrowed(userId, true);
        return BookMapper.bookListToBookWithAuthorDtoList(books);
    }
}
