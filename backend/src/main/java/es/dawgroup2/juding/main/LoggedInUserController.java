package es.dawgroup2.juding.main;

import es.dawgroup2.juding.belts.BeltService;
import es.dawgroup2.juding.main.image.ImageService;
import es.dawgroup2.juding.users.User;
import es.dawgroup2.juding.users.UserService;
import es.dawgroup2.juding.users.refereeRange.RefereeRange;
import es.dawgroup2.juding.users.refereeRange.RefereeRangeService;
import es.dawgroup2.juding.users.roles.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

@Controller
public class LoggedInUserController {
    @Autowired
    UserService userService;

    @Autowired
    BeltService beltService;

    @Autowired
    DateService dateService;

    @Autowired
    ImageService imageService;

    @Autowired
    RefereeRangeService refereeRangeService;

    /**
     * Dynamic view of homepage of logged in users.
     *
     * @param model   Model.
     * @param request HTTP Servlet Request.
     * @return Dynamic view of homepage of logged in users.
     */
    @GetMapping("/myHome")
    public String myHome(Model model, HttpServletRequest request) {
        User currentUser = userService.findByNickname(request.getUserPrincipal().getName());
        if (currentUser != null) {
            model.addAttribute("user", currentUser)
                    .addAttribute("isCompetitor", currentUser.isRole(Role.C));
        }
        return "myHome";
    }

    /**
     * Dynamic view of profile screen, with some data about logged user.
     *
     * @param model Model.
     * @return Dynamic view of profile screen.
     */
    @GetMapping("/myProfile")
    public String myProfile(Model model, HttpServletRequest request) {
        User currentUser = userService.findByNickname(request.getUserPrincipal().getName());
        if (currentUser == null) {
            return "/error/403";
        } else {
            model.addAttribute("user", currentUser)
                    .addAttribute("isCompetitor", currentUser.isRole(Role.C))
                    .addAttribute("isMale", currentUser.isMale())
                    .addAttribute("beltValue", currentUser.getBelt().getLongName());
        }
        return "/myProfile/index";
    }

    /**
     * Dynamic view of edit profile screen.
     *
     * @param model Model.
     * @return Dynamic view of edit profile screen.
     */
    @GetMapping("/myProfile/edit")
    public String editProfile(Model model, HttpServletRequest request) {
        User currentUser = userService.findByNickname(request.getUserPrincipal().getName());
        if (currentUser == null) {
            return "/error/403";
        } else {
            model.addAttribute("user", currentUser)
                    .addAttribute("isCompetitor", currentUser.isRole(Role.C))
                    .addAttribute("beltSelector", beltService.getSelectField(currentUser.getBelt()));
            if (currentUser.isRole(Role.R)) {
                model.addAttribute("refereeRangeSelector", refereeRangeService.generateActiveRangesSelect(currentUser.getRefereeRange(), true));
            }
        }
        return "/myProfile/edit";
    }

    /**
     * Method for saving edited values of user when {@link #editProfile(Model) editProfile} form is filled and sent.
     *
     * @param model        Model.
     * @param licenseId    License ID (PK).
     * @param beltSelector Belt.
     * @param gym          Gym.
     * @param weight       Weight
     * @param refereeRange Referee range (in case it's a referee)
     * @param nick         Nickname.
     * @param phone        Phone.
     * @param email        Email.
     * @param image        Profile image.
     * @return Profile page if successful.
     * @throws IOException Input-output exception.
     */
    @PostMapping("/myProfile/edit")
    public String editingUser(@RequestParam String licenseId,
                              @RequestParam String beltSelector,
                              @RequestParam(required = false) String gym,
                              @RequestParam(required = false) Integer weight,
                              @RequestParam(required = false) RefereeRange refereeRange,
                              @RequestParam String nick,
                              @RequestParam int phone,
                              @RequestParam String email,
                              MultipartFile image) throws IOException {
        User user = userService.getUserOrNull(licenseId);
        // Common fields
        user.setBelt(beltService.findBeltById(beltSelector)).setNickname(nick).setPhone(phone).setEmail(email);
        // Role-based fields
        if (user.isRole(Role.C)) {
            user.setGym(gym).setWeight(weight);
        } else if (user.isRole(Role.R)) {
            user.setRefereeRange(refereeRange);
        }
        // Changing image
        if (!image.isEmpty()) {
            user.setImageFile(imageService.uploadProfileImage(image));
        }
        userService.save(user);
        return "redirect:/myProfile";
    }
}
