package es.dawgroup2.juding.competitions;

import es.dawgroup2.juding.fight.FightService;
import es.dawgroup2.juding.main.DateService;
import es.dawgroup2.juding.main.HeaderInflater;
import es.dawgroup2.juding.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.text.ParseException;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/admin/competition")
public class AdminCompetitionAPIController {

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

    @GetMapping("/list")
    public ResponseEntity<Page<Competition>> getCompetitionPage(@RequestParam(required = false) Integer page) {
        int defPage = (page == null) ? 1 : page;
        if (defPage<0){
            return ResponseEntity.badRequest().build();
        }
        Page<Competition> competitionPage = competitionService.getCompetitionsInPages(page);
        if (competitionPage.hasContent()) {
            return ResponseEntity.ok(competitionPage);
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Generates a new competition with the received information.
     *
     * @param shortName      The short name of the competition
     * @param additionalInfo Info about the competition
     * @param minWeight      The minimum weight allowed in a competition
     * @param maxWeight      The maximum weight allowed in a competition
     * @param startDate      The start date of a competition
     * @param endDate        The end date of a competition
     * @param referee        The license of the referee in charge of the competition
     * @return Redirection to the list of competitions, where new competition will be listed.
     */
    @PostMapping("/newCompetition")
    public ResponseEntity<Competition> addCompetition(@RequestParam String shortName,
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
        URI location = fromCurrentRequest().path("/api/competition/{idCompetition}").buildAndExpand(competition.getIdCompetition()).toUri();
        return ResponseEntity.created(location).body(competition);
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
     * @throws ParseException Parsing exception
     */
    @PutMapping("/edit")
    public ResponseEntity<Competition> updatingCompetitionInfo(@RequestParam String idCompetition,
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
        if (competition != null) {
            return ResponseEntity.ok(competition);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @DeleteMapping("/delete/{idCompetition}")
    public ResponseEntity<Competition> showCompetitionToDelete(@PathVariable String idCompetition) {
        Competition competition = competitionService.findById(Integer.parseInt(idCompetition));
        competitionService.deleteById(idCompetition);
        if (competition != null) {
            return ResponseEntity.ok(competition);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
