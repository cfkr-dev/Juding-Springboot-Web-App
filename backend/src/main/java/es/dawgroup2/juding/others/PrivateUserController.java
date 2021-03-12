package es.dawgroup2.juding.others;

import es.dawgroup2.juding.belts.BeltService;
import es.dawgroup2.juding.users.User;
import es.dawgroup2.juding.users.UserService;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@Controller
public class PrivateUserController {

    // TODO CHANGE THIS WHEN SESSION IS CONTROLLED
    String licenseId = "JU-1234567893";

    @Autowired
    UserService userService;

    @Autowired
    BeltService beltService;

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
        return "/myProfile/index";
    }

    @GetMapping("/myProfile/edit")
    public String editProfile(Model model, HttpServletResponse response) {
        User currentUser = userService.getUserOrNull(licenseId);
        if (currentUser == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            model.addAttribute("user", currentUser)
                    .addAttribute("isCompetitor", currentUser.isCompetitor())
                    .addAttribute("beltSelector", beltService.getSelectField(currentUser.getBelt().name()));
        }
        return "/myProfile/edit";
    }

    @PostMapping("/myProfile/edit")
    public String editingUser(Model model, @RequestParam String licenseId,
                              @RequestParam String beltSelector,
                              @RequestParam(required = false) String gym,
                              @RequestParam(required = false) Integer weight,
                              @RequestParam(required = false) Integer refereeRange,
                              @RequestParam String nick,
                              @RequestParam int phone,
                              @RequestParam String email,
                              MultipartFile image) throws IOException {
        User user = userService.getUserOrNull(licenseId);
        // Common fields
        user.setBelt(beltService.findBeltById(beltSelector)).setNickname(nick).setPhone(phone).setEmail(email);
        // Role-based fields
        if (user.getRole() == 1) {
            user.setGym(gym).setWeight(weight);
        } else if (user.getRole() == 2) {
            user.setRefereeRange(refereeRange);
        }
        // Changing image
        if (!image.isEmpty()) {
            user.setProfileImage(BlobProxy.generateProxy(image.getInputStream(), image.getSize()));
        }
        userService.save(user);
        return "redirect:/myProfile";
    }
}
