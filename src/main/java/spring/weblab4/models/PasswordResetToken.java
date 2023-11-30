package spring.weblab4.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.Date;
import java.util.Optional;

@Entity
@Data
public class PasswordResetToken {

    private static final int EXPIRATION = 60;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "id")
    private @Valid Optional<User> user;

    private Date expiryDate;

    public PasswordResetToken(String token, @Valid Optional<User> user){
        this.token = token;
        this.user = user;
    }

    public PasswordResetToken() {

    }
}
