package pl.pp.project.dto.mappers;

import org.springframework.stereotype.Component;
import pl.pp.project.data.models.Author;
import pl.pp.project.data.models.Book;
import pl.pp.project.dto.AuthorDto;
import pl.pp.project.dto.impl.AuthorWithoutBooksDto;

import java.sql.Date;
import java.util.Set;

@Component
public class AuthorMapper {
    public static AuthorDto toAuthorWithoutBooks(Author author) {
        int id = author.getId();
        String firstName = author.getFirstName();
        String lastName = author.getLastName();
        Date dateOfBirth = author.getDateOfBirth();
        return new AuthorWithoutBooksDto(id, firstName, lastName, dateOfBirth);
    }

    public static Author toAuthor (AuthorDto authorWithoutBooks, Set<Book> booksOfAuthor) {
        Author author = new Author();
        author.setId(authorWithoutBooks.getId());
        author.setFirstName(authorWithoutBooks.getFirstName());
        author.setLastName(authorWithoutBooks.getLastName());
        author.setDateOfBirth(authorWithoutBooks.getDateOfBirth());
        author.setBooks(booksOfAuthor);
        return author;
    }
}
