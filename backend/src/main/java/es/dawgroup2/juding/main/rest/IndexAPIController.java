package es.dawgroup2.juding.main.rest;

import es.dawgroup2.juding.auxTypes.belts.BeltService;
import es.dawgroup2.juding.auxTypes.gender.GenderService;
import es.dawgroup2.juding.auxTypes.refereeRange.RefereeRange;
import es.dawgroup2.juding.auxTypes.refereeRange.RefereeRangeService;
import es.dawgroup2.juding.auxTypes.roles.Role;
import es.dawgroup2.juding.main.DateService;
import es.dawgroup2.juding.main.image.ImageService;
import es.dawgroup2.juding.posts.PostService;
import es.dawgroup2.juding.security.jwt.AuthResponse;
import es.dawgroup2.juding.security.jwt.LoginRequest;
import es.dawgroup2.juding.security.jwt.UserLoginService;
import es.dawgroup2.juding.users.User;
import es.dawgroup2.juding.users.UserService;
import es.dawgroup2.juding.users.rest.CompetitorDTO;
import es.dawgroup2.juding.users.rest.RefereeDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api")
public class IndexAPIController {
    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Autowired
    GenderService genderService;

    @Autowired
    BeltService beltService;

    @Autowired
    RefereeRangeService refereeRangeService;

    @Autowired
    ImageService imageService;

    @Autowired
    DateService dateService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserLoginService userLoginService;

