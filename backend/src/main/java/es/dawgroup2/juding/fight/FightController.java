package es.dawgroup2.juding.fight;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class FightController {

    @Autowired FightService fightService;

    /**
     * List of the fights
     * @param model Model
     * @return A view of the fights
     */
    @GetMapping("/admin/fight/list")
    public String fightList(Model model) {
        List<Fight> fightList= fightService.findAll();
        model.addAttribute("fightList",fightList);
        return "admin/fight/list";
    }

}
