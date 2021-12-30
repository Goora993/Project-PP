package pl.pp.project.data.models;

import lombok.Data;

import javax.persistence.*;

@Entity
public @Data
class BorrowedBook {
    @Id
    private int bookId;
    private int userId;
    private boolean active;

    public BorrowedBook() {
    }
}
