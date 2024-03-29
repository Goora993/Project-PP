package pl.pp.project.dto.impl;


import lombok.Data;

import pl.pp.project.dto.AuthorDto;
import pl.pp.project.dto.BookDto;

import java.sql.Date;

public @Data class BookWithAuthorDto implements BookDto {
        private int id;
        private String name;
        private String isbn;
        private Date publicationYear;
        private boolean isBorrowed;
        private AuthorDto author;

    public BookWithAuthorDto() {
    }

    public BookWithAuthorDto(int id, String name, String isbn, Date publicationYear, boolean isBorrowed, AuthorDto author) {
        this.id = id;
        this.name = name;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.isBorrowed = isBorrowed;
        this.author = author;
    }
}
