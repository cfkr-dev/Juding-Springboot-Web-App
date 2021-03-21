package es.dawgroup2.juding.main;

import es.dawgroup2.juding.auxTypes.belts.BeltService;
import es.dawgroup2.juding.auxTypes.refereeRange.RefereeRange;
import es.dawgroup2.juding.auxTypes.refereeRange.RefereeRangeService;
import es.dawgroup2.juding.auxTypes.roles.Role;
import es.dawgroup2.juding.competitions.CompetitionService;
import es.dawgroup2.juding.main.image.ImageService;
import es.dawgroup2.juding.users.User;
import es.dawgroup2.juding.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
public class LoggedInUserController {
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
            model.addAttribute("header", headerInflater.getHeader("Inicio", request, "bootstrap/css/bootstrap.min.css", "font-awesome/css/all.css", "style", "header", "profiles", "responsiveTable", "beltAssignations"))
                    .addAttribute("myFutureComp", competitionService.getFutureFights(currentUser, true))
                    .addAttribute("myNotFutureComp", competitionService.getFutureFights(currentUser, false))
                    .addAttribute("myCurrentComp", competitionService.getCurrentCompetitions(currentUser))
                    .addAttribute("myPastComp", competitionService.getPastFights(currentUser))
                    .addAttribute("user", currentUser)
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
            model.addAttribute("header", headerInflater.getHeader("Mi perfil", request, "bootstrap/css/bootstrap.min.css", "aos/aos.css", "font-awesome/css/all.css", "style", "header", "bootstrapAccomodations", "responsiveTable", "profiles"))
                    .addAttribute("user", currentUser)
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
            return "redirect:/error/403";
        } else {
            model.addAttribute("header", headerInflater.getHeader("Editar perfil", request, "bootstrap/css/bootstrap.min.css", "aos/aos.css", "font-awesome/css/all.css", "style", "header", "bootstrapAccomodations", "responsiveTable", "profiles"))
                    .addAttribute("user", currentUser)
                    .addAttribute("isCompetitor", currentUser.isRole(Role.C))
                    .addAttribute("beltSelector", beltService.getSelectField(currentUser.getBelt(), currentUser.isRole(Role.R)));
            if (currentUser.isRole(Role.R)) {
                model.addAttribute("refereeRangeSelector", refereeRangeService.generateActiveRangesSelect(currentUser.getRefereeRange(), true));
            }
        }
        return "/myProfile/edit";
    }

    // Method for saving edited values of user when {@link #editProfile(Model) editProfile} form is filled and sent.
    @PostMapping("/myProfile/edit")
    public String editingUser(@RequestParam String licenseId,
                              @RequestParam String beltSelector,
                              @RequestParam(required = false) String gym,
                              @RequestParam(required = false) Integer weight,
                              @RequestParam(required = false) RefereeRange refereeRange,
                              @RequestParam String nickname,
                              @RequestParam int phone,
                              @RequestParam String email,
                              MultipartFile image) throws IOException {
        User user = userService.getUserOrNull(licenseId);
        // Common fields
        user.setBelt(beltService.findBeltById(beltSelector)).setNickname(nickname).setPhone(phone).setEmail(email);
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

    @GetMapping("/ranking")
    public String getRanking(HttpServletRequest request, Model model) {
        List<?> rankingList = userService.getRanking();
        model.addAttribute("header", headerInflater.getHeader("Ranking", request, "bootstrap/css/bootstrap.min.css", "aos/aos.css", "font-awesome/css/all.css", "style", "header", "bootstrapAccomodations", "responsiveTable", "profiles", "beltAssignations"))
                .addAttribute("list", rankingList);
        return "/ranking";
    }
}
