package pl.pp.project.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pl.pp.project.data.models.Book;
import pl.pp.project.data.models.User;
import pl.pp.project.data.payloads.request.BorrowBookRequest;
import pl.pp.project.data.payloads.response.MessageResponse;
import pl.pp.project.data.repository.BookRepository;
import pl.pp.project.data.repository.BorrowedBookRepository;
import pl.pp.project.data.repository.UserRepository;
import pl.pp.project.exception.BookAlreadyBorrowedException;
import pl.pp.project.exception.ResourceNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BorrowBookServiceImplTest {

    @Mock
    UserRepository userRepository;
    @Mock
    BookRepository bookRepository;
    @Mock
    BorrowedBookRepository borrowedBookRepository;
    @InjectMocks
    BorrowBookServiceImpl borrowBookService;

    private final BorrowBookRequest borrowBookRequest = new BorrowBookRequest();
    private final User existingUser = new User();
    private final Book exisingBook = new Book();

    @Test
    public void borrowBookByNotExistingUserThrowsException() {
        //given
        when(userRepository
                .findById(1))
                .thenReturn(Optional.empty());
        borrowBookRequest.setUserId(1);

        //when
        Throwable thrown = catchThrowable(() -> borrowBookService.borrowBook(borrowBookRequest));

        //then
        assertThat(thrown)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Cannot find User with id: 1");
    }

    @Test
    public void borrowNotExistingBookThrowsException() {
        //given
        when(userRepository
                .findById(1))
                .thenReturn(Optional.of(existingUser));
        when(bookRepository
                .findById(1))
                .thenReturn(Optional.empty());
        borrowBookRequest.setUserId(1);
        borrowBookRequest.setBookId(1);

        //when
        Throwable thrown = catchThrowable(() -> borrowBookService.borrowBook(borrowBookRequest));

        //then
        assertThat(thrown)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Cannot find Book with id: 1");
    }

    @Test
    public void borrowBorrowedBookThrowsException() {
        //given
        when(userRepository
                .findById(1))
                .thenReturn(Optional.of(existingUser));
        when(bookRepository
                .findById(1))
                .thenReturn(Optional.of(exisingBook));
        borrowBookRequest.setUserId(1);
        borrowBookRequest.setBookId(1);
        exisingBook.setBorrowed(true);

        //when
        Throwable thrown = catchThrowable(() -> borrowBookService.borrowBook(borrowBookRequest));

        //then
        assertThat(thrown)
                .isInstanceOf(BookAlreadyBorrowedException.class)
                .hasMessageContaining("Book with id: 1 was already borrowed");
    }

    @Test
    public void borrowBookSuccessfully() {
        //given
        when(userRepository
                .findById(1))
                .thenReturn(Optional.of(existingUser));
        when(bookRepository
                .findById(1))
                .thenReturn(Optional.of(exisingBook));
        borrowBookRequest.setUserId(1);
        borrowBookRequest.setBookId(1);
        exisingBook.setBorrowed(false);

        //when
        MessageResponse messageResponse = borrowBookService.borrowBook(borrowBookRequest);

        //then
        assertThat(exisingBook.isBorrowed()).isTrue();
        assertThat(messageResponse.getMessage()).isEqualTo("Book was borrowed successfully");
    }

}
