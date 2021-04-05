package es.dawgroup2.juding.main;

import com.fasterxml.jackson.annotation.JsonView;
import es.dawgroup2.juding.auxTypes.belts.BeltService;
import es.dawgroup2.juding.auxTypes.refereeRange.RefereeRangeService;
import es.dawgroup2.juding.competitions.Competition;
import es.dawgroup2.juding.competitions.CompetitionService;
import es.dawgroup2.juding.main.image.ImageService;
import es.dawgroup2.juding.users.User;
import es.dawgroup2.juding.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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

    @GetMapping("/me/myProfile")
    public ResponseEntity<User> me(HttpServletRequest request) {
        User currentUser = userService.findByNickname(request.getUserPrincipal().getName());
        if (currentUser != null)
            return ResponseEntity.ok(currentUser);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/me/pastCompetitions")
    @JsonView(Competition.MainAttributes.class)
    public ResponseEntity<List<Competition>> pastCompetitions(HttpServletRequest request) {
        User currentUser = userService.findByNickname(request.getUserPrincipal().getName());
        if (currentUser != null)
            return ResponseEntity.ok(competitionService.getPastFights(currentUser));
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/me/currentCompetitions")
    @JsonView(Competition.MainAttributes.class)
    public ResponseEntity<List<Competition>> currentCompetitions(HttpServletRequest request) {
        User currentUser = userService.findByNickname(request.getUserPrincipal().getName());
        if (currentUser != null)
            return ResponseEntity.ok(competitionService.getCurrentCompetitions(currentUser));
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/me/futureCompetitions")
    @JsonView(Competition.MainAttributes.class)
    public ResponseEntity<List<Competition>> futureCompetitions(@RequestParam(required = false) String joined, HttpServletRequest request) {
        User currentUser = userService.findByNickname(request.getUserPrincipal().getName());
        if (currentUser != null)
            return ResponseEntity.ok(competitionService.getFutureFights(currentUser, (joined != null && !(joined.equals("false")))));
        else
            return ResponseEntity.notFound().build();
    }

    @PutMapping("/me/myProfile")
    public ResponseEntity<User> editingUser(@RequestParam String licenseId,
                                            @RequestParam String belt,
                                            @RequestParam(required = false) String gym,
                                            @RequestParam(required = false) Integer weight,
                                            @RequestParam(required = false) String refereeRange,
                                            @RequestParam String nickname,
                                            @RequestParam String phone,
                                            @RequestParam String email,
                                            MultipartFile image,
                                            HttpServletRequest request) throws IOException {
        User user = null;
        if (userService.findByNickname(request.getUserPrincipal().getName()).getLicenseId().equals(licenseId))
            user = userService.save(null, null, null, phone, email, null, null, licenseId, nickname, null, null, null, image, belt, gym, weight, refereeRange);
        if (user == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(user);
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<?>> getRanking() {
        return ResponseEntity.ok(userService.getRanking());
    }
}
