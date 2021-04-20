package es.dawgroup2.juding.users.rest;

import es.dawgroup2.juding.auxTypes.belts.BeltService;
import es.dawgroup2.juding.auxTypes.gender.GenderService;
import es.dawgroup2.juding.auxTypes.refereeRange.RefereeRangeService;
import es.dawgroup2.juding.main.DateService;
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
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AdminUserAPIController {
    @Autowired
    UserService userService;

    @Autowired
    BeltService beltService;

    @Autowired
    GenderService genderService;

    @Autowired
    RefereeRangeService refereeRangeService;

    @Autowired
    DateService dateService;

    @Autowired
    ImageService imageService;


    /**
     * Returns a list with all competitors registered in the application (paginated).
     *
     * @param page Requested page number.
     * @return Page with competitors.
     */
    @Operation(summary = "Returns a list with all competitors registered in the application (paginated).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Page with competitors.",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = User.class)))}),
            @ApiResponse(responseCode = "400", description = "Requested page does not exist.",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Not allowed (there is not logged in user or it is not an administrator).",
                    content = @Content)
    })
    @GetMapping("/competitors")
    public ResponseEntity<Page<User>> competitorList(@Parameter(description = "Requested page number.") @RequestParam(required = false) Integer page) {
        int defPage = (page == null) ? 1 : page;
        if (defPage < 0) return ResponseEntity.badRequest().build();
        Page<User> requiredPage = userService.getCompetitorsInPages(defPage);
        if (requiredPage.hasContent())
            return ResponseEntity.ok(requiredPage);
        else
            return ResponseEntity.badRequest().build();
    }

    /**
     * Returns a list with all registered referee applications.
     *
     * @return List of referee applications.
     */
    @Operation(summary = "Returns a list with all registered referee applications.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of referee applications.",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = User.class)))}),
            @ApiResponse(responseCode = "403", description = "Not allowed (there is not logged in user or it is not an administrator).",
                    content = @Content)
    })
    @GetMapping("/referees/applications")
    public List<User> applicantList() {
        return userService.getRefereeApplications();
    }

    /**
     * Returns a list with all referees registered in the application (paginated).
     *
     * @param page Requested page number.
     * @return Page with referees.
     */
    @Operation(summary = "Returns a list with all referees registered in the application (paginated).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Page with referees.",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = User.class)))}),
            @ApiResponse(responseCode = "400", description = "Requested page does not exist.",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Not allowed (there is not logged in user or it is not an administrator).",
                    content = @Content)
    })
    @GetMapping("/referees")
    public ResponseEntity<Page<User>> refereeList(@Parameter(description = "Requested page number.") @RequestParam(required = false) Integer page) {
        int defPage = (page == null) ? 1 : page;
        if (defPage < 0) return ResponseEntity.badRequest().build();
        Page<User> requiredPage = userService.getActiveRefereesInPages(defPage);
        return (requiredPage.hasContent()) ? ResponseEntity.ok(requiredPage) : ResponseEntity.badRequest().build();
    }

    /**
     * Saves new information associated with a user and returns the user with the new information modified.
     *
     * @param adminUserEditionDTO Admin user edition Data Transfer Object.
     * @return User object with new information saved.
     */
    @Operation(summary = "Saves new information associated with a user and returns the user with the new information modified.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User object with new information saved.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "403", description = "Not allowed (there is not logged in user or it is not an administrator).",
                    content = @Content)
    })
    @PutMapping(value = {"/competitors/{id}", "/referees/{id}"})
    public ResponseEntity<User> savingUser(@Valid @Parameter(description = "Admin user edition Data Transfer Object.") @RequestBody AdminUserEditionDTO adminUserEditionDTO,
                                           @Parameter(description = "ID of user.") @PathVariable String id) {
        if (userService.matchingLicenseAndNickname(id, adminUserEditionDTO.getNickname())) {
            return ResponseEntity.badRequest().build();
        }
        User user = userService.save(adminUserEditionDTO.getName(),
                adminUserEditionDTO.getSurname(),
                adminUserEditionDTO.getGender(),
                adminUserEditionDTO.getPhone(),
                adminUserEditionDTO.getEmail(),
                adminUserEditionDTO.getBirthDate(),
                adminUserEditionDTO.getDni(),
                id,
                adminUserEditionDTO.getNickname(),
                null,
                null,
                null,
                null,
                adminUserEditionDTO.getBelt(),
                null, adminUserEditionDTO.getGym(),
                adminUserEditionDTO.getWeight(),
                adminUserEditionDTO.getRefereeRange());
        return ResponseEntity.ok(user);
    }

    /**
     * Process a referee's application and saves it as an official referee.
     *
     * @param id License ID of admitted referee.
     * @return New referee's complete information.
     */
    @Operation(summary = "Process a referee's application and saves it as an official referee.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New referee's complete information.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Requested license ID is not registered in the application or referee has been admitted before",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Not allowed (there is not logged in user or it is not an administrator).",
                    content = @Content)
    })
    @PutMapping("/referees/applications/{id}")
    public ResponseEntity<User> admitReferee(@Parameter(description = "License ID of admitted referee.") @PathVariable String id) {
        User user = userService.admitReferee(id);
        return (user == null) ? ResponseEntity.badRequest().build() : ResponseEntity.ok(user);
    }

    /**
     * Deletes a user (only successful if user has not taken part of any competition previously or if it has joined no one).
     *
     * @param id License ID of user.
     * @return Deleted user information.
     */
    @Operation(summary = "Deletes a user.", description = "Warning: this method is only successful if user has not taken part of any competition previously or if it has joined no one.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "403", description = "Not allowed (there is not logged in user or it is not an administrator).",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not able to delete user (user did not exist or user has taken part of any past or future competition)",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@Parameter(description = "License ID of user.") @PathVariable String id) {
        User user = userService.getUserOrNull(id);
        userService.delete(user);
        return (user == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }
}
