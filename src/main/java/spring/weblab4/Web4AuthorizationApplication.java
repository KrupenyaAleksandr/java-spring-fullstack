package spring.weblab4;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

//21)	Разработка веб-сайта сервиса записной книжки.
// Аналог Evernote. Должны поддерживаться несколько видов заметок, например, отчет о путешествии, контакты, рецепты и т.п.
// Заметки могут быть отсортированы по самым разным признакам, тегам.
// Система администрирования для получения статистики сервиса, управления пользователями.
@SpringBootApplication
public class Web4AuthorizationApplication {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
    public static void main(String[] args) {
        SpringApplication.run(Web4AuthorizationApplication.class, args);
    }

}
