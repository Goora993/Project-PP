package pl.pp.project.dto.impl;

import lombok.Data;
import pl.pp.project.dto.AuthorDto;

import java.sql.Date;

public @Data class AuthorWithoutBooksDto implements AuthorDto {
    private int id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;

    public AuthorWithoutBooksDto(int id, String firstName, String lastName, Date dateOfBirth) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }
}
