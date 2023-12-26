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
    private User logUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_id")
    private LogAction logAction;

    @Column(name = "action_time", columnDefinition = "TIMESTAMP DEFAULT LOCALTIMESTAMP")
    private Calendar actionTime;

    public Log(User user, LogAction action){
        this.logUser = user;
        this.logAction = action;
    }
    public Log() {
        actionTime = Calendar.getInstance();
    }
}
