package pl.pp.project.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pp.project.data.models.Author;
import pl.pp.project.data.payloads.request.ImportBooksRequest;
import pl.pp.project.data.payloads.response.MessageResponse;
import pl.pp.project.data.repository.AuthorRepository;
import pl.pp.project.data.repository.BookRepository;
import pl.pp.project.dto.impl.AuthorToImportDto;
import pl.pp.project.dto.impl.BookToImportDto;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class DataImportServiceImpl implements DataImportService {
    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorService authorService;

    @Autowired
    BookService bookService;

    @Autowired
    ObjectMapper mapper;


    @SneakyThrows
    @Override
    public MessageResponse importBooks(ImportBooksRequest importBooksRequest) {
        InputStream inputStream = new FileInputStream(new File(importBooksRequest.getPathToImport()));
        TypeReference<List<BookToImportDto>> typeReference = new TypeReference<List<BookToImportDto>>() {
        };
        List<BookToImportDto> booksToImport = mapper.readValue(inputStream, typeReference);
        Optional<Author> existingAuthor;
        AuthorToImportDto authorToImportDto;
        for (BookToImportDto bookToImportDto : booksToImport) {
            authorToImportDto = bookToImportDto.getAuthor();
            existingAuthor = authorRepository.findByFirstNameAndLastNameAndDateOfBirth(
                    authorToImportDto.getFirstName(), authorToImportDto.getLastName(), authorToImportDto.getDateOfBirth());
            if (existingAuthor.isPresent()) {
                createBookWithExistingAuthor(existingAuthor.get(), bookToImportDto);
            } else {
                createBookWithNewAuthor(bookToImportDto);
            }
        }
        return new MessageResponse("Books were imported successfully");
    }

    private void createBookWithExistingAuthor(Author existingAuthor, BookToImportDto bookToImportDto){
        bookService.createBook(bookToImportDto, existingAuthor);
    }

    private void createBookWithNewAuthor(BookToImportDto bookToImportDto){
        Author newAuthor = authorService.createAuthor(bookToImportDto.getAuthor());
        bookService.createBook(bookToImportDto, newAuthor);
    }
}
