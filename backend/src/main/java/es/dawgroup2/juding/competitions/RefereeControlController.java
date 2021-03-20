package es.dawgroup2.juding.competitions;

import es.dawgroup2.juding.users.User;
import es.dawgroup2.juding.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RefereeControlController {

    @Autowired
    CompetitionService competitionService;

    @Autowired
    UserService userService;

    @GetMapping("/competition/{idCompetition}/control/saveResult")
    public boolean controlCompetition(@PathVariable String idCompetition,
                                      @RequestParam String winner,
                                      @RequestParam String loser){
        Competition competition = competitionService.findById(Integer.parseInt(idCompetition));
        if (competition != null){
            // 1. Find users
            User winnerUser = userService.findByNickname(winner);
            User loserUser = userService.findByNickname(loser);
            // 2. Save result
            competitionService.fightFinished(competition, winnerUser, loserUser);
            competitionService.add(competition);
            return true;
        }
        return false;
    }
}
