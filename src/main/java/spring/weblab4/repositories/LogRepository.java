package spring.weblab4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.weblab4.models.Log;

public interface LogRepository extends JpaRepository<Log, Integer> {

}
