package es.dawgroup2.juding.main.rest;

import com.fasterxml.jackson.annotation.JsonView;
import es.dawgroup2.juding.auxTypes.belts.BeltService;
import es.dawgroup2.juding.auxTypes.refereeRange.RefereeRangeService;
import es.dawgroup2.juding.auxTypes.roles.Role;
import es.dawgroup2.juding.competitions.Competition;
import es.dawgroup2.juding.competitions.CompetitionService;
import es.dawgroup2.juding.main.DateService;
import es.dawgroup2.juding.main.image.ImageService;
import es.dawgroup2.juding.users.User;
import es.dawgroup2.juding.users.UserService;
import es.dawgroup2.juding.users.rest.AdminUserEditionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LoggedInUserAPIController {
    @Autowired
    UserService userService;

    @Autowired
    CompetitionService competitionService;

    @Autowired
    BeltService beltService;

    @Autowired
    DateService dateService;

    @Autowired
    ImageService imageService;

    @Autowired
    RefereeRangeService refereeRangeService;

    /**
     * Returns user profile data.
     *
     * @param id Id of the user.
     * @param request HTTP Servlet Request.
     * @return User profile information.
     */
    @Operation(summary = "Returns user profile data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile information.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "403", description = "Not allowed.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Requested user was not found.",
                    content = @Content)
    })
    @GetMapping(value = {"/competitors/{id}", "/referees/{id}"})
    public ResponseEntity<User> profileInfo(@Parameter(description = "ID of user.") @PathVariable String id,
                                            @Parameter(description = "HTTP Servlet Request.") HttpServletRequest request) {
        User currentUser = userService.getUserOrNull(id);
        if (currentUser == null) return ResponseEntity.notFound().build();
        if (currentUser.getNickname().equals(request.getUserPrincipal().getName()) || userService.findByNickname(request.getUserPrincipal().getName()).isRole(Role.A))
            return ResponseEntity.ok(currentUser);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    /**
     * Returns user chart information.
     *
     * @param id Id of the user.
     * @param request HTTP Servlet Request.
     * @return User profile information.
     */
    @Operation(summary = "Returns user profile data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User chart information.",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Integer.class)))}),
            @ApiResponse(responseCode = "403", description = "Not allowed.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Requested user was not found.",
                    content = @Content)
    })
    @GetMapping(value = {"/competitors/points/{id}"})
    public ResponseEntity<List<Integer>> chartInfo(@Parameter(description = "ID of user.") @PathVariable String id,
                                                   @Parameter(description = "HTTP Servlet Request.") HttpServletRequest request) {
        User currentUser = userService.getUserOrNull(id);
        if (currentUser == null) return ResponseEntity.notFound().build();
        if (currentUser.getNickname().equals(request.getUserPrincipal().getName()) || userService.findByNickname(request.getUserPrincipal().getName()).isRole(Role.A))
            return ResponseEntity.ok(currentUser.getCompetitorMedals());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    /**
     * Returns a list of past competitions of currently logged-in user.
     *
     * @param id Id of the user.
     * @return List of past competitions of currently logged-in user.
     */
    @Operation(summary = "Returns a list of past competitions of currently logged-in user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of past competitions of currently logged-in user.",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Competition.class)))}),
            @ApiResponse(responseCode = "403", description = "Not allowed (user is not logged in).",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Currently logged in user was not found.",
                    content = @Content)
    })
    @GetMapping("/competitions/{id}/past")
    @JsonView(Competition.MainAttributes.class)
    public ResponseEntity<List<Competition>> pastCompetitions(@Parameter(description = "ID of user.") @PathVariable String id) {
        User currentUser = userService.getUserOrNull(id);
        return (currentUser != null) ? ResponseEntity.ok(competitionService.getPastFights(currentUser)) : ResponseEntity.notFound().build();
    }

    /**
     * Returns a list of current competitions of logged-in user.
     *
     * @param id Id of the user.
     * @return List of current competitions of logged-in user.
     */
    @Operation(summary = "Returns a list of current competitions of logged-in user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of current competitions of logged-in user.",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Competition.class)))}),
            @ApiResponse(responseCode = "403", description = "Not allowed (user is not logged in).",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Currently logged in user was not found.",
                    content = @Content)
    })
    @GetMapping("/competitions/{id}/current")
    @JsonView(Competition.MainAttributes.class)
    public ResponseEntity<List<Competition>> currentCompetitions(@Parameter(description = "ID of user.") @PathVariable String id) {
        User currentUser = userService.getUserOrNull(id);
        return (currentUser != null) ? ResponseEntity.ok(competitionService.getCurrentCompetitions(currentUser)) : ResponseEntity.notFound().build();
    }

    /**
     * Returns a list of future competitions of logged-in user, distinguishing if user has joined them or not.
     *
     * @param joined  True if returning joined future competitions, false otherwise.
     * @param id Id of the user.
     * @return List of future competitions.
     */
    @Operation(summary = "Returns a list of future competitions of logged-in user.", description = "This method can distinguish if user has joined them or not.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of future competitions.",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Competition.class)))}),
            @ApiResponse(responseCode = "403", description = "Not allowed (user is not logged in).",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Currently logged in user was not found.",
                    content = @Content)
    })
    @GetMapping("/competitions/{id}/future")
    @JsonView(Competition.MainAttributes.class)
    public ResponseEntity<List<Competition>> pastCompetitions(@Parameter(description = "True if returning joined future competitions, false otherwise.") @RequestParam(required = false) String joined,
                                                              @Parameter(description = "ID of user.") @PathVariable String id) {
        User currentUser = userService.getUserOrNull(id);
        return (currentUser != null) ? ResponseEntity.ok(competitionService.getFutureFights(currentUser, (joined != null && !(joined.equals("false"))))) : ResponseEntity.notFound().build();
    }

    /**
     * Saves new information for some values relating to currently logged in user (list of all of them in {@link AdminUserEditionDTO}).
     *
     * @param adminUserEditionDTO Admin Data Transfer Object.
     * @param id Id of the user.
     * @param request HTTP Servlet Request.
     * @return Saved user.
     */
    @Operation(summary = "Saves new information for some values relating to currently logged in user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile information with changed values.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "403", description = "Not allowed (user is not logged in).",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Currently logged in user was not found.",
                    content = @Content)
    })
    @PutMapping({"/competitors/{id}", "/referees/{id}"})
    public ResponseEntity<User> editingUser(@Valid @Parameter(description = "Admin Data Transfer Object.") @RequestBody AdminUserEditionDTO adminUserEditionDTO,
                                            @Parameter(description = "ID of the user.") @PathVariable String id,
                                            @Parameter(description = "HTTP Servlet Request.") HttpServletRequest request) {
        User user = userService.findByNickname(request.getUserPrincipal().getName());
        boolean isAdmin = user.isRole(Role.A);
        if (!isAdmin && !user.getLicenseId().equals(id))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        // A control boolean helps to check if a user is allowed to modify some values or not.
        // Edition is only allowed if user is admin or currently logged in user
        try {
            user = userService.save((isAdmin) ? adminUserEditionDTO.getName() : null,
                    (isAdmin) ? adminUserEditionDTO.getSurname() : null,
                    (isAdmin) ? adminUserEditionDTO.getGender() : null,
                    adminUserEditionDTO.getPhone(),
                    adminUserEditionDTO.getEmail(),
                    adminUserEditionDTO.getBirthDate(),
                    adminUserEditionDTO.getDni(),
                    id,
                    (isAdmin && !user.getLicenseId().equals(adminUserEditionDTO.getLicenseId())) ? adminUserEditionDTO.getNickname() : null,
                    null,
                    (isAdmin) ? adminUserEditionDTO.getSecurityQuestion() : null,
                    (isAdmin) ? adminUserEditionDTO.getSecurityAnswer() : null,
                    null,
                    adminUserEditionDTO.getBelt(),
                    null,
                    adminUserEditionDTO.getGym(),
                    adminUserEditionDTO.getWeight(),
                    adminUserEditionDTO.getRefereeRange());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return (user == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    /**
     * Returns a ordered list with some fields of each user for building ranking table.
     *
     * @return Ranking list.
     */
    @Operation(summary = "Returns a ordered list with some fields of each user for building ranking table.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of ranking.",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RankingDTO.class)))}),
            @ApiResponse(responseCode = "403", description = "Not allowed (user is not logged in).",
                    content = @Content)
    })
    @GetMapping("/ranking")
    public ResponseEntity<List<RankingDTO>> getRanking() {
        return ResponseEntity.ok(userService.getRanking());
    }
}
