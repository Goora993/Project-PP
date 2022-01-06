package pl.pp.project.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ReturnBookServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    BookRepository bookRepository;
    @Mock
    BorrowedBookRepository borrowedBookRepository;
    @InjectMocks
    ReturnBookServiceImpl returnBookService;

    private final ReturnBookRequest returnBookRequest = new ReturnBookRequest();

    @Test
    public void returningBookForNotExistingUserThrowsException(){
        //given
        when(userRepository.findById(1))
                .thenReturn(Optional.empty());
        returnBookRequest.setUserId(1);

        //when
        Throwable thrown = catchThrowable(() -> returnBookService.returnBook(returnBookRequest));

        //then
        assertThat(thrown)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Cannot find User with id: 1");
    }

    @Test
    public void returningBookForNotExistingBookThrowsException(){
        //given
        when(userRepository.findById(1))
                .thenReturn(Optional.of(new User()));
        when(bookRepository.findById(1))
                .thenReturn(Optional.empty());
        returnBookRequest.setUserId(1);
        returnBookRequest.setBookId(1);

        //when
        Throwable thrown = catchThrowable(() -> returnBookService.returnBook(returnBookRequest));

        //then
        assertThat(thrown)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Cannot find Book with id: 1");
    }

    @Test
    public void returningNotBorrowedBookThrowsException(){
        //given
        when(userRepository.findById(1))
                .thenReturn(Optional.of(new User()));
        when(bookRepository.findById(1))
                .thenReturn(Optional.of(new Book()));
        when(borrowedBookRepository.findById(1))
                .thenReturn(Optional.empty());
        returnBookRequest.setUserId(1);
        returnBookRequest.setBookId(1);

        //when
        Throwable thrown = catchThrowable(() -> returnBookService.returnBook(returnBookRequest));

        //then
        assertThat(thrown)
                .isInstanceOf(BorrowedBookNotFoundException.class)
                .hasMessageContaining("Cannot find book with id: 1 on borrowed books list");
    }

    @Test
    public void returningBookForWrongUserIdThrowsException(){
        //given
        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setUserId(2);
        when(userRepository.findById(1))
                .thenReturn(Optional.of(new User()));
        when(bookRepository.findById(1))
                .thenReturn(Optional.of(new Book()));
        when(borrowedBookRepository.findById(1))
                .thenReturn(Optional.of(borrowedBook));
        returnBookRequest.setUserId(1);
        returnBookRequest.setBookId(1);

        //when
        Throwable thrown = catchThrowable(() -> returnBookService.returnBook(returnBookRequest));

        //then
        assertThat(thrown)
                .isInstanceOf(BorrowedBookWrongUserId.class)
                .hasMessageContaining("User with id: 1 doesn't have book with id: 1 borrowed");
    }

    @Test
    public void returningAlreadyReturnedBookThrowsException(){
        //given
        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setUserId(1);
        borrowedBook.setActive(false);
        when(userRepository.findById(1))
                .thenReturn(Optional.of(new User()));
        when(bookRepository.findById(1))
                .thenReturn(Optional.of(new Book()));
        when(borrowedBookRepository.findById(1))
                .thenReturn(Optional.of(borrowedBook));
        returnBookRequest.setUserId(1);
        returnBookRequest.setBookId(1);

        //when
        Throwable thrown = catchThrowable(() -> returnBookService.returnBook(returnBookRequest));

        //then
        assertThat(thrown)
                .isInstanceOf(BookAlreadyReturnedException.class)
                .hasMessageContaining("Book with id: 1 was already returned");
    }

    @Test
    public void bookReturnedSuccessfully(){
        //given
        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setUserId(1);
        borrowedBook.setActive(true);
        when(userRepository.findById(1))
                .thenReturn(Optional.of(new User()));
        when(bookRepository.findById(1))
                .thenReturn(Optional.of(new Book()));
        when(borrowedBookRepository.findById(1))
                .thenReturn(Optional.of(borrowedBook));
        returnBookRequest.setUserId(1);
        returnBookRequest.setBookId(1);

        //when
        MessageResponse messageResponse = returnBookService.returnBook(returnBookRequest);

        //then
        assertThat(messageResponse.getMessage())
                .isEqualTo("Book was returned successfully");
    }


}
