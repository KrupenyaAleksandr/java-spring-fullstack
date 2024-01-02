package spring.weblab4.dto;

import lombok.Data;

import java.util.Calendar;

@Data
public class NoteDto {
    int noteId;
    int userId;
    String title;
    String text;
    String tag;
    Calendar updatedTime;
}
