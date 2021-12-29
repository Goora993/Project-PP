package pl.pp.project.data.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public @Data class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String isbn;
    private String publicationYear;
    private int authorId;
    private boolean isBorrowed;

    public Book() {

    }
}
