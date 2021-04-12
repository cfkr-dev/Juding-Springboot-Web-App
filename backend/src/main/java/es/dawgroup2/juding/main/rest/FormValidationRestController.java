package es.dawgroup2.juding.main.rest;

import es.dawgroup2.juding.competitions.CompetitionService;
import es.dawgroup2.juding.users.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/formCheck")
public class FormValidationRestController {

    @Autowired
    UserService userService;

    @Autowired
    CompetitionService competitionService;


    /**
     * Checks if the data introduced (license ID or nickname) by the user are not duplicated in the database.
     * License ID or nickname have to be unique when signing up.
     *
     * @param licenseId License ID of the user.
     * @param nickname  Nickname of the user.
     * @return An integer depending on the use case.
     */
    @Operation(summary = "Checks if the data introduced (license ID or nickname) by the user are not duplicated in the database.", description = "License ID or nickname have to be unique when signing up.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Checking was successful.",
                    content = {@Content(mediaType = "text/plain",
                            schema = @Schema(type = "integer"))})
    })
    @GetMapping("/signup")
    public int signUp(@Parameter(description = "License ID of the user.") @RequestParam String licenseId,
                      @Parameter(description = "Nickname of the user.") @RequestParam String nickname) {
        return userService.matchingLicenseOrNickname(licenseId, nickname);
    }

    /**
     * Checks if the data introduced (license ID and nickname) by the user are not duplicated in the database.
     * License ID and nickname have to be unique when updating a user.
     *
     * @param licenseId License ID of the user.
     * @param nickname  Nickname of the user.
     * @return An integer depending on the use case.
     */
    @Operation(summary = "Checks if the data introduced (license ID and nickname) by the user are not duplicated in the database.", description = "License ID and nickname have to be unique when updating a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Checking was successful.",
                    content = {@Content(mediaType = "text/plain",
                            schema = @Schema(type = "integer"))})
    })
    @GetMapping("/update")
    public boolean update(@Parameter(description = "License ID of the user.") @RequestParam String licenseId,
                          @Parameter(description = "Nickname of the user.") @RequestParam String nickname) {
        return userService.matchingLicenseAndNickname(licenseId, nickname);
    }

    /**
     * Checks if the values introduced by the user are correct when creating a competition.
     *
     * @param startDate The start date of a competition.
     * @param endDate   The end date of a competition.
     * @param minWeight Min Weight introduced.
     * @param maxWeight Max Weight introduced.
     * @return An integer depending on the use case.
     */
    @Operation(summary = "Checks if the values introduced by the user are correct when creating a competition.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Checking was successful.",
                    content = {@Content(mediaType = "text/plain",
                            schema = @Schema(type = "integer"))})
    })
    @GetMapping("/checkingNewCompetition")
    public int checkingNewCompetition(@Parameter(description = "The start date of a competition.") @RequestParam String startDate,
                                      @Parameter(description = "The end date of a competition.") @RequestParam String endDate,
                                      @Parameter(description = "Min Weight introduced.") @RequestParam String minWeight,
                                      @Parameter(description = "Max Weight introduced.") @RequestParam String maxWeight) {
        if (competitionService.checkingMinAndMaxWeight(minWeight, maxWeight)) {
            return (!competitionService.checkingDates(startDate, endDate)) ? 0 : 1;
        } else {
            return competitionService.checkingDates(startDate, endDate) ? 3 : 2;
        }
    }

    /**
     * Checks if the values introduced by the user are correct when updating a competition.
     *
     * @param startDate The start date of a competition.
     * @param endDate   The end date of a competition.
     * @param minWeight Min Weight introduced.
     * @param maxWeight Max Weight introduced.
     * @return An integer depending on the use case.
     */
    @Operation(summary = "Checks if the values introduced by the user are correct when updating a competition.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Checking was successful.",
                    content = {@Content(mediaType = "text/plain",
                            schema = @Schema(type = "integer"))})
    })
    @GetMapping("/checkingUpdatedCompetition")
    public int checkingUpdatedCompetitionData(@Parameter(description = "The start date of a competition.") @RequestParam String startDate,
                                              @Parameter(description = "The end date of a competition.") @RequestParam String endDate,
                                              @Parameter(description = "Min Weight introduced.") @RequestParam String minWeight,
                                              @Parameter(description = "Max Weight introduced.") @RequestParam String maxWeight) {
        if (competitionService.checkingMinAndMaxWeight(minWeight, maxWeight)) {
            return (!competitionService.checkingDatesAlt(startDate, endDate)) ? 0 : 1;
        } else {
            return competitionService.checkingDatesAlt(startDate, endDate) ? 3 : 2;
        }
    }
}