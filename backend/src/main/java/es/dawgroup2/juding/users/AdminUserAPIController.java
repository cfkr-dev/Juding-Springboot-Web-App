package es.dawgroup2.juding.users;

import es.dawgroup2.juding.auxTypes.belts.BeltService;
import es.dawgroup2.juding.auxTypes.gender.GenderService;
import es.dawgroup2.juding.auxTypes.refereeRange.RefereeRangeService;
import es.dawgroup2.juding.main.DateService;
import es.dawgroup2.juding.main.HeaderInflater;
import es.dawgroup2.juding.main.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<Page<User>> competitorList(@RequestParam(required = false) Integer page) {
        int defPage = (page == null) ? 1 : page - 1;
        if (defPage < 0) return ResponseEntity.badRequest().build();
        Page<User> requiredPage = userService.getCompetitorsInPages(defPage);
        if (requiredPage.hasContent())
            return ResponseEntity.ok(requiredPage);
        else
            return ResponseEntity.badRequest().build();
    }

    @GetMapping("/referees/applications")
    public List<User> applicantList(){ return userService.getRefereeApplications(); }

    @GetMapping("/referees")
    public ResponseEntity<Page<User>> refereeList(@RequestParam(required = false) Integer page) {
        int defPage = (page == null) ? 1 : page - 1;
        if (defPage < 0) return ResponseEntity.badRequest().build();
        Page<User> requiredPage = userService.getActiveRefereesInPages(defPage);
        if (requiredPage.hasContent())
            return ResponseEntity.ok(requiredPage);
        else
            return ResponseEntity.badRequest().build();
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
        User user = userService.save(name, surname, gender, phone, email, birthDate, dni, licenseId, nickname, null, null, null, null, belt, gym, weight, refereeRange);
        return ResponseEntity.created(fromCurrentRequest().path("/api/admin/user/{licenseId}").buildAndExpand(user.getLicenseId()).toUri()).body(user);
    }

    @PutMapping("/admitReferee/{licenseId}")
    public ResponseEntity<User> admitReferee(@PathVariable String licenseId) {
        User user = userService.admitReferee(licenseId);
        return (user == null) ? ResponseEntity.badRequest().build() : ResponseEntity.ok(user);
    }

    @DeleteMapping("/{licenseId}")
    public ResponseEntity<User> deleteUser(@PathVariable String licenseId) {
        User user = userService.getUserOrNull(licenseId);
        userService.delete(user);
        return (user == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }
}
