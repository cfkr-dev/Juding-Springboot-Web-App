package es.dawgroup2.juding.main;

import es.dawgroup2.juding.belts.BeltService;
import es.dawgroup2.juding.users.User;
import es.dawgroup2.juding.users.UserService;
import es.dawgroup2.juding.users.gender.GenderService;
import es.dawgroup2.juding.users.refereeRange.RefereeRangeService;
import es.dawgroup2.juding.users.roles.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@Controller
public class IndexController {

    @Autowired
    UserService userService;

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

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signUp/{role}")
    public String signUp(@PathVariable String role, Model model) {
        model.addAttribute("genderSelection", genderService.getRadioField(null))
                .addAttribute("beltSelector", beltService.getSelectField(null));
        return "/signUp/" + role;
    }

    @PostMapping("/signUp/competitor")
    public String signUpCompetitor(@RequestParam String name,
                                   @RequestParam String surname,
                                   @RequestParam String gender,
                                   @RequestParam int phone,
                                   @RequestParam String email,
                                   @RequestParam String birthDate,
                                   @RequestParam String dni,
                                   @RequestParam String licenseId,
                                   @RequestParam String nickname,
                                   @RequestParam String securityQuestion,
                                   @RequestParam String securityAnswer,
                                   @RequestParam String password,
                                   MultipartFile image,
                                   @RequestParam String gym,
                                   @RequestParam int weight,
                                   @RequestParam String belt) {
        User newUser = new User();
        try {
            newUser.setLicenseId(licenseId).setName(name).setSurname(surname).setEmail(email).setPhone(phone)
                    .setGender(genderService.findGenderById(gender)).setBirthDate(dateService.stringToDate(birthDate))
                    .setDni(dni).setGym(gym).setWeight(weight).setBelt(beltService.findBeltById(belt))
                    .setProfileImage(imageService.uploadProfileImage(image)).setNickname(nickname)
                    .setPassword(password).setSecurityQuestion(securityQuestion).setSecurityAnswer(securityAnswer)
                    .setRoles(List.of(Role.C));
        } catch (Exception e) {
            return "redirect:/error/500";
        }
        userService.save(newUser);
        return "redirect:/login";
    }
}
