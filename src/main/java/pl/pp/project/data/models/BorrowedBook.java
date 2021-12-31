package pl.pp.project.data.models;

import lombok.Data;

import javax.persistence.*;

@Entity
public @Data
class BorrowedBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int bookId;
    private int userId;
    private boolean active;

    public BorrowedBook() {
    }
}
