package pl.pp.project.dto.impl;

import lombok.Data;

import java.sql.Date;

public @Data
class BookToImportDto {
    private String name;
    private String isbn;
    private Date publicationYear;
    private AuthorToImportDto author;

    public BookToImportDto(){

    }

    public BookToImportDto(String name, String isbn, Date publicationYear, AuthorToImportDto author) {
        this.name = name;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.author = author;
    }
}
