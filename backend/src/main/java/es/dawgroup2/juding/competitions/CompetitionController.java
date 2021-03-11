package es.dawgroup2.juding.competitions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Controller
public class CompetitionController {


    @Autowired
    CompetitionService competitionService;

    @GetMapping("/admin/competition/list")
    public String competitionList(Model model) {
        List<Competition> competitionList = competitionService.findAll();
        for (Competition competition: competitionList){
            String state= competition.translatingDates(competition.getStartDate(),competition.getEndDate());
            model.addAttribute("state", state);
        }
        model.addAttribute("competitionList", competitionList);
        return "/admin/competition/list";
    }

    @GetMapping("/admin/competition/edit/{idCompetition}")
    public String editCompetition(@PathVariable String idCompetition, Model model) {
        Competition competition = competitionService.findById(idCompetition);
        model.addAttribute("competition", competition);
        return "/admin/competition/edit";
    }

    @PostMapping("/admin/competition/edit")
    public String updatingCompetitionInfo(@RequestParam String idCompetition, @RequestParam String shortName, @RequestParam String additionalInfo, @RequestParam int minWeight, @RequestParam int maxWeight, @RequestParam Timestamp startDate, @RequestParam Timestamp endDate, @RequestParam String referee, @RequestParam int refereeStatus) {
        Competition competition = competitionService.findById(idCompetition);
        competition.setIdCompetition(Integer.parseInt(idCompetition));
        competition.setShortName(shortName);
        competition.setAdditionalInfo(additionalInfo);
        competition.setMinWeight(minWeight);
        competition.setMaxWeight(maxWeight);
        competition.setReferee(referee);
        competition.setRefereeStatus(refereeStatus);
        competition.setStartDate(startDate);
        competition.setEndDate(endDate);
        competitionService.updatingInfoCompetition(competition);
        return "redirect:/admin/competition/list";

    }

    @GetMapping("/admin/competition/deleteCompetition/{idCompetition}")
    public String showCompetitionToDelete(Model model, @PathVariable String idCompetition) {
        Competition competition = competitionService.findById(idCompetition);
        model.addAttribute("competition", competition);
        competitionService.deleteById(idCompetition);
        return "redirect:/admin/competition/list";
    }

    @GetMapping("/admin/competition/newCompetition")
    public String newCompetition(Model model){
        return "/admin/competition/newCompetition";
    }

    @PostMapping("/admin/competition/newCompetition")
    public String addACompetition(Competition competition) {
        competitionService.add(competition);
        return "redirect:/admin/competition/list";
    }

    @GetMapping("/competition/detail/{idCompetition}")
    public String showCompetition(Model model, @PathVariable String idCompetition){
        Competition competition= competitionService.findById(idCompetition);
        String state= competition.translatingDates(competition.getStartDate(),competition.getEndDate());
        model.addAttribute("state", state);
        model.addAttribute("competition", competition);
        return "/competition/detail";
    }


    /*@PostMapping("/admin/competition/edit")
    public String updatingCompetitionInfo(@RequestParam Competition competition){
        competitionService.updatingInfoCompetition(competition);
        return "redirect:/admin/competition/list";
    }*/

}
