package pl.pp.project.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pp.project.data.models.BorrowedBook;

public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Integer> {
}
