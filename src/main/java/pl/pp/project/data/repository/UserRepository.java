package pl.pp.project.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pp.project.data.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
