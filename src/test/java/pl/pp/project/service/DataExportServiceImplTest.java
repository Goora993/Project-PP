package pl.pp.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import pl.pp.project.data.models.Author;
import pl.pp.project.data.models.Book;
import pl.pp.project.data.models.User;
import pl.pp.project.data.payloads.request.ExportAllBooksRequest;
import pl.pp.project.data.payloads.request.ExportUserWithBorrowedBooksRequest;
import pl.pp.project.data.payloads.response.MessageResponse;
import pl.pp.project.data.repository.BookRepository;
import pl.pp.project.data.repository.UserRepository;
import pl.pp.project.exception.ResourceNotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DataExportServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    BookRepository bookRepository;
    @Spy
    ObjectMapper mapper;
    @InjectMocks
    DataExportServiceImpl dataExportService;

    private final ExportUserWithBorrowedBooksRequest exportUserWithBorrowedBooksRequest = new ExportUserWithBorrowedBooksRequest();
    private final ExportAllBooksRequest exportAllBooksRequest = new ExportAllBooksRequest();

    @Test
    public void exportingNotExistingUserThrowsException() {
        //given
        when(userRepository.findById(1))
                .thenReturn(Optional.empty());
        exportUserWithBorrowedBooksRequest.setUserId(1);

        //when
        Throwable thrown = catchThrowable(() -> dataExportService.exportUserWithBorrowedBooks(exportUserWithBorrowedBooksRequest));

        //then
        assertThat(thrown)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Cannot find User with id: 1");
    }

    @Test
    public void exportUserSuccessfully() {
        //given
        User user = new User();
        user.setBorrowedBooks(new ArrayList<>());
        exportUserWithBorrowedBooksRequest.setUserId(1);
        exportUserWithBorrowedBooksRequest.setPathToExport("src/test/resources");
        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        //when
        MessageResponse messageResponse = dataExportService.exportUserWithBorrowedBooks(exportUserWithBorrowedBooksRequest);

        //then
        assertThat(messageResponse.getMessage())
                .isEqualTo("User with borrowed books data exported successfully");
    }

    @Test
    public void exportingUserToWrongPathThrowsException() {
        //given
        User user = new User();
        user.setBorrowedBooks(new ArrayList<>());
        exportUserWithBorrowedBooksRequest.setUserId(1);
        exportUserWithBorrowedBooksRequest.setPathToExport("XXXwrongPathXXX");
        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        //when
        Throwable thrown = catchThrowable(() -> dataExportService.exportUserWithBorrowedBooks(exportUserWithBorrowedBooksRequest));

        //then
        assertThat(thrown)
                .isInstanceOf(IOException.class);
    }

    @Test
    public void exportAllBorrowedBooksSuccessfully() {
        //given
        Book book = new Book();
        book.setAuthor(new Author());
        List<Book> books = new ArrayList<>();
        books.add(book);
        exportAllBooksRequest.setIsBorrowed(true);
        exportAllBooksRequest.setPathToExport("src/test/resources");
        when(bookRepository.findBooksByIsBorrowed(true))
                .thenReturn(books);

        //when
        MessageResponse messageResponse = dataExportService.exportAllBooks(exportAllBooksRequest);

        //then
        assertThat(messageResponse.getMessage())
                .isEqualTo("Borrowed books list exported successfully");
    }

    @Test
    public void exportAllNotBorrowedBooksSuccessfully() {
        //given
        Book book = new Book();
        book.setAuthor(new Author());
        List<Book> books = new ArrayList<>();
        books.add(book);
        exportAllBooksRequest.setIsBorrowed(false);
        exportAllBooksRequest.setPathToExport("src/test/resources");
        when(bookRepository.findBooksByIsBorrowed(false))
                .thenReturn(books);

        //when
        MessageResponse messageResponse = dataExportService.exportAllBooks(exportAllBooksRequest);

        //then
        assertThat(messageResponse.getMessage())
                .isEqualTo("Not borrowed books list exported successfully");
    }

    @Test
    public void exportAllBooksSuccessfully() {
        //given
        Book book = new Book();
        book.setAuthor(new Author());
        List<Book> books = new ArrayList<>();
        books.add(book);
        exportAllBooksRequest.setIsBorrowed(null);
        exportAllBooksRequest.setPathToExport("src/test/resources");
        when(bookRepository.findBooksByIsBorrowed(null))
                .thenReturn(books);

        //when
        MessageResponse messageResponse = dataExportService.exportAllBooks(exportAllBooksRequest);

        //then
        assertThat(messageResponse.getMessage())
                .isEqualTo("All books list exported successfully");
    }

    @Test
    public void exportingBooksToWrongPathThrowsException() {
        //given
        Book book = new Book();
        book.setAuthor(new Author());
        List<Book> books = new ArrayList<>();
        books.add(book);
        exportAllBooksRequest.setIsBorrowed(null);
        exportAllBooksRequest.setPathToExport("AAAwrongPathAAA");
        when(bookRepository.findBooksByIsBorrowed(null))
                .thenReturn(books);

        //when
        Throwable thrown = catchThrowable(() -> dataExportService.exportAllBooks(exportAllBooksRequest));

        //then
        assertThat(thrown)
                .isInstanceOf(IOException.class);
    }
}
