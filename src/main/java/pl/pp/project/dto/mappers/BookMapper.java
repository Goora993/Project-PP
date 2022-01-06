package pl.pp.project.dto.mappers;

import org.springframework.stereotype.Component;
import pl.pp.project.data.models.Book;
import pl.pp.project.data.models.User;
import pl.pp.project.dto.AuthorDto;
import pl.pp.project.dto.BookDto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookMapper {

        public static BookDto toBookWithAuthorDto(Book book, AuthorDto authorWithoutBooksDto) {
            int id = book.getId();
            String name = book.getName();
            String isbn = book.getIsbn();
            Date publicationYear = book.getPublicationYear();
            boolean isBorrowed = book.isBorrowed();

            return new pl.pp.project.dto.impl.BookWithAuthorDto(id, name, isbn, publicationYear, isBorrowed, authorWithoutBooksDto);
        }

        public static List<BookDto> bookListToBookWithAuthorDtoList(List<Book> books) {
            List<BookDto> bookWithAuthorDtos = new ArrayList<>();

            for (int i = 0; i < books.size(); i++) {
                AuthorDto authorWithoutBooksDto = AuthorMapper.toAuthorWithoutBooks(books.get(i).getAuthor());
                BookDto bookWithAuthorDto = BookMapper.toBookWithAuthorDto(books.get(i), authorWithoutBooksDto);
                bookWithAuthorDtos.add(i, bookWithAuthorDto);
            }
            return bookWithAuthorDtos;
        }
}
