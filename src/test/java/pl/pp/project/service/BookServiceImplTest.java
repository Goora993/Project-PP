package pl.pp.project.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pl.pp.project.data.models.Author;
import pl.pp.project.data.models.Book;
import pl.pp.project.data.payloads.request.CreateBookRequest;
import pl.pp.project.data.payloads.response.MessageResponse;
import pl.pp.project.data.repository.AuthorRepository;
import pl.pp.project.data.repository.BookRepository;
import pl.pp.project.exception.ResourceNotFoundException;

import java.sql.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private final Author existingAuthor = new Author();
    private final Book existingBook = new Book();
    private final CreateBookRequest createBookRequest = new CreateBookRequest();

    @Test
    public void bookIsCreatedSuccessfully() {
        //given
        when(authorRepository
                .findById(any(Integer.class)))
                .thenReturn(Optional.of(existingAuthor));

        createBookRequest.setName("Książka");
        createBookRequest.setIsbn("123123");
        createBookRequest.setPublicationYear(Date.valueOf("1999-11-11"));
        createBookRequest.setAuthorId(1);

        //when
        MessageResponse actualMessageResponse = bookService.createBook(createBookRequest);

        //then
        assertThat(actualMessageResponse)
                .isEqualTo(new MessageResponse("New book was added successfully"));
    }

    @Test
    public void bookCreationWithNotExistingAuthorThrowsException() {
        //given
        when(authorRepository
                .findById(any(Integer.class)))
                .thenReturn(Optional.empty());

        createBookRequest.setName("Książka");
        createBookRequest.setIsbn("123123");
        createBookRequest.setPublicationYear(Date.valueOf("1999-11-11"));
        createBookRequest.setAuthorId(1);

        //when
        Throwable thrown = catchThrowable(() -> bookService.createBook(createBookRequest));

        //then
        assertThat(thrown)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Cannot find Author with id: 1");
    }

    @Test
    public void updateNotExistingBookThrowsException() {
        //given
        when(bookRepository
                .findById(any(Integer.class)))
                .thenReturn(Optional.empty());

        //when
        Throwable thrown = catchThrowable(() -> bookService.updateBook(1, createBookRequest));

        //then
        assertThat(thrown)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Cannot find Book with id: 1");
    }

    @Test
    public void updateBookWithNotExistingAuthorThrowsException() {
        //given
        when(bookRepository
                .findById(1))
                .thenReturn(Optional.of(existingBook));
        when(authorRepository
                .findById(1))
                .thenReturn(Optional.empty());
        createBookRequest.setAuthorId(1);

        //when
        Throwable thrown = catchThrowable(() -> bookService.updateBook(1, createBookRequest));

        //then
        assertThat(thrown)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Cannot find Author with id: 1");
    }

    @Test
    public void updateBookSuccessfully() {
        when(authorRepository
                .findById(1))
                .thenReturn(Optional.of(existingAuthor));
        when(bookRepository
                .findById(1))
                .thenReturn(Optional.of(existingBook));
        createBookRequest.setAuthorId(1);

        MessageResponse messageResponse = bookService.updateBook(1, createBookRequest);

        assertThat(messageResponse.getMessage()).isEqualTo("Book updated successfully");
    }
}
