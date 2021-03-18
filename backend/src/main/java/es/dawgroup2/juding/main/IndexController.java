package es.dawgroup2.juding.main;

import es.dawgroup2.juding.belts.BeltService;
import es.dawgroup2.juding.main.image.ImageService;
import es.dawgroup2.juding.posts.Post;
import es.dawgroup2.juding.posts.PostService;
import es.dawgroup2.juding.users.User;
import es.dawgroup2.juding.users.UserService;
import es.dawgroup2.juding.users.gender.GenderService;
import es.dawgroup2.juding.users.refereeRange.RefereeRange;
import es.dawgroup2.juding.users.refereeRange.RefereeRangeService;
import es.dawgroup2.juding.users.roles.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

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

    /**
     * This method inflates the post box list in index main page.
     * @param model Post data model.
     * @return index main page view.
     */
    @GetMapping("/")
    public String index(Model model) {
        List<Post> postList = postService.findAll();
        model.addAttribute("postList", postList);
        return "/index";
    }

    @RequestMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("login/{error}")
    public String loginError(@PathVariable(required = false) String error, Model model) {
        model.addAttribute("error",(error!=null));
        return "/login";
    }

    @GetMapping("/signUp/{role}")
    public String signUp(@PathVariable String role, Model model) {
        model.addAttribute("genderSelection", genderService.getRadioField(null))
                .addAttribute("beltSelector", beltService.getSelectField(null));
        if (role.equals("competitor"))
            model.addAttribute("isCompetitor", true).addAttribute("action", "/signUp/competitor");
        else if (role.equals("referee"))
            model.addAttribute("isCompetitor", false).addAttribute("action", "/signUp/referee");
        else return "/error/404";
        return "/signUp";
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
                                   @RequestParam String password,
                                   @RequestParam String securityQuestion,
                                   @RequestParam String securityAnswer,
                                   MultipartFile image,
                                   @RequestParam String belt,
                                   @RequestParam String gym,
                                   @RequestParam int weight) {
        User newUser = new User();
        try {
            newUser.setLicenseId(licenseId).setName(name).setSurname(surname).setEmail(email).setPhone(phone)
                    .setGender(genderService.findGenderById(gender)).setBirthDate(dateService.stringToDate(birthDate))
                    .setDni(dni).setGym(gym).setWeight(weight).setBelt(beltService.findBeltById(belt))
                    .setImageFile(imageService.uploadProfileImage(image)).setNickname(nickname)
                    .setPassword(password).setSecurityQuestion(securityQuestion).setSecurityAnswer(securityAnswer)
                    .setRoles(List.of(Role.C));
        } catch (Exception e) {
            return "redirect:/error/500";
        }
        userService.save(newUser);
        return "redirect:/login";
    }

    @PostMapping("/signUp/referee")
    public String signUpReferee(@RequestParam String name,
                                   @RequestParam String surname,
                                   @RequestParam String gender,
                                   @RequestParam int phone,
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
        User newUser = new User();
        try {
            newUser.setLicenseId(licenseId).setName(name).setSurname(surname).setEmail(email).setPhone(phone)
                    .setGender(genderService.findGenderById(gender)).setBirthDate(dateService.stringToDate(birthDate))
                    .setDni(dni).setBelt(beltService.findBeltById(belt)).setRefereeRange(RefereeRange.S)
                    .setImageFile(imageService.uploadProfileImage(image)).setNickname(nickname)
                    .setPassword(password).setSecurityQuestion(securityQuestion).setSecurityAnswer(securityAnswer)
                    .setRoles(List.of(Role.R));
        } catch (Exception e) {
            return "redirect:/error/500";
        }
        userService.save(newUser);
        return "redirect:/login";
    }
}
