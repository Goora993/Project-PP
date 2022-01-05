package pl.pp.project.dto.mappers;

import org.springframework.stereotype.Component;
import pl.pp.project.data.models.Author;
import pl.pp.project.dto.AuthorDto;
import pl.pp.project.dto.impl.AuthorToImportDto;
import pl.pp.project.dto.impl.AuthorWithoutBooksDto;

import java.sql.Date;

@Component
public class AuthorMapper {
    public static AuthorDto toAuthorWithoutBooks(Author author) {
        int id = author.getId();
        String firstName = author.getFirstName();
        String lastName = author.getLastName();
        Date dateOfBirth = author.getDateOfBirth();
        return new AuthorWithoutBooksDto(id, firstName, lastName, dateOfBirth);
    }

    public static Author toAuthor (AuthorToImportDto authorToImportDto) {
        return new Author(authorToImportDto.getFirstName(), authorToImportDto.getLastName(), authorToImportDto.getDateOfBirth());
    }
}
