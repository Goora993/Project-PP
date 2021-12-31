package pl.pp.project.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pp.project.data.models.Book;
import pl.pp.project.data.models.BorrowedBook;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Integer> {
    Optional<BorrowedBook> findBorrowedBookByActiveAndBookId(Boolean isActive, Integer bookId);
}
