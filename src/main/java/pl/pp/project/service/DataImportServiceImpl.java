package pl.pp.project.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class DataImportServiceImpl implements DataImportService{
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


    @Override
    public MessageResponse importBooks(ImportBooksRequest importBooksRequest) {
        try {
            InputStream inputStream = new FileInputStream(new File(importBooksRequest.getPathToImport()));
            TypeReference<List<BookToImportDto>> typeReference = new TypeReference<List<BookToImportDto>>() {};
            List<BookToImportDto> booksToImport = mapper.readValue(inputStream, typeReference);
            Optional<Author> existingAuthor;
            Optional<Author> newAuthor;
            BookToImportDto bookToImportDto;
            AuthorToImportDto authorToImportDto;
            for (int i = 0; i < booksToImport.size(); i++) {
                int authorId;
                bookToImportDto = booksToImport.get(i);
                authorToImportDto = bookToImportDto.getAuthor();
                existingAuthor = authorRepository.findByFirstNameAndLastNameAndDateOfBirth(
                        authorToImportDto.getFirstName(), authorToImportDto.getLastName(), authorToImportDto.getDateOfBirth());
                if(existingAuthor.isPresent()){
                    authorId = existingAuthor.get().getId();
                    existingAuthor = authorRepository.findById(authorId);
                    bookService.createBook(bookToImportDto, existingAuthor.get());
                } else {
                    authorId = authorService.createAuthor(authorToImportDto).getId();
                    System.out.println("Author Id: " + authorId);
                    newAuthor = authorRepository.findById(authorId);
                    bookService.createBook(bookToImportDto, newAuthor.get());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new MessageResponse("Books were imported successfully");
    }
}
