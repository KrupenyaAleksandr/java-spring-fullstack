package spring.weblab4.dto;

import lombok.Data;

import java.util.Calendar;

@Data
public class LogDto {
    Integer userId;
    String username;
    String action;
    Calendar time;
}
