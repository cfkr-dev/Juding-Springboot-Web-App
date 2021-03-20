package es.dawgroup2.juding.main;

import es.dawgroup2.juding.auxTypes.belts.BeltService;
import es.dawgroup2.juding.main.image.ImageService;
import es.dawgroup2.juding.posts.Post;
import es.dawgroup2.juding.posts.PostService;
import es.dawgroup2.juding.users.User;
import es.dawgroup2.juding.users.UserService;
import es.dawgroup2.juding.auxTypes.gender.GenderService;
import es.dawgroup2.juding.auxTypes.refereeRange.RefereeRange;
import es.dawgroup2.juding.auxTypes.refereeRange.RefereeRangeService;
import es.dawgroup2.juding.auxTypes.roles.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

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

    @GetMapping("/termsAndConditionsOfUse")
    public String termsAndConditionsOfUse(){
        return "/termsAndConditionsOfUse";
    }

    @GetMapping("/cookiePolicy")
    public String cookiePolicy(){
        return "/cookiePolicy";
    }

    @RequestMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/login/{error}")
    public String loginError(@PathVariable(required = false) String error, Model model) {
        model.addAttribute("error",(error!=null));
        return "/login";
    }

    @GetMapping("/signUp/{role}")
    public String signUp(@PathVariable String role, Model model) {
        model.addAttribute("genderSelection", genderService.getRadioField(null))
                .addAttribute("beltSelector", beltService.getSelectField(null, false));
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
        User newUser = new User();
        try {
            newUser.setLicenseId(licenseId).setName(name).setSurname(surname).setEmail(email).setPhone((phone.equals("")) ? null : Integer.parseInt(phone))
                    .setGender(genderService.findGenderById(gender)).setBirthDate(dateService.stringToDate(birthDate))
                    .setDni(dni).setGym(gym).setWeight(weight).setBelt(beltService.findBeltById(belt))
                    .setImageFile(imageService.uploadProfileImage(image)).setMimeProfileImage(image.getContentType())
                    .setNickname(nickname).setPassword(passwordEncoder.encode(password)).setSecurityQuestion(securityQuestion)
                    .setSecurityAnswer(securityAnswer).setRoles(Set.of(Role.C));
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
        User newUser = new User();
        try {
            newUser.setLicenseId(licenseId).setName(name).setSurname(surname).setEmail(email).setPhone((phone.equals("")) ? null : Integer.parseInt(phone))
                    .setGender(genderService.findGenderById(gender)).setBirthDate(dateService.stringToDate(birthDate))
                    .setDni(dni).setBelt(beltService.findBeltById(belt)).setRefereeRange(RefereeRange.S)
                    .setImageFile(imageService.uploadProfileImage(image)).setMimeProfileImage(image.getContentType())
                    .setNickname(nickname).setPassword(passwordEncoder.encode(password)).setSecurityQuestion(securityQuestion)
                    .setSecurityAnswer(securityAnswer).setRoles(Set.of(Role.C)).setRoles(Set.of(Role.R));
        } catch (Exception e) {
            return "redirect:/error/500";
        }
        userService.save(newUser);
        return "redirect:/login";
    }
}
