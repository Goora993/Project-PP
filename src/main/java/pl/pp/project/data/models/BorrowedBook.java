package pl.pp.project.data.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public @Data
class BorrowedBook {
    @Id
    private int bookId;
    private int userId;
    @Column(columnDefinition = "tinyint(1) default 1")
    private boolean active;

    public BorrowedBook() {
    }
}
