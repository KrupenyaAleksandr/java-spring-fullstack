package spring.weblab4.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import java.util.Calendar;

@Entity
@Table(name = "notes")
@Data
@DynamicInsert
public class Note {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "tags")
    private String tags;

    @Column(name = "created_time")
    private Calendar created_time;

    @Column(name = "changed_time")
    private Calendar changed_time;

    @Column(name = "text")
    private String text;

    @Column(name = "title")
    private String title;

    public Note(User user){
        this.user = user;
    }

    public Note(){
        created_time = Calendar.getInstance();
        changed_time = Calendar.getInstance();
        title = "Untitled";
    }
}
