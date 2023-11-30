package spring.weblab4.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name ="password_reset_tokens")
@Data
public class PasswordResetToken {

    private static final int EXPIRATION = 60;

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "token")
    private String token;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    public PasswordResetToken(String token, User user){
        this.token = token;
        this.user = user;
    }
    public PasswordResetToken() {}
}
