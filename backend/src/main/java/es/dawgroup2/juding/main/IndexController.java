package es.dawgroup2.juding.main;

import es.dawgroup2.juding.auxTypes.belts.BeltService;
import es.dawgroup2.juding.auxTypes.gender.GenderService;
import es.dawgroup2.juding.auxTypes.refereeRange.RefereeRange;
import es.dawgroup2.juding.auxTypes.refereeRange.RefereeRangeService;
import es.dawgroup2.juding.auxTypes.roles.Role;
import es.dawgroup2.juding.main.image.ImageService;
import es.dawgroup2.juding.posts.Post;
import es.dawgroup2.juding.posts.PostService;
import es.dawgroup2.juding.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * This method inflates the index page with the first 3 news in the posts section of index.
     *
     * @param model Model.
     * @return Index page with dynamic news.
     */
    @GetMapping("/")
    public String index(Model model) {
        Page<Post> postFirstPage = postService.getPostsInPages(0, 3);
        model.addAttribute("postPage", postFirstPage.getContent())
                .addAttribute("empty", postFirstPage.getTotalElements() == 0)
                .addAttribute("morePages", postFirstPage.hasNext())
                .addAttribute("totalPages", postFirstPage.getTotalPages());
        return "/index";
    }

    /**
     * Dynamic view of Terms and Conditions of Use page.
     *
     * @return Terms and Conditions of Use page.
     */
    @GetMapping("/termsAndConditionsOfUse")
    public String termsAndConditionsOfUse() {
        return "/termsAndConditionsOfUse";
    }

    /**
     * Dynamic view of Cookie Policy page.
     *
     * @return Cookie Policy view.
     */
    @GetMapping("/cookiePolicy")
    public String cookiePolicy() {
        return "/cookiePolicy";
    }

    /**
     * Login page (ready for Spring Security use).
     *
     * @return Login page.
     */
    @RequestMapping("/login")
    public String login() {
        return "/login";
    }

    /**
     * Page for errors while logging in.
     *
     * @param error Error
     * @param model Model
     * @return Page for login error.
     */
    @GetMapping("/login/{error}")
    public String loginError(@PathVariable(required = false) String error, Model model) {
        model.addAttribute("error", (error != null));
        return "/login";
    }

    /**
     * Dynamic view of sign up page (different by role).
     *
     * @param role  Role (via path)
     * @param model Model
     * @return Dynamic view of sign up page.
     */
    @GetMapping("/signUp/{role}")
    public String signUp(@PathVariable String role, Model model) {
        model.addAttribute("genderSelection", genderService.getRadioField(null))
                .addAttribute("beltSelector", beltService.getSelectField(null, false));
        if (role.equals("competitor"))
            model.addAttribute("isCompetitor", true).addAttribute("action", "/signUp/competitor");
        else if (role.equals("referee"))
            model.addAttribute("isCompetitor", false).addAttribute("action", "/signUp/referee");
        else return "redirect:/error/404";
        return "/signUp";
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
    public String signUpCompetitor(@RequestParam String name,
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
                                   @RequestParam Integer weight) {
        if (userService.save(name, surname, gender, phone, email, birthDate, dni, licenseId, nickname, password, securityQuestion, securityAnswer, image, belt, Role.C, gym, weight, null) != null)
            return "redirect:/login";
        else
            return "redirect:/error/500";
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
    public String signUpReferee(@RequestParam String name,
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
        if (userService.save(name, surname, gender, phone, email, birthDate, dni, licenseId, nickname, password, securityQuestion, securityAnswer, image, belt, Role.R, null, null, RefereeRange.S.name()) != null)
            return "redirect:/login";
        else
            return "redirect:/error/500";
    }
}