    /**
     * Login into application (via JWT).
     *
     * @param accessToken  Access token.
     * @param refreshToken Refresh token.
     * @param loginRequest Login Request.
     * @return Auth response.
     */
    @Operation(summary = "Login into application (via JWT).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login into application (via JWT).",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class))})
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Parameter(description = "Access token.") @CookieValue(name = "accessToken", required = false) String accessToken,
            @Parameter(description = "Refresh token.") @CookieValue(name = "refreshToken", required = false) String refreshToken,
            @Parameter(description = "Login request.") @RequestBody LoginRequest loginRequest) {

        return userLoginService.login(loginRequest, accessToken, refreshToken);
    }

    /**
     * Refreshing of token.
     *
     * @param refreshToken Refresh token.
     * @return Refreshed token.
     */
    @Operation(summary = "Refreshing of token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Refreshing of token.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class))})
    })
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(
            @Parameter(description = "Refresh token.") @CookieValue(name = "refreshToken", required = false) String refreshToken) {
        return userLoginService.refresh(refreshToken);
    }

    /**
     * Logout from application and deletion of cookies.
     *
     * @param request  HTTP Servlet Request.
     * @param response HTTP Servlet Response.
     * @return Confirmation of logout.
     */
    @Operation(summary = "Logout from application and deletion of cookies.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Confirmation of logout.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class))})
    })
    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logOut(@Parameter(description = "HTTP Servlet Request.") HttpServletRequest request,
                                               @Parameter(description = "HTTP Servlet Response.") HttpServletResponse response) {
        return ResponseEntity.ok(new AuthResponse(AuthResponse.Status.SUCCESS, userLoginService.logout(request, response)));
    }

    /**
     * Sign up of a competitor.
     *
     * @param competitorDTO Competitor Data Transfer Object.
     * @return User created and saved.
     */
    @Operation(summary = "Sign up of a competitor.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful sign up.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Request could not be completed successfully.",
                    content = @Content)
    })
    @PostMapping("/competitors")
    public ResponseEntity<User> signUpCompetitor(@Valid @Parameter(description = "Competitor Data Transfer Object.") @RequestBody CompetitorDTO competitorDTO) {
        if (userService.matchingLicenseOrNickname(competitorDTO.getLicenseId(), competitorDTO.getNickname()) == 3) {
            User user = userService.save(competitorDTO.getName(),
                    competitorDTO.getSurname(),
                    competitorDTO.getGender(),
                    competitorDTO.getPhone(),
                    competitorDTO.getEmail(),
                    competitorDTO.getBirthDate(),
                    competitorDTO.getDni(),
                    competitorDTO.getLicenseId(),
                    competitorDTO.getNickname(),
                    competitorDTO.getPassword(),
                    competitorDTO.getSecurityQuestion(),
                    competitorDTO.getSecurityAnswer(),
                    null,
                    competitorDTO.getBelt(),
                    Role.C,
                    competitorDTO.getGym(),
                    competitorDTO.getWeight(),
                    null);
            if (user != null)
                return ResponseEntity.created(fromCurrentRequest().path("/{id}").buildAndExpand(user.getLicenseId()).toUri()).body(user);
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Application of a referee (sign up as an applicator).
     *
     * @param refereeDTO Referee Data Transfer Object.
     * @return User created and saved.
     */
    @Operation(summary = "Application of a referee (sign up as an applicator).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful application.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Request could not be completed successfully.",
                    content = @Content)
    })
    @PostMapping("/referees")
    public ResponseEntity<User> signUpReferee(@Valid @Parameter(description = "Referee Data Transfer Object.") @RequestBody RefereeDTO refereeDTO) {
        if (userService.matchingLicenseOrNickname(refereeDTO.getLicenseId(), refereeDTO.getNickname()) == 3) {
            User user = userService.save(refereeDTO.getName(),
                    refereeDTO.getSurname(),
                    refereeDTO.getGender(),
                    refereeDTO.getPhone(),
                    refereeDTO.getEmail(),
                    refereeDTO.getBirthDate(),
                    refereeDTO.getDni(),
                    refereeDTO.getLicenseId(),
                    refereeDTO.getNickname(),
                    refereeDTO.getPassword(),
                    refereeDTO.getSecurityQuestion(),
                    refereeDTO.getSecurityAnswer(),
                    null,
                    refereeDTO.getBelt(),
                    Role.R,
                    null,
                    null,
                    RefereeRange.S.name());
            if (user != null)
                return ResponseEntity.created(fromCurrentRequest().path("/{id}").buildAndExpand(user.getLicenseId()).toUri()).body(user);
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Recovery password method (get security question of user by its license ID).
     *
     * @param licenseId License ID of user.
     * @return User's security question.
     */
    @Operation(summary = "Recovery password method (get security question of user by its license ID).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User's security question.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request since user was not found.",
                    content = @Content)
    })
    @GetMapping("/passwordRecovery")
    public ResponseEntity<String> passwordRecovery(@Parameter(description = "License ID of user.") @RequestParam String licenseId,
                                                   @Parameter(description = "License ID of user.") @RequestParam(required = false) String secAnswer,
                                                   @Parameter(description = "Step of recovery process (1 or 2).") @RequestParam int step) {
        User user = userService.getUserOrNull(licenseId);
        if (step == 1)
            return (user == null) ? ResponseEntity.badRequest().build() : ResponseEntity.ok(user.getSecurityQuestion());
        else if (step == 2)
            return (user.getSecurityAnswer().equals(secAnswer)) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
        return ResponseEntity.badRequest().build();
    }

    /**
     * Recovery password method (including licenseId, correct security answer and new password).
     *
     * @param recoverPasswordDTO Recover password Data Transfer Object.
     * @return User saved with its new password (bad request otherwise).
     */
    @Operation(summary = "Recovery password method (including licenseId, correct security answer and new password).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User saved with its new password.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request since user was not found by its license ID or security answer was incorrect.",
                    content = @Content)
    })
    @PutMapping("/passwordRecovery")
    public ResponseEntity<User> passwordRecovery(@Valid @Parameter(description = "Recover password Data Transfer Object.") @RequestBody RecoverPasswordDTO recoverPasswordDTO) {
        User user = userService.getUserOrNull(recoverPasswordDTO.getLicenseId());
        if (user != null)
            if (user.getSecurityAnswer().equals(recoverPasswordDTO.getSecurityAnswer())) {
                user.setPassword(passwordEncoder.encode(recoverPasswordDTO.getNewPassword()));
                userService.save(user);
                return ResponseEntity.ok(user);
            }
        return ResponseEntity.badRequest().build();
    }
}
