package es.dawgroup2.juding.others;

import es.dawgroup2.juding.users.User;
import es.dawgroup2.juding.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class PrivateUserController {

    // TODO CHANGE THIS WHEN SESSION IS CONTROLLED
    String licenseId = "1234567890";

    @Autowired
    UserService userService;

    @GetMapping("/myHome")
    public String myHome(Model model, HttpServletResponse response){
        User currentUser = userService.getUserOrNull(licenseId);
        if (currentUser == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            model.addAttribute("user", currentUser);
        }
        return "myHome";
    }

    @GetMapping("/myProfile")
    public String myProfile(Model model, HttpServletResponse response){
        User currentUser = userService.getUserOrNull(licenseId);
        if (currentUser == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            model.addAttribute("user", currentUser)
                 .addAttribute("isCompetitor", currentUser.isCompetitor())
                 .addAttribute("isMale", currentUser.isMale())
                 .addAttribute("beltValue", currentUser.getBelt().getLongName());
        }
        return "myProfile";
    }
}
