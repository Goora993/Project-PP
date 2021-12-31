package pl.pp.project.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pp.project.data.models.Author;
import pl.pp.project.data.models.Book;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Set<Book> findBooksByAuthor(Author author);
    List<Book> findBooksByUserIdAndIsBorrowed(Integer userId, Boolean isBorrowed);
    List<Book> findBooksByIsBorrowed(Boolean isBorrowed);
}
