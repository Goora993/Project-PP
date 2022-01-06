package pl.pp.project.dto.impl;

import lombok.Data;
import pl.pp.project.dto.AuthorDto;

import java.sql.Date;

public @Data
class AuthorToImportDto implements AuthorDto {
    private String firstName;
    private String lastName;
    private Date dateOfBirth;

    public AuthorToImportDto() {

    }

    public AuthorToImportDto(String firstName, String lastName, Date dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }
}
