package pl.pp.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import pl.pp.project.data.models.Author;
import pl.pp.project.data.payloads.request.ImportBooksRequest;
import pl.pp.project.data.payloads.response.MessageResponse;
import pl.pp.project.data.repository.AuthorRepository;
import pl.pp.project.data.repository.BookRepository;

import java.io.IOException;
import java.sql.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DataImportServiceImplTest {
    @Mock
    AuthorRepository authorRepository;
    @Mock
    BookRepository bookRepository;
    @Mock
    AuthorService authorService;
    @Mock
    BookService bookService;
    @Spy
    ObjectMapper mapper;
    @InjectMocks
    DataImportServiceImpl dataImportService;

    private final ImportBooksRequest importBooksRequest = new ImportBooksRequest();

    @Test
    public void importBooksFromNotExistingFile() {
        //given
        importBooksRequest.setPathToImport("XXXwrongPathXXX");

        //when
        Throwable thrown = catchThrowable(() -> dataImportService.importBooks(importBooksRequest));

        //then
        assertThat(thrown)
                .isInstanceOf(IOException.class);
    }

    @Test
    public void importBooksForNotExistingAuthors() {
        //given
        importBooksRequest.setPathToImport("src/test/resources/booksImport.json");
        when(authorRepository.findByFirstNameAndLastNameAndDateOfBirth("Arnold", "Boczek", Date.valueOf("1970-10-10")))
                .thenReturn(Optional.empty());

        //when
        MessageResponse messageResponse = dataImportService.importBooks(importBooksRequest);

        //then
        assertThat(messageResponse.getMessage())
                .isEqualTo("Books were imported successfully");
    }

    @Test
    public void importBooksForExistingAuthors() {
        //given
        Author author = new Author(1, "Arnold", "Boczek", Date.valueOf("1970-10-10"));
        importBooksRequest.setPathToImport("src/test/resources/booksImport.json");
        when(authorRepository.findByFirstNameAndLastNameAndDateOfBirth("Arnold", "Boczek", Date.valueOf("1970-10-10")))
                .thenReturn(Optional.of(author));

        //when
        MessageResponse messageResponse = dataImportService.importBooks(importBooksRequest);

        //then
        assertThat(messageResponse.getMessage())
                .isEqualTo("Books were imported successfully");
    }



}
