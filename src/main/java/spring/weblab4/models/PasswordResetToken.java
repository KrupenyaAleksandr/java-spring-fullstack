package spring.weblab4.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

@Entity
@Table(name ="password_reset_tokens")
@DynamicInsert
@Data
public class PasswordResetToken {

    private static final int EXPIRATION = 60;

    @Id
    private int id;

    @Column(name = "token")
    private String token;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    @Column(name = "expire_date", columnDefinition = "TIMESTAMP DEFAULT LOCALTIMESTAMP + INTERVAL '1 hour'")
    private Calendar expire_date;

    public PasswordResetToken(String token, User user){
        this.token = token;
        this.user = user;
    }
    public PasswordResetToken() {
        expire_date = Calendar.getInstance();
        expire_date.add(Calendar.HOUR_OF_DAY, 1);
    }
}
