package pl.pp.project.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pp.project.data.models.Author;

import java.sql.Date;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Optional<Author> findByFirstNameAndLastNameAndDateOfBirth(String firstName, String lastName, Date dateOfBirth);
}

