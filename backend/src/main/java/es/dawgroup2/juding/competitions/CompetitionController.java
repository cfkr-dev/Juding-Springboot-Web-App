package es.dawgroup2.juding.competitions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class CompetitionController {

    @Autowired
    CompetitionRepository competitionRepository;

    @GetMapping("/admin/competition/list")
    public String competitionList(Model model) {
        List<Competition> competitionList = competitionRepository.findAll();
        model.addAttribute("competitionList", competitionList);
        return "/admin/competition/list";
    }
    @GetMapping("/admin/competition/edit/{idCompetition}")
    public String editCompetition(@PathVariable String idCompetition, Model model){
        Competition competition= competitionRepository.findById(idCompetition).orElseThrow();
        model.addAttribute("competition", competition);
        return "/admin/competition/edit/{idCompetition}";
    }

}
