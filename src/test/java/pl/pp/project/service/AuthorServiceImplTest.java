package pl.pp.project.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pl.pp.project.data.models.Author;
import pl.pp.project.data.payloads.request.CreateAuthorRequest;
import pl.pp.project.data.repository.AuthorRepository;
import pl.pp.project.dto.impl.AuthorToImportDto;
import pl.pp.project.exception.AuthorAlreadyExistsException;
import pl.pp.project.exception.ResourceNotFoundException;

import java.sql.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
public class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private final Author existingAuthor = new Author();
    private final CreateAuthorRequest createAuthorRequest = new CreateAuthorRequest();


    @Test
    public void authorAlreadyExistExceptionIsThrown() {
        //given
        when(authorRepository
                .findByFirstNameAndLastNameAndDateOfBirth("New", "Author", Date.valueOf("1999-11-11")))
                .thenReturn(Optional.of(existingAuthor));
        createAuthorRequest.setFirstName("New");
        createAuthorRequest.setLastName("Author");
        createAuthorRequest.setDateOfBirth(Date.valueOf("1999-11-11"));

        //when
        Throwable thrown = catchThrowable(() -> authorService.createAuthor(createAuthorRequest));

        //then
        assertThat(thrown)
                .isInstanceOf(AuthorAlreadyExistsException.class)
                .hasMessageContaining("Author New Author born in 1999-11-11 already exist");
    }

    @Test
    public void authorIsCreatedSuccessfully() {
        //given
        when(authorRepository
                .findByFirstNameAndLastNameAndDateOfBirth("New", "Author", Date.valueOf("1999-11-11")))
                .thenReturn(Optional.empty());
        createAuthorRequest.setFirstName("New");
        createAuthorRequest.setLastName("Author");
        createAuthorRequest.setDateOfBirth(Date.valueOf("1999-11-11"));

        //when
        Author author = authorService.createAuthor(createAuthorRequest);

        //then
        assertThat(author.getFirstName()).isEqualTo(createAuthorRequest.getFirstName());
        assertThat(author.getLastName()).isEqualTo(createAuthorRequest.getLastName());
        assertThat(author.getDateOfBirth()).isEqualTo(createAuthorRequest.getDateOfBirth());
    }

    @Test
    public void authorToImportIsCreatedSuccessfully() {
        //given
        AuthorToImportDto authorToImportDto = new AuthorToImportDto("Jan", "Kowalski", Date.valueOf("1999-11-11"));

        //when
        Author newAuthor = authorService.createAuthor(authorToImportDto);

        //then
        assertThat(newAuthor)
                .isEqualTo(new Author("Jan", "Kowalski", Date.valueOf("1999-11-11")));
    }

    @Test
    public void updateNotExistingAuthorThrowsException() {
        //given
        when(authorRepository
                .findById(any(Integer.class)))
                .thenReturn(Optional.empty());

        //when
        Throwable thrown = catchThrowable(() -> authorService.updateAuthor(1, createAuthorRequest));

        //then
        assertThat(thrown)
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Cannot find Author with id: 1");
    }

    @Test
    public void updateExistingAuthorIsSuccessful() {
        //given
        when(authorRepository
                .findById(any(Integer.class)))
                .thenReturn(Optional.of(existingAuthor));
        createAuthorRequest.setFirstName("New");
        createAuthorRequest.setLastName("Author");
        createAuthorRequest.setDateOfBirth(Date.valueOf("1999-11-11"));

        //when
        Author updatedAuthor = authorService.updateAuthor(1, createAuthorRequest);

        //then
        assertThat(updatedAuthor.getFirstName()).isEqualTo(createAuthorRequest.getFirstName());
        assertThat(updatedAuthor.getLastName()).isEqualTo(createAuthorRequest.getLastName());
        assertThat(updatedAuthor.getDateOfBirth()).isEqualTo(createAuthorRequest.getDateOfBirth());

    }


}




