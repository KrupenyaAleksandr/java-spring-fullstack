package spring.weblab4.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import spring.weblab4.models.Log;
import spring.weblab4.models.LogAction;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public interface LogRepository extends JpaRepository<Log, Integer> {
    Page<Log> findAllBy(Pageable pageable);
    List<Log> findAllBylogAction(LogAction logAction);

    List<Log> findAllBylogActionAndActionTimeBetween(LogAction logAction, Calendar actionTimeStart, Calendar actionTimeEnd);
}
