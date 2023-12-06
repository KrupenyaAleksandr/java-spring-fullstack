package spring.weblab4.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import java.util.List;

@Entity // опрееделяем сущность, которая реализует таблицу из БД
@Table(name = "users")
@DynamicInsert
@Data // аннотация ломбок, создаёт геттеры, сеттеры, тустринг()
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
    //@Size(min = 6, max = 32, message = "Пароль должен быть длиной от 6 до 32 символов")
    @Column(name = "password")
    private String password;

    @Column(name = "role", columnDefinition = "VARCHAR(32) DEFAULT 'ROLE_USER'")
    private String role;

    //@NotEmpty(message = "Введите имя")
    //@Size(min = 2, max = 32, message = "Некорректное имя")
    @Column(name = "first_name")
    private String first_name;

    //@Size(min = 2, max = 32, message = "Некорректное отчество")
    @Column(name = "middle_name")
    private String middle_name;

    //@NotEmpty(message = "Введите фамилию")
    //@Size(min = 2, max = 32, message = "Некорректная фамилия")
    @Column(name = "last_name")
    private String last_name;

    @OneToOne(mappedBy = "user")
    private PasswordResetToken passwordResetTokenList;

    @OneToMany
    private List<Log> logs;
}