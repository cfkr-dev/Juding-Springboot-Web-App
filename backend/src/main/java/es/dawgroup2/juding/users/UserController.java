package es.dawgroup2.juding.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    String licenseId = "1234567890";

    /*
     * ADMIN PAGES - LIST AND EDITION OF USERS
     */
    @GetMapping("/admin/user/list/{role}")
    public String userList(@PathVariable String role, Model model) {
        List<User> userList;
        if (role.equals("competitors")) {
            model.addAttribute("userList", userService.getCompetitors()).addAttribute("competitors", true);
        } else if (role.equals("referees")) {
            // Detect if there are pending applications
            if (userService.refereePendingApplications() > 0){
                model.addAttribute("pendingApplications", true).addAttribute("pendingList", userService.getRefereeApplications());
            } else {
                model.addAttribute("pendingApplications", false);
            }
            model.addAttribute("userList", userService.getActiveReferees()).addAttribute("competitors", false);
        } else {
            userList = null;
        }
        return "/admin/user/list";
    }

    @GetMapping("/admin/user/edit/{licenseId}")
    public String editUser(@PathVariable String licenseId, Model model) {
        User user = userRepository.findById(licenseId).orElseThrow();
        model.addAttribute("user", user);
        return "/admin/user/edit";
    }

    @PostMapping("/admin/user/edit/save")
    public String savingUser(User user) {
        if (user.getRole() == null) {
            user.setRole(userRepository.findById(user.getLicenseId()).orElseThrow().getRole());
        }
        userRepository.save(user);
        return "redirect:/admin/user/list/";
    }
}
