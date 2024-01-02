package spring.weblab4.dto;

import lombok.Data;

import java.util.Calendar;

@Data
public class LogDto {
    int userId;
    String username;
    String action;
    Calendar time;
}
