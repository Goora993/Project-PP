package pl.pp.project.dto.mappers;

import pl.pp.project.data.models.User;
import pl.pp.project.dto.BookDto;
import pl.pp.project.dto.impl.UserBookWithAuthorDto;

import java.util.List;

public class UserMapper {

    public static UserBookWithAuthorDto userToUserBookWithAuthorDto(User user) {
        int id = user.getId();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String pesel = user.getPesel();
        List<BookDto> borrowedBooks = BookMapper.bookListToBookWithAuthorDtoList(user.getBorrowedBooks()) ;
        return new UserBookWithAuthorDto(id, firstName, lastName, pesel, borrowedBooks);
    }

}

