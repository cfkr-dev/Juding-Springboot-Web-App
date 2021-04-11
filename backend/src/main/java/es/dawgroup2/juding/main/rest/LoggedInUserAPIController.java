package es.dawgroup2.juding.main.rest;

import com.fasterxml.jackson.annotation.JsonView;
import es.dawgroup2.juding.auxTypes.belts.BeltService;
import es.dawgroup2.juding.auxTypes.refereeRange.RefereeRangeService;
import es.dawgroup2.juding.competitions.Competition;
import es.dawgroup2.juding.competitions.CompetitionService;
import es.dawgroup2.juding.main.DateService;
import es.dawgroup2.juding.main.HeaderInflater;
import es.dawgroup2.juding.main.image.ImageService;
import es.dawgroup2.juding.users.User;
import es.dawgroup2.juding.users.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/api")
public class LoggedInUserAPIController {
    @Autowired
    HeaderInflater headerInflater;

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
     * Returns logged in user profile data.
     *
     * @param request HTTP Servlet Request (for catching logged in user nickname).
     * @return Logged in user date profile data.
     */
    @Operation(summary = "Returns logged in user profile data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile information.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "403", description = "Not allowed (user is not logged in).",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Currently logged in user was not found.",
                    content = @Content)
    })
    @GetMapping("/me/myProfile")
    public ResponseEntity<User> me(@Parameter(description = "HTTP Servlet Request (for catching logged in user nickname)") HttpServletRequest request) {
        User currentUser = userService.findByNickname(request.getUserPrincipal().getName());
        if (currentUser != null)
            return ResponseEntity.ok(currentUser);
        else
            return ResponseEntity.notFound().build();
    }

    /**
     * Returns a list of past competitions of currently logged-in user.
     *
     * @param request HTTP Servlet Request (for catching logged in user nickname).
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
    @GetMapping("/me/pastCompetitions")
    @JsonView(Competition.MainAttributes.class)
    public ResponseEntity<List<Competition>> pastCompetitions(@Parameter(description = "HTTP Servlet Request (for catching logged in user nickname)") HttpServletRequest request) {
        User currentUser = userService.findByNickname(request.getUserPrincipal().getName());
        if (currentUser != null)
            return ResponseEntity.ok(competitionService.getPastFights(currentUser));
        else
            return ResponseEntity.notFound().build();
    }

    /**
     * Returns a list of current competitions of logged-in user.
     *
     * @param request HTTP Servlet Request (for catching logged in user nickname).
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
    @GetMapping("/me/currentCompetitions")
    @JsonView(Competition.MainAttributes.class)
    public ResponseEntity<List<Competition>> currentCompetitions(@Parameter(description = "HTTP Servlet Request (for catching logged in user nickname)") HttpServletRequest request) {
        User currentUser = userService.findByNickname(request.getUserPrincipal().getName());
        if (currentUser != null)
            return ResponseEntity.ok(competitionService.getCurrentCompetitions(currentUser));
        else
            return ResponseEntity.notFound().build();
    }

    /**
     * Returns a list of future competitions of logged-in user, distinguishing if user has joined them or not.
     *
     * @param joined  True if returning joined future competitions, false otherwise.
     * @param request HTTP Servlet Request (for catching logged in user nickname).
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
    @GetMapping("/me/futureCompetitions")
    @JsonView(Competition.MainAttributes.class)
    public ResponseEntity<List<Competition>> futureCompetitions(@Parameter(description = "True if returning joined future competitions, false otherwise.") @RequestParam(required = false) String joined,
                                                                @Parameter(description = "HTTP Servlet Request (for catching logged in user nickname)") HttpServletRequest request) {
        User currentUser = userService.findByNickname(request.getUserPrincipal().getName());
        if (currentUser != null)
            return ResponseEntity.ok(competitionService.getFutureFights(currentUser, (joined != null && !(joined.equals("false")))));
        else
            return ResponseEntity.notFound().build();
    }

    /**
     * Saves new information for some values relating to currently logged in user (list of all of them in {@link UserProfileDTO}).
     *
     * @param userProfileDTO User profile Data Transfer Object.
     * @param request        HTTP Servlet Request (for catching logged in user nickname).
     * @return List of future competitions.
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
    @PutMapping("/me/myProfile")
    public ResponseEntity<User> editingUser(@Parameter(description = "User profile Data Transfer Object.") @RequestBody UserProfileDTO userProfileDTO,
                                            @Parameter(description = "HTTP Servlet Request (for catching logged in user nickname).") HttpServletRequest request) {
        User user = null;
        if (userService.findByNickname(request.getUserPrincipal().getName()).getLicenseId().equals(userProfileDTO.getLicenseId()))
            try {
                user = userService.save(null,
                        null,
                        null,
                        userProfileDTO.getPhone(),
                        userProfileDTO.getEmail(),
                        null,
                        null,
                        userProfileDTO.getLicenseId(),
                        userProfileDTO.getNickname(),
                        null,
                        null,
                        null,
                        null,
                        userProfileDTO.getBelt(),
                        userProfileDTO.getGym(),
                        userProfileDTO.getWeight(),
                        userProfileDTO.getRefereeRange());
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
                            array = @ArraySchema(schema = @Schema(implementation = Object.class)))}),
            @ApiResponse(responseCode = "403", description = "Not allowed (user is not logged in).",
                    content = @Content)
    })
    @GetMapping("/ranking")
    public ResponseEntity<List<?>> getRanking() {
        return ResponseEntity.ok(userService.getRanking());
    }
}
