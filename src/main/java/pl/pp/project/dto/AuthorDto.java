package pl.pp.project.dto;

import pl.pp.project.data.models.Author;

import java.sql.Date;

public interface AuthorDto {
    int getId();
    String getFirstName();
    String getLastName();
    Date getDateOfBirth();
}
