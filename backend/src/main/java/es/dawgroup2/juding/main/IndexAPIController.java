package es.dawgroup2.juding.main;

import es.dawgroup2.juding.auxTypes.belts.BeltService;
import es.dawgroup2.juding.auxTypes.gender.GenderService;
import es.dawgroup2.juding.auxTypes.refereeRange.RefereeRange;
import es.dawgroup2.juding.auxTypes.refereeRange.RefereeRangeService;
import es.dawgroup2.juding.main.image.ImageService;
import es.dawgroup2.juding.posts.PostService;
import es.dawgroup2.juding.security.jwt.AuthResponse;
import es.dawgroup2.juding.security.jwt.LoginRequest;
import es.dawgroup2.juding.security.jwt.UserLoginService;
import es.dawgroup2.juding.users.User;
import es.dawgroup2.juding.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.ParseException;

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

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @CookieValue(name = "accessToken", required = false) String accessToken,
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            @RequestBody LoginRequest loginRequest) {

        return userLoginService.login(loginRequest, accessToken, refreshToken);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(
            @CookieValue(name = "refreshToken", required = false) String refreshToken) {

        return userLoginService.refresh(refreshToken);
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logOut(HttpServletRequest request, HttpServletResponse response) {

        return ResponseEntity.ok(new AuthResponse(AuthResponse.Status.SUCCESS, userLoginService.logout(request, response)));
    }

    /**
     * Saving information of new competitor.
     *
     * @param name             Name
     * @param surname          Surname
     * @param gender           Gender
     * @param phone            Phone
     * @param email            Email
     * @param birthDate        Birth date
     * @param dni              DNI
     * @param licenseId        License ID
     * @param nickname         Nickname
     * @param password         Passsword
     * @param securityQuestion Security Question
     * @param securityAnswer   Security Answer
     * @param image            Profile image
     * @param belt             Belt
     * @param gym              Gym
     * @param weight           Weight
     * @return Redirection to login if successful.
     */
    @PostMapping("/signUp/competitor")
    public ResponseEntity<User> signUpCompetitor(@RequestParam String name,
                                                 @RequestParam String surname,
                                                 @RequestParam String gender,
                                                 @RequestParam String phone,
                                                 @RequestParam String email,
                                                 @RequestParam String birthDate,
                                                 @RequestParam String dni,
                                                 @RequestParam String licenseId,
                                                 @RequestParam String nickname,
                                                 @RequestParam String password,
                                                 @RequestParam String securityQuestion,
                                                 @RequestParam String securityAnswer,
                                                 @RequestParam MultipartFile image,
                                                 @RequestParam String belt,
                                                 @RequestParam String gym,
                                                 @RequestParam int weight) {
        User user = userService.save(name, surname, gender, phone, email, birthDate, dni, licenseId, nickname, password, securityQuestion, securityAnswer, image, belt, gym, weight, null);
        return ResponseEntity.created(fromCurrentRequest().path("/api/me/myProfile").buildAndExpand(user.getLicenseId()).toUri()).body(user);
    }

    /**
     * Saving information of new referee (as a application for being officialy admitted).
     *
     * @param name             Name
     * @param surname          Surname
     * @param gender           Gender
     * @param phone            Phone
     * @param email            Email
     * @param birthDate        Birth date
     * @param dni              DNI
     * @param licenseId        License ID
     * @param nickname         Nickname
     * @param password         Password
     * @param securityQuestion Security question
     * @param securityAnswer   Security answer
     * @param image            Profile image
     * @param belt             Belt
     * @return Redirection to login if successful (if application was properly submitted).
     */
    @PostMapping("/signUp/referee")
    public ResponseEntity<User> signUpReferee(@RequestParam String name,
                                              @RequestParam String surname,
                                              @RequestParam String gender,
                                              @RequestParam String phone,
                                              @RequestParam String email,
                                              @RequestParam String birthDate,
                                              @RequestParam String dni,
                                              @RequestParam String licenseId,
                                              @RequestParam String nickname,
                                              @RequestParam String password,
                                              @RequestParam String securityQuestion,
                                              @RequestParam String securityAnswer,
                                              MultipartFile image,
                                              @RequestParam String belt) {
        User user = userService.save(name, surname, gender, phone, email, birthDate, dni, licenseId, nickname, password, securityQuestion, securityAnswer, image, belt, null, null, RefereeRange.S.name());
        return ResponseEntity.created(fromCurrentRequest().path("/api/me/myProfile").buildAndExpand(user.getLicenseId()).toUri()).body(user);
    }
}
