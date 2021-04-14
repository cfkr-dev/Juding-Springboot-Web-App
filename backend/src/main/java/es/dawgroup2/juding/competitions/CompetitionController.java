package es.dawgroup2.juding.competitions;

import es.dawgroup2.juding.fight.Fight;
import es.dawgroup2.juding.fight.FightService;
import es.dawgroup2.juding.main.DateService;
import es.dawgroup2.juding.main.HeaderInflater;
import es.dawgroup2.juding.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CompetitionController {
    @Autowired
    HeaderInflater headerInflater;

    @Autowired
    UserService userService;

    @Autowired
    CompetitionService competitionService;

    @Autowired
    FightService fightService;

    @Autowired
    DateService dateService;

    /**
     * Returns the dynamic view of a competition, including the necessary information for displaying the competition tree and the podium when it's finished.
     *
     * @param model         Model of the view
     * @param idCompetition Id of the competition
     * @return View of the competition screen
     */
    @GetMapping("/competition/{idCompetition}")
    public String showCompetition(@PathVariable String idCompetition, HttpServletRequest request, Model model) {
        Competition competition = competitionService.findById(Integer.parseInt(idCompetition));
        String state = competition.translatingDates(competition.getStartDate(), competition.getEndDate());
        model.addAllAttributes(headerInflater.getHeader("Competición", request, "font-awesome/css/all.css", "bootstrap/css/bootstrap.min.css", "header", "responsiveTable", "competitionScreen", "beltAssignations"))
                .addAttribute("state", state)
                .addAttribute("people", fightService.countParticipants(competition))
                .addAttribute("competition", competition);
        for (int i = 0; i < competition.getFights().size(); i++)
            model.addAttribute("fight" + i, competition.getFights().get(i));
        return "competition/detail";
    }

    /**
     * Receives a future competition and a user and adds the user into the competition if there is enough place.
     *
     * @param idCompetition ID of the competition.
     * @param request       HTTP Servlet request (brings the user nickname).
     * @param model         Model.
     * @return Redirection to competition detail page
     */
    @GetMapping("/competition/{idCompetition}/join")
    public String joinCompetition(@PathVariable String idCompetition, HttpServletRequest request, Model model) {
        Competition competition = competitionService.findById(Integer.parseInt(idCompetition));
        competitionService.joinCompetition(competition, userService.findByNickname(request.getUserPrincipal().getName()));
        competitionService.save(competition);
        return "redirect:/competition/" + idCompetition;
    }

    /**
     * Returns the frontend-made controller for referring competitions.
     *
     * @param model         Model.
     * @param idCompetition ID of the refereed competition.
     * @return Dynamic view of screen, including all the required elements to proportionate complete functionality.
     */
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


            model.addAttribute("selectItems", sb.toString())
                    .addAttribute("idCompetition", idCompetition);
            return "competition/control";
        }
        return "redirect:/error/500";
    }
}