package spring.weblab4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.weblab4.models.LogAction;

public interface LogActionRepository extends JpaRepository<LogAction, Integer> {

}
