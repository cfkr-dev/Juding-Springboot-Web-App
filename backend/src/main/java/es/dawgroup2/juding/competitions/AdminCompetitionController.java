package es.dawgroup2.juding.competitions;

import es.dawgroup2.juding.fight.FightService;
import es.dawgroup2.juding.main.DateService;
import es.dawgroup2.juding.main.HeaderInflater;
import es.dawgroup2.juding.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

@Controller
public class AdminCompetitionController {
    @Autowired
    HeaderInflater headerInflater;

    @Autowired
    CompetitionService competitionService;

    @Autowired
    DateService dateService;

    @Autowired
    UserService userService;

    @Autowired
    FightService fightService;

    /**
     * @param model model of the view
     * @return the view of the competition list
     */
    @GetMapping("/admin/competition/list")
    public String competitionList(HttpServletRequest request, Model model) {
        List<Competition> competitionList = competitionService.findAll();
        model.addAttribute("competitionList", competitionList)
                .addAttribute("header", headerInflater.getHeader("Lista de competiciones", request, "bootstrap/css/bootstrap.min.css", "aos/aos.css", "font-awesome/css/all.css", "style", "header", "bootstrapAccomodations", "responsiveTable", "adminScreen"));
        return "/admin/competition/list";
    }

    /**
     * @param idCompetition id of the competition
     * @param model         model of the view
     * @return view of the competition to edit
     */
    @GetMapping("/admin/competition/edit/{idCompetition}")
    public String editCompetition(@PathVariable String idCompetition, HttpServletRequest request, Model model) {
        Competition competition = competitionService.findById(Integer.parseInt(idCompetition));
        model.addAttribute("competition", competition)
                .addAttribute("header", headerInflater.getHeader("Edición de competición", request, "bootstrap/css/bootstrap.min.css", "bootstrap-datepicker/bootstrap-datepicker.css", "font-awesome/css/all.css", "header", "bootstrapAccomodations", "loginAndRegistration"))
                .addAttribute("refereeList", userService.getActiveRefereesList(competition.getReferee().getLicenseId()));
        return "/admin/competition/edit";
    }

    /**
     * @param model model of the view
     * @return view of the competition to add
     */
    @GetMapping("/admin/competition/newCompetition")
    public String newCompetition(HttpServletRequest request, Model model) {
        model.addAttribute("header", headerInflater.getHeader("Nueva competición", request, "bootstrap/css/bootstrap.min.css", "bootstrap-datepicker/bootstrap-datepicker.css", "font-awesome/css/all.css", "header", "bootstrapAccomodation", "loginAndRegistration"))
                .addAttribute("refereeList", userService.getActiveRefereesList());
        return "/admin/competition/newCompetition";
    }

    /**
     * Generates a new competition
     *
     * @param shortName      The short name of the competition
     * @param additionalInfo Info about the competition
     * @param minWeight      The minimum weight allowed in a competition
     * @param maxWeight      The maximum weight allowed in a competition
     * @param startDate      The start date of a competition
     * @param endDate        The end date of a competition
     * @param referee        referee The license of the referee in charge of the competition
     * @return The new competition
     * @throws IOException
     */
    @PostMapping("/admin/competition/newCompetition")
    public String addACompetition(@RequestParam String shortName,
                                  @RequestParam String additionalInfo,
                                  @RequestParam int minWeight,
                                  @RequestParam int maxWeight,
                                  @RequestParam String startDate,
                                  @RequestParam String endDate,
                                  @RequestParam String referee) throws ParseException {
        Competition competition = new Competition();
        competition.setShortName(shortName)
                .setAdditionalInfo(additionalInfo)
                .setMinWeight(minWeight)
                .setMaxWeight(maxWeight)
                .setStartDate(dateService.stringToTimestamp(startDate))
                .setEndDate(dateService.stringToTimestamp(endDate))
                .setReferee(userService.getUserOrNull(referee));

        return "redirect:/admin/competition/list";
    }

    /**
     * Form to edit a competition
     *
     * @param idCompetition  Id of a competition
     * @param shortName      The name of a competitiom
     * @param additionalInfo Information of a competition
     * @param minWeight      The minimum weight allowed in a competition
     * @param maxWeight      The maximum weight allowed in a competition
     * @param startDate      The start date of a competition
     * @param endDate        The end date of a competition
     * @param referee        The license of the referee in charge of the competition
     * @return The competition edited
     * @throws IOException
     * @throws SQLException
     */
    @PostMapping("/admin/competition/edit")
    public String updatingCompetitionInfo(@RequestParam String idCompetition,
                                          @RequestParam String shortName,
                                          @RequestParam String additionalInfo,
                                          @RequestParam int minWeight,
                                          @RequestParam int maxWeight,
                                          @RequestParam String startDate,
                                          @RequestParam String endDate,
                                          @RequestParam String referee) throws ParseException {
        Competition competition = competitionService.findById(Integer.parseInt(idCompetition));
        competition.setShortName(shortName)
                .setAdditionalInfo(additionalInfo)
                .setMinWeight(minWeight)
                .setMaxWeight(maxWeight)
                .setReferee(userService.getUserOrNull(referee))
                .setStartDate(dateService.stringToTimestamp(startDate))
                .setEndDate(dateService.stringToTimestamp(endDate));
        competitionService.updatingInfoCompetition(competition);
        return "redirect:/admin/competition/list";

    }

    /**
     * This method deletes the competition selected
     *
     * @param model         model of the view
     * @param idCompetition id of the competition
     * @return view of the competition list
     */
    @GetMapping("/admin/competition/delete/{idCompetition}")
    public String showCompetitionToDelete(Model model, @PathVariable String idCompetition) {
        Competition competition = competitionService.findById(Integer.parseInt(idCompetition));
        model.addAttribute("competition", competition);
        competitionService.deleteById(idCompetition);
        return "redirect:/admin/competition/list";
    }

}
