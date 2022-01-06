package pl.pp.project.dto.impl;

import lombok.Data;
import pl.pp.project.dto.BookDto;
import pl.pp.project.dto.UserDto;

import java.util.List;

public @Data
class UserBookWithAuthorDto implements UserDto {
    private int id;
    private String firstName;
    private String lastName;
    private String pesel;
    private List<BookDto> borrowedBooks;

    public UserBookWithAuthorDto(int id, String firstName, String lastName, String pesel, List<BookDto> borrowedBooks) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.borrowedBooks = borrowedBooks;
    }
}
