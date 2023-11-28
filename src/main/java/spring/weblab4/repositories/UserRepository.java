package spring.weblab4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.weblab4.models.User;

import java.util.Optional;

// репозиторий, прослойка между entity и кодом приложения,
// реализует CRUD методы т.к наследуется от JpaRepository
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

}
