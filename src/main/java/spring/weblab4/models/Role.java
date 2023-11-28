/*package spring.weblab4.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Collection;

@Entity // опрееделяем сущность, которая реализует таблицу из БД
@Table(name = "roles")
@Data // аннотация ломбок, создаёт геттеры, сеттеры, тустринг()
public class Role {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Size(max = 32)
    @Column(name = "role_name")
    //@OneToMany(mappedBy = "user_role")
    private String role_name;

    @OneToMany(mappedBy = "user_role")
    private Collection<User> users;

    Role(String role_name){
        this.role_name = role_name;
    }
}*/
