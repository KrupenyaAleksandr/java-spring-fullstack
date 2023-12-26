package spring.weblab4.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.weblab4.dto.LogDto;
import spring.weblab4.models.Log;
import spring.weblab4.repositories.LogRepository;
import spring.weblab4.services.LogService;
import spring.weblab4.services.MappingDtoService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final LogService logService;

    public AdminController(LogRepository logRepository, MappingDtoService mappingDtoService, LogService logService) {
        this.logService = logService;
    }

    @GetMapping("")
    public String adminPage(){
        return "admin-page";
    }

    @GetMapping("/logs")
    public String logsPage(Model model,
                           @RequestParam(value = "page", defaultValue = "1") int currentPage,
                           @RequestParam(value = "size", defaultValue = "30") int pageSize){
        Page<LogDto> logPage = logService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("logPage", logPage);
        int totalPages = logPage.getTotalPages();
        if (totalPages > 0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "logs";
    }

    @GetMapping("/logs/chart")
    public String logsChartPage(Model model,
                                @RequestParam(name = "fromDate", required=false) String fromDate,
                                @RequestParam(name = "toDate", required=false) String toDate) throws ParseException {
        Map<String, Integer> data = new LinkedHashMap<String, Integer>();
        if (fromDate == null || toDate == null) {
            data = logService.getAllByAction(null, null);
        }
        else {
            Calendar fromDateCalendar = Calendar.getInstance();
            Calendar toDateCalendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            fromDateCalendar.setTime(simpleDateFormat.parse(fromDate));
            toDateCalendar.setTime(simpleDateFormat.parse(toDate));
            data = logService.getAllByAction(fromDateCalendar, toDateCalendar);
        }
        model.addAttribute("keySet", data.keySet());
        model.addAttribute("values", data.values());
        model.addAttribute("maxLogSize", Collections.max(data.values()) + 10);
        return "chart";
    }
}
