package spring.weblab4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.weblab4.models.PasswordResetToken;

public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken, Integer> {

}
