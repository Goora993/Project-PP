package pl.pp.project.data.models;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Date;

@Entity
public @Data class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String isbn;
    private Date publicationYear;
    private boolean isBorrowed;

    @ManyToOne(cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name="author_id")
    @JsonBackReference
    private Author author;

    @ManyToOne(cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name="user_id")
    @JsonBackReference
    private User user;

    public int getAuthorId(){
        return author.getId();
    }

    public Book() {

    }
}
