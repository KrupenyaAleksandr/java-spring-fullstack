package spring.weblab4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.weblab4.models.PasswordResetToken;

import java.util.Optional;

@Repository
public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
    Optional<PasswordResetToken> findPasswordResetTokenByUserId(int userId);
    Optional<PasswordResetToken> findPasswordResetTokenByToken(String s);
}
