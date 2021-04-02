package es.dawgroup2.juding.competitions;

import es.dawgroup2.juding.users.User;
import es.dawgroup2.juding.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RefereeControlAPIController{

        @Autowired
        CompetitionService competitionService;

        @Autowired
        UserService userService;

        @PutMapping("/api/competition/{idCompetition}/control/saveResult")
        public ResponseEntity<Competition> controlCompetition(@PathVariable String idCompetition,
                                          @RequestParam String winner,
                                          @RequestParam String loser,
                                          HttpServletRequest request) {
            if (request.getUserPrincipal().getName().equals(competitionService.findById(Integer.parseInt(idCompetition)).getReferee().getNickname())){
                Competition competition = competitionService.findById(Integer.parseInt(idCompetition));
                if (competition != null) {
                    // 1. Find users
                    User winnerUser = userService.findByNickname(winner);
                    User loserUser = userService.findByNickname(loser);
                    // 2. Save result
                    competitionService.fightFinished(competition, winnerUser, loserUser);
                    competitionService.add(competition);
                    return ResponseEntity.ok(competition);
                }
                return ResponseEntity.notFound().build();
            }
            else{
                return ResponseEntity.notFound().build();
            }
        }
}
