package es.dawgroup2.juding.competitions.rest;

import es.dawgroup2.juding.competitions.Competition;
import es.dawgroup2.juding.competitions.CompetitionService;
import es.dawgroup2.juding.fight.FightService;
import es.dawgroup2.juding.main.DateService;
import es.dawgroup2.juding.main.HeaderInflater;
import es.dawgroup2.juding.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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

    /**
     * Gets a list with the competitions (paginated)
     *
     * @param page Number of the page
     * @return Response Entity with the competition or bad request
     */
    @GetMapping("/list")
    public ResponseEntity<Page<Competition>> getCompetitionPage(@RequestParam(required = false) Integer page) {
        int defPage = (page == null) ? 0 : page;
        if (defPage < 0) {
            return ResponseEntity.badRequest().build();
        }
        Page<Competition> competitionPage = competitionService.getCompetitionsInPages(defPage);
        if (competitionPage.hasContent()) {
            return ResponseEntity.ok(competitionPage);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Generates a new competition with the received information.
     *
     * @param competitionDTO Competition Data Transfer Object
     * @return Response Entity with the competition or bad request
     */
    @PostMapping("/new")
    public ResponseEntity<Competition> addCompetition(@RequestBody CompetitionDTO competitionDTO) {
        Competition competition;
        try {
            competition = competitionService.save(null,
                    competitionDTO.getShortName(),
                    competitionDTO.getAdditionalInfo(),
                    Integer.parseInt(competitionDTO.getMinWeight()),
                    Integer.parseInt(competitionDTO.getMaxWeight()),
                    competitionDTO.getStartDate(),
                    competitionDTO.getEndDate(),
                    competitionDTO.getReferee());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        URI location = fromCurrentRequest().path("/api/competition/{idCompetition}").buildAndExpand(competition.getIdCompetition()).toUri();
        return ResponseEntity.created(location).body(competition);
    }

    /**
     * Edits a competition
     *
     * @param competitionDTO Competition Data Transfer Object
     * @return Response Entity with the competition or bad request
     */
    @PutMapping("/edit")
    public ResponseEntity<Competition> updatingCompetitionInfo(@RequestBody CompetitionDTO competitionDTO) {
        Competition competition;
        try {
            competition = competitionService.save(competitionDTO.getIdCompetition(),
                    competitionDTO.getShortName(),
                    competitionDTO.getAdditionalInfo(),
                    Integer.parseInt(competitionDTO.getMinWeight()),
                    Integer.parseInt(competitionDTO.getMaxWeight()),
                    competitionDTO.getStartDate(),
                    competitionDTO.getEndDate(),
                    competitionDTO.getReferee());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return (competition == null) ? ResponseEntity.badRequest().build() : ResponseEntity.ok(competition);
    }

    /**
     * Deletes a competition
     *
     * @param idCompetition Id of the competition
     * @return Response Entity with the competition or bad request
     */
    @DeleteMapping("/delete/{idCompetition}")
    public ResponseEntity<Competition> showCompetitionToDelete(@PathVariable String idCompetition) {
        Competition competition = competitionService.findById(Integer.parseInt(idCompetition));
        competitionService.deleteById(idCompetition);
        return (competition == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(competition);
    }
}
