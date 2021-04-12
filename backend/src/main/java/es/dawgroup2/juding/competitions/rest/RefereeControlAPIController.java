package es.dawgroup2.juding.competitions.rest;

import es.dawgroup2.juding.competitions.Competition;
import es.dawgroup2.juding.competitions.CompetitionService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RefereeControlAPIController {

    @Autowired
    CompetitionService competitionService;

    @Autowired
    UserService userService;

    /**
     * Saves the result of a competition
     *
     * @param competitionResultDTO Competition result Data Transfer Object
     * @param request              HTTP Servlet Request
     * @return Response Entity with the competition changed or bad request
     */
    @Operation(summary = "Saves the result of a competition")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Save successfully completed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CompetitionResultDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Request is invalid because of non-finished competition retrieve",
                    content = @Content),
    })
    @PutMapping("/api/competition/control")
    public ResponseEntity<Competition> controlCompetition(@Parameter(description = "Competition result Data Transfer Object") @RequestBody CompetitionResultDTO competitionResultDTO,
                                                          HttpServletRequest request) {
        if (request.getUserPrincipal().getName().equals(competitionService.findById(Integer.parseInt(competitionResultDTO.getIdCompetition())).getReferee().getNickname())) {
            Competition competition = competitionService.findById(Integer.parseInt(competitionResultDTO.getIdCompetition()));
            if (competition != null) {
                // 1. Find users
                User winnerUser = userService.findByNickname(competitionResultDTO.getWinner());
                User loserUser = userService.findByNickname(competitionResultDTO.getLoser());
                // 2. Save result
                competitionService.fightFinished(competition, winnerUser, loserUser);
                competitionService.save(competition);
                return ResponseEntity.ok(competition);
            }
        }
        return ResponseEntity.notFound().build();
    }
}
