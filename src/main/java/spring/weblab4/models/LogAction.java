package spring.weblab4.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "actions")
@Data
public class LogAction {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "action")
    private String action;

    @OneToMany
    private List<Log> logs;

    public LogAction(int action_id){
        this.id = action_id;
    }

    public LogAction() {

    }
}
