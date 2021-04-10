package es.dawgroup2.juding.competitions.rest;

import es.dawgroup2.juding.competitions.Competition;
import es.dawgroup2.juding.competitions.CompetitionService;
import es.dawgroup2.juding.fight.FightService;
import es.dawgroup2.juding.main.DateService;
import es.dawgroup2.juding.main.HeaderInflater;
import es.dawgroup2.juding.users.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Get a list with the competitions (paginated)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Page with more than one competition",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Competition.class))) }),
            @ApiResponse(responseCode = "400", description = "Request is invalid because of empty or non-existant page retrieve",
                    content = @Content)
    })
    @GetMapping("/list")
    public ResponseEntity<Page<Competition>> getCompetitionPage(@Parameter(description = "Number of page to be searched") @RequestParam(required = false) Integer page) {
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
    @Operation(summary = "Post a new competition")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Creation of a new competition",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CompetitionDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Competition cannot be created on the basis of failed data",
                    content = @Content)
    })
    @PostMapping("/new")
    public ResponseEntity<Competition> addCompetition(@Parameter(description = "Competition Data Transfer Object") @RequestBody CompetitionDTO competitionDTO) {
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
    @Operation(summary = "Existing competition edition")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Edit the competition",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CompetitionDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Competition cannot be modified on the basis of failed data",
                    content = @Content)
    })
    @PutMapping("/edit")
    public ResponseEntity<Competition> updatingCompetitionInfo(@Parameter(description = "Competition Data Transfer Object") @RequestBody CompetitionDTO competitionDTO) {
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
    @Operation(summary = "Elimination of a competition")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Elimination successfully completed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Competition.class)) }),
            @ApiResponse(responseCode = "404", description = "Request is invalid because of empty or non-existant competition retrieve",
                    content = @Content)
    })
    @DeleteMapping("/delete/{idCompetition}")
    public ResponseEntity<Competition> showCompetitionToDelete(@Parameter(description = "Identifier of competition to be deleted") @PathVariable String idCompetition) {
        Competition competition = competitionService.findById(Integer.parseInt(idCompetition));
        competitionService.deleteById(idCompetition);
        return (competition == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(competition);
    }
}
