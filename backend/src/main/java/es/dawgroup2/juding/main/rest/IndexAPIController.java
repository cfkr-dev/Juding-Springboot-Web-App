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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @PostMapping("/signUp/competitor")
    public ResponseEntity<User> signUpCompetitor(@RequestBody CompetitorDTO competitorDTO) {
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
        return ResponseEntity.created(fromCurrentRequest().path("/api/me/myProfile").buildAndExpand(user.getLicenseId()).toUri()).body(user);
    }

    @PostMapping("/signUp/referee")
    public ResponseEntity<User> signUpReferee(@RequestBody RefereeDTO refereeDTO) {
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
                "S");
        return ResponseEntity.created(fromCurrentRequest().path("/api/me/myProfile").buildAndExpand(user.getLicenseId()).toUri()).body(user);
    }
}
