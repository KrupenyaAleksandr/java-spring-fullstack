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
    private User userId;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "tag")
    private String tag;

    @Column(name = "updated_time")
    private Calendar updatedTime;

    public Note(User user){
        this.userId = user;
    }

    public Note(){
        title = "Новый заголовок...";
        body = "Напишите о чем то тут...";
        updatedTime = Calendar.getInstance();
    }
}
