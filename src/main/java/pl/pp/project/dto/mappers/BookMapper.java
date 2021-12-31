package pl.pp.project.dto.mappers;

import org.springframework.stereotype.Component;
import pl.pp.project.data.models.Book;
import pl.pp.project.data.models.User;
import pl.pp.project.dto.AuthorDto;
import pl.pp.project.dto.BookDto;
import pl.pp.project.dto.impl.BookWithAuthorDto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookMapper {

        public static BookDto toBookWithAuthorDto(Book book, AuthorDto authorWithoutBooksDto) {
            Integer id = book.getId();
            String name = book.getName();
            String isbn = book.getIsbn();
            Date publicationYear = book.getPublicationYear();
            Boolean isBorrowed = book.isBorrowed();

            return new BookWithAuthorDto(id, name, isbn, publicationYear, isBorrowed, authorWithoutBooksDto);
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

        public static Book toBook (BookDto bookDto, User user) {
            Book book = new Book();
            book.setId(bookDto.getId());
            book.setName(bookDto.getName());
            book.setIsbn(bookDto.getIsbn());
            book.setPublicationYear(bookDto.getPublicationYear());
            book.setBorrowed(bookDto.isBorrowed());
            if(bookDto.isBorrowed()) {
                book.setUser(user);
            }
            return new Book();
        }
}
