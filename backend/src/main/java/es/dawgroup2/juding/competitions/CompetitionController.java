package es.dawgroup2.juding.competitions;

import es.dawgroup2.juding.auxTypes.attendances.AttendanceService;
import es.dawgroup2.juding.main.DateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CompetitionController {


    @Autowired
    CompetitionService competitionService;

    @Autowired
    AttendanceService attendanceService;

    @Autowired
    DateService dateService;

    /**
     *
     * @param model model of the view
     * @param idCompetition id of the competition
     * @return view of the competition screen
     */
    @GetMapping("/competition/detail/{idCompetition}")
    public String showCompetition(Model model, @PathVariable String idCompetition) {
        Competition competition = competitionService.findById(idCompetition);
        String state = competition.translatingDates(competition.getStartDate(), competition.getEndDate());
        model.addAttribute("state", state);
        model.addAttribute("competition", competition);
        return "/competition/detail";
    }

}
