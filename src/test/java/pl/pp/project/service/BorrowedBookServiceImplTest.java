package pl.pp.project.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pl.pp.project.data.models.Author;
import pl.pp.project.data.models.Book;
import pl.pp.project.data.models.BorrowedBook;
import pl.pp.project.data.models.User;
import pl.pp.project.data.repository.BookRepository;
import pl.pp.project.data.repository.BorrowedBookRepository;
import pl.pp.project.data.repository.UserRepository;
import pl.pp.project.dto.BookDto;
import pl.pp.project.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BorrowedBookServiceImplTest {
    @Mock
    BorrowedBookRepository borrowedBookRepository;
    @Mock
    BookRepository bookRepository;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    BorrowedBookServiceImpl borrowedBookService;

    @Test
    public void searchForBookNotCurrentlyBorrowedThrowsException() {
        //given
        when(borrowedBookRepository.findBorrowedBookByActiveAndBookId(true, 1))
                .thenReturn(Optional.empty());

        //when
        Throwable thrown = catchThrowable(() -> borrowedBookService.findByBookId(1));

        //then
        assertThat(thrown)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Cannot find BorrowedBook with bookId: 1");
    }

    @Test
    public void searchForBorrowedBookWithWrongUserId() {
        //given
        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setBookId(1);
        borrowedBook.setUserId(1);
        borrowedBook.setActive(true);
        when(borrowedBookRepository.findBorrowedBookByActiveAndBookId(true, 1))
                .thenReturn(Optional.of(borrowedBook));
        when(userRepository.findById(1))
                .thenReturn(Optional.empty());

        //when
        Throwable thrown = catchThrowable(() -> borrowedBookService.findByBookId(1));

        //then
        assertThat(thrown)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Cannot find User with id: 1");
    }

    @Test
    public void userIsFoundWhenBorrowedBookExist() {
        //given
        BorrowedBook borrowedBook = new BorrowedBook();
        User userWithBorrowedBook = new User();
        borrowedBook.setBookId(1);
        borrowedBook.setUserId(1);
        borrowedBook.setActive(true);
        userWithBorrowedBook.setId(1);
        when(borrowedBookRepository.findBorrowedBookByActiveAndBookId(true, 1))
                .thenReturn(Optional.of(borrowedBook));
        when(userRepository.findById(1))
                .thenReturn(Optional.of(userWithBorrowedBook));

        //when
        User user = borrowedBookService.findByBookId(1);

        //then
        assertThat(user.getId())
                .isEqualTo(1);
    }

    @Test
    public void userHasNotBorrowedBooks() {
        //given
        when(bookRepository.findBooksByUserIdAndIsBorrowed(1, true))
                .thenReturn(new ArrayList<>());

        //when
        List<BookDto> borrowedBooks = borrowedBookService.findBooksByUserId(1);

        //then
        assertThat(borrowedBooks.size())
                .isEqualTo(0);
    }

    @Test
    public void userHasBorrowedBooks() {
        //given
        List<Book> booksOfUser = new ArrayList<>();
        Book borrowedBook = new Book();
        borrowedBook.setAuthor(new Author());
        booksOfUser.add(borrowedBook);
        when(bookRepository.findBooksByUserIdAndIsBorrowed(1, true))
                .thenReturn(booksOfUser);

        //when
        List<BookDto> borrowedBooks = borrowedBookService.findBooksByUserId(1);

        //then
        assertThat(borrowedBooks.size())
                .isEqualTo(1);
    }
}
