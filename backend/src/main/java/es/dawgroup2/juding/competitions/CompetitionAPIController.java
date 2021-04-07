package es.dawgroup2.juding.competitions;

import es.dawgroup2.juding.fight.FightService;
import es.dawgroup2.juding.main.DateService;
import es.dawgroup2.juding.main.HeaderInflater;
import es.dawgroup2.juding.users.User;
import es.dawgroup2.juding.users.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/competition")
public class CompetitionAPIController {
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
     * Gets the competition
     * @param idCompetition Id of the competition
     * @return Response Entity with the competition or bad request
     */
    @Operation(summary = "Gets a competition using its identifier (ID)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtaining the competition",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Competition.class)) }),
            @ApiResponse(responseCode = "404", description = "Request is invalid because of empty or non-existant competition retrieve",
                    content = @Content)
    })
    @GetMapping("/{idCompetition}")
    public ResponseEntity<Competition> showCompetition(@Parameter(description = "Identifier of competition to be obtained") @PathVariable String idCompetition) {
        Competition competition = competitionService.findById(Integer.parseInt(idCompetition));
        return (competition == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(competition);
    }

    /**
     * Joins a competition
     * @param idCompetition Id of the competition
     * @param request HTTP Servlet Request
     * @return Response Entity with the competition or bad request
     */
    @Operation(summary = "Registration of a user to a competition")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Join successfully completed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Join cannot be made because it was already made",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Join cannot be made on the basis of failed data",
                    content = @Content)
    })
    @PutMapping("/{idCompetition}/join")
    public ResponseEntity<Competition> joinCompetition(@Parameter(description = "Identifier of the competition")@PathVariable String idCompetition, HttpServletRequest request) {
        Competition competition = competitionService.findById(Integer.parseInt(idCompetition));
        User user = userService.findByNickname(request.getUserPrincipal().getName());
        if (fightService.checkParticipation(competition, user)) {
            return ResponseEntity.badRequest().build();
        } else {
            competitionService.joinCompetition(competition, user);
            competitionService.save(competition);
            return (competition == null) ? ResponseEntity.badRequest().build() : ResponseEntity.ok(competition);
        }
    }
}

