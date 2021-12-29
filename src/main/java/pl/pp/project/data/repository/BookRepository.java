package pl.pp.project.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pp.project.data.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}
