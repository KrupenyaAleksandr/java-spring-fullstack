package spring.weblab4.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import java.util.List;

@Entity
@Table(name = "users")
@DynamicInsert
@Data
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Введите почту")
    //@Size(min = 4, max = 128, message = "Некорректный email")
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "Введите пароль")
    @Column(name = "password")
    private String password;

    @Column(name = "role", columnDefinition = "VARCHAR(32) DEFAULT 'ROLE_USER'")
    private String role;

    @OneToOne(mappedBy = "user")
    private PasswordResetToken passwordResetToken;

    @OneToMany
    private List<Log> logs;
}