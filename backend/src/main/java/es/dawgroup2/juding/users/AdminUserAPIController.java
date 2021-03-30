package es.dawgroup2.juding.users;

import es.dawgroup2.juding.auxTypes.belts.BeltService;
import es.dawgroup2.juding.auxTypes.gender.GenderService;
import es.dawgroup2.juding.auxTypes.refereeRange.RefereeRangeService;
import es.dawgroup2.juding.main.DateService;
import es.dawgroup2.juding.main.HeaderInflater;
import es.dawgroup2.juding.main.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.text.ParseException;
import java.util.List;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/admin/user")
public class AdminUserAPIController {
    @Autowired
    HeaderInflater headerInflater;

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


    @GetMapping("/competitors")
    public List<User> competitorList() {
        return userService.getCompetitors();
    }

    @GetMapping("/referees/applications")
    public List<User> applicantList(){ return userService.getRefereeApplications(); }

    @GetMapping("/referees")
    public List<User> refereeList() {
        return userService.getActiveReferees();
    }

    @GetMapping("/{licenseId}")
    public User getUser(@PathVariable String licenseId){return userService.getUserOrNull(licenseId); }

    @PostMapping("/")
    public ResponseEntity<User> savingUser(@RequestParam String name,
                             @RequestParam String surname,
                             @RequestParam String gender,
                             @RequestParam String phone,
                             @RequestParam String email,
                             @RequestParam String birthDate,
                             @RequestParam String dni,
                             @RequestParam String licenseId,
                             @RequestParam String nickname,
                             @RequestParam String belt,
                             @RequestParam(required = false) String gym,
                             @RequestParam(required = false) Integer weight,
                             @RequestParam(required = false) String refereeRange
    ) throws ParseException {
        User user = userService.save(name, surname, gender, phone, email, birthDate, dni, licenseId, nickname, belt, gym, weight, refereeRange);
        return ResponseEntity.created(fromCurrentRequest().path("/api/admin/user/{licenseId}").buildAndExpand(user.getLicenseId()).toUri()).body(user);
    }

    @PutMapping("/admitReferee/{licenseId}")
    public ResponseEntity<User> admitReferee(@PathVariable String licenseId) {
        User user = userService.admitReferee(licenseId);
        if (user != null)
            return ResponseEntity.ok(user);
        else
            return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{licenseId}")
    public ResponseEntity<User> deleteUser(@PathVariable String licenseId) {
        User user = userService.delete(licenseId);
        if (user != null)
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.notFound().build();
    }
}
