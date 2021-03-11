package es.dawgroup2.juding.others;

import es.dawgroup2.juding.users.User;
import es.dawgroup2.juding.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Optional;

@Controller
public class PrivateUserController {

    // TODO CHANGE THIS WHEN SESSION IS CONTROLLED
    String licenseId = "1234567890";

    @Autowired
    UserService userService;

    @GetMapping("/myHome")
    public String myHome(Model model, HttpServletResponse response) {
        User currentUser = userService.getUserOrNull(licenseId);
        if (currentUser == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            model.addAttribute("user", currentUser)
                    .addAttribute("isCompetitor", currentUser.isCompetitor())
                    .addAttribute("stringRange", currentUser.getRefereeRange());
        }
        return "myHome";
    }

    @GetMapping("/profileImage/{licenseId}")
    public ResponseEntity<Object> downloadProfileImage(@PathVariable String licenseId) throws SQLException {
        User user = userService.getUserOrNull(licenseId);
        if (user != null && user.getProfileImage() != null) {
            Resource file = new InputStreamResource(user.getProfileImage().getBinaryStream());
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .contentLength(user.getProfileImage().length()).body(file);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/myProfile")
    public String myProfile(Model model, HttpServletResponse response) {
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
