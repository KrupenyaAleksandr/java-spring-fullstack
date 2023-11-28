package spring.weblab4.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.Set;

@Entity // опрееделяем сущность, которая реализует таблицу из БД
@Table(name = "users")
@Data // аннотация ломбок, создаёт геттеры, сеттеры, тустринг()
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Введите логин")
    @Size(min = 2, max = 32, message = "Логин должен быть длиной от 2 до 32 символов")
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "Введите пароль")
    @Size(min = 6, max = 32, message = "Пароль должен быть длиной от 6 до 32 символов")
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    //@DefaultValue("ROLE_USER")
    private String role;

    //@NotEmpty(message = "Введите почту")
    //@Size(min = 8, max = 64, message = "Некорректный email")
    @Column(name = "email")
    private String email;

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
}