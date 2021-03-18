package es.dawgroup2.juding.competitions;

import es.dawgroup2.juding.auxTypes.attendances.AttendanceService;
import es.dawgroup2.juding.fight.Fight;
import es.dawgroup2.juding.main.DateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CompetitionController {

    @Autowired
    CompetitionService competitionService;

    @Autowired
    AttendanceService attendanceService;

    @Autowired
    DateService dateService;

    /**
     * @param model         model of the view
     * @param idCompetition id of the competition
     * @return view of the competition screen
     */
    @GetMapping("/competition/{idCompetition}")
    public String showCompetition(Model model, @PathVariable String idCompetition) {
        Competition competition = competitionService.findById(Integer.parseInt(idCompetition));
        String state = competition.translatingDates(competition.getStartDate(), competition.getEndDate());
        model.addAttribute("state", state)
                .addAttribute("competition", competition);
        for (int i = 0; i < competition.getFights().size(); i++)
            model.addAttribute("fight" + i, competition.getFights().get(i));
        return "/competition/detail";
    }

    @GetMapping("/competition/{idCompetition}/control")
    public String showControlScreen(Model model, @PathVariable String idCompetition) {
        Competition competition = competitionService.findById(Integer.parseInt(idCompetition));
        if (competition != null) {
            StringBuilder sb = new StringBuilder();
            List<Fight> fights = competition.getFights();

            sb.append("<optgroup label=\"Octavos\">");
            for (int i = 14; i >= 7; i--) {
                if (!fights.get(i).isFinished()) {
                    if (fights.get(i).getWinner() != null && fights.get(i).getLoser() != null) {
                        sb.append("<option value=\"")
                                .append(i)
                                .append("\" data-nick1=\"")
                                .append(fights.get(i).getWinner().getNickname())
                                .append("\" data-nick2=\"")
                                .append(fights.get(i).getLoser().getNickname())
                                .append("\">")
                                .append(fights.get(i).getWinner().getNickname())
                                .append(" - ")
                                .append(fights.get(i).getLoser().getNickname())
                                .append("</option>");
                    }
                }
            }
            sb.append("</optgroup>");

            sb.append("<optgroup label=\"Cuartos\">");
            for (int i = 6; i >= 3; i--) {
                if (!fights.get(i).isFinished()) {
                    if (fights.get(i).getWinner() != null & fights.get(i).getLoser() != null) {
                        sb.append("<option value=\"")
                                .append(i)
                                .append("\" data-nick1=\"")
                                .append(fights.get(i).getWinner().getNickname())
                                .append("\" data-nick2=\"")
                                .append(fights.get(i).getLoser().getNickname())
                                .append("\">")
                                .append(fights.get(i).getWinner().getNickname())
                                .append(" - ")
                                .append(fights.get(i).getLoser().getNickname())
                                .append("</option>");
                    }
                }
            }
            sb.append("</optgroup>");

            sb.append("<optgroup label=\"Semifinales\">");
            for (int i = 2; i >= 1; i--) {
                if (!fights.get(i).isFinished()) {
                    if (fights.get(i).getWinner() != null & fights.get(i).getLoser() != null) {
                        sb.append("<option value=\"")
                                .append(i)
                                .append("\" data-nick1=\"")
                                .append(fights.get(i).getWinner().getNickname())
                                .append("\" data-nick2=\"")
                                .append(fights.get(i).getLoser().getNickname())
                                .append("\">")
                                .append(fights.get(i).getWinner().getNickname())
                                .append(" - ")
                                .append(fights.get(i).getLoser().getNickname())
                                .append("</option>");
                    }
                }
            }
            sb.append("</optgroup>");

            sb.append("<optgroup label=\"Final\">");
            if (!fights.get(0).isFinished()) {
                if (fights.get(0).getWinner() != null & fights.get(0).getLoser() != null) {
                    sb.append("<option value=\"")
                            .append(0)
                            .append("\" data-nick1=\"")
                            .append(fights.get(0).getWinner().getNickname())
                            .append("\" data-nick2=\"")
                            .append(fights.get(0).getLoser().getNickname())
                            .append("\">")
                            .append(fights.get(0).getWinner().getNickname())
                            .append(" - ")
                            .append(fights.get(0).getLoser().getNickname())
                            .append("</option>");
                }
            }
            sb.append("</optgroup>");


            model.addAttribute("selectItems", sb.toString());
            return "competition/control";
        }
        return "redirect:/error/500";
    }
}