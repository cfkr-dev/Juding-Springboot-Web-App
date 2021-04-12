package es.dawgroup2.juding.main;

import es.dawgroup2.juding.auxTypes.belts.BeltService;
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
            model.addAllAttributes(headerInflater.getHeader("Inicio", request, "bootstrap/css/bootstrap.min.css", "font-awesome/css/all.css", "style", "header", "profiles", "responsiveTable", "beltAssignations"))
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
            model.addAllAttributes(headerInflater.getHeader("Mi perfil", request, "bootstrap/css/bootstrap.min.css", "aos/aos.css", "font-awesome/css/all.css", "style", "header", "bootstrapAccomodations", "responsiveTable", "profiles"))
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
            model.addAllAttributes(headerInflater.getHeader("Editar perfil", request, "bootstrap/css/bootstrap.min.css", "aos/aos.css", "font-awesome/css/all.css", "style", "header", "bootstrapAccomodations", "responsiveTable", "profiles"))
                    .addAttribute("user", currentUser)
                    .addAttribute("isCompetitor", currentUser.isRole(Role.C))
                    .addAttribute("beltSelector", beltService.getSelectField(currentUser.getBelt(), currentUser.isRole(Role.R)));
            if (currentUser.isRole(Role.R)) {
                model.addAttribute("refereeRangeSelector", refereeRangeService.generateActiveRangesSelect(currentUser.getRefereeRange(), true));
            }
        }
        return "/myProfile/edit";
    }

    /**
     * Saves the edited values from profile edition.
     *
     * @param licenseId    License ID
     * @param beltSelector Belt
     * @param gym          Gym (if user is competitor)
     * @param weight       Weight (if user is competitor)
     * @param refereeRange Range (if user is referee)
     * @param phone        Phone
     * @param email        Email
     * @param image        Profile image (if changed, if null is not deleted)
     * @return Redirection to profile view page if saving was successful
     * @throws IOException Input-output exception
     */
    @PostMapping("/myProfile/edit")
    public String editingUser(@RequestParam String licenseId,
                              @RequestParam String beltSelector,
                              @RequestParam(required = false) String gym,
                              @RequestParam(required = false) Integer weight,
                              @RequestParam(required = false) String refereeRange,
                              @RequestParam String phone,
                              @RequestParam String email,
                              MultipartFile image,
                              HttpServletRequest request) throws IOException {
        User user = null;
        if (userService.findByNickname(request.getUserPrincipal().getName()).getLicenseId().equals(licenseId))
            user = userService.save(null, null, null, phone, email, null, null, licenseId, null, null, null, null, image, beltSelector, gym, weight, refereeRange);
        if (user != null)
            return "redirect:/myProfile";
        else
            return "redirect:/error/500";
    }

    /**
     * Dynamic view of ranking.
     *
     * @param request HTTP Servlet Request.
     * @param model   Model.
     * @return Ranking view.
     */
    @GetMapping("/ranking")
    public String getRanking(HttpServletRequest request, Model model) {
        List<?> rankingList = userService.getRanking();
        model.addAllAttributes(headerInflater.getHeader("Ranking", request, "bootstrap/css/bootstrap.min.css", "aos/aos.css", "font-awesome/css/all.css", "style", "header", "bootstrapAccomodations", "responsiveTable", "profiles", "beltAssignations"))
                .addAttribute("list", rankingList);
        return "/ranking";
    }
}
