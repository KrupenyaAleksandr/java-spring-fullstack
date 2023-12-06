package spring.weblab4.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import java.util.Calendar;

@Entity
@Table(name = "logs")
@DynamicInsert
@Data
public class Log {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_id")
    private LogAction logAction;

    @Column(name = "action_time", columnDefinition = "TIMESTAMP DEFAULT LOCALTIMESTAMP")
    private Calendar action_time;

    public Log(User user, LogAction logAction){
        this.user = user;
        this.logAction = logAction;
    }
    public Log() {
        action_time = Calendar.getInstance();
    }
}
