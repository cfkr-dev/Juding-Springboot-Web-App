package es.dawgroup2.juding.users;

import es.dawgroup2.juding.belts.BeltService;
import es.dawgroup2.juding.users.refereeRange.RefereeRangeService;
import es.dawgroup2.juding.users.roles.Role;
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
    UserService userService;

    @Autowired
    BeltService beltService;

    @Autowired
    RefereeRangeService refereeRangeService;

    /*
     * VIEWS
     */
    @GetMapping("/admin/user/list/{stringRole}")
    public String userList(@PathVariable String stringRole, Model model) {
        if (stringRole.equals("competitors")) {
            model.addAttribute("userList", userService.getCompetitors()).addAttribute("competitors", true);
        } else if (stringRole.equals("referees")) {
            if (userService.refereePendingApplications() > 0) {
                model.addAttribute("pendingApplications", true).addAttribute("pendingList", userService.getRefereeApplications());
            } else {
                model.addAttribute("pendingApplications", false);
            }
            model.addAttribute("userList", userService.getActiveReferees()).addAttribute("competitors", false);
        }
        return "/admin/user/list";
    }

    @GetMapping("/admin/user/edit/{licenseId}")
    public String editUser(@PathVariable String licenseId, Model model) {
        User user = userService.getUserOrNull(licenseId);
        model.addAttribute("user", user).addAttribute("beltSelector", beltService.getSelectField(user.getBelt()));
        if (user.isRole(Role.R)) {
            model.addAttribute("refereeRangeSelector", refereeRangeService.generateActiveRangesSelect(user.getRefereeRange(), true));
        }
        return "/admin/user/edit";
    }


    /*
     * SAVES
     */
    @PostMapping("/admin/user/edit/save")
    public String savingUser(User user) {
        userService.save(user);
        return "redirect:/admin/user/list/";
    }


    /*
     * DELETES
     */
    @GetMapping("/admin/user/delete/{licenseId}")
    public String deleteUser(@PathVariable String licenseId) {
        List<Role> rolesOfUser = userService.getUserRolesOrNull(licenseId);
        userService.delete(userService.getUserOrNull(licenseId));
        if (rolesOfUser.contains(Role.C))
            return "redirect:/admin/user/list/competitors";
        else
            return "redirect:/admin/user/list/referees";
    }
}
