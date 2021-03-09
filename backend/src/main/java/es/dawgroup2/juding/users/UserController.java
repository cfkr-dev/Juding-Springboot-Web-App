package es.dawgroup2.juding.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    String licenseId = "1234567890";

    /*
     * ADMIN PAGES - LIST AND EDITION OF USERS
     */
    @GetMapping("/admin/user/list")
    public String userList(Model model){
        List<User> userList = userRepository.findAll();
        model.addAttribute("userList", userList);
        return "/admin/user/list";
    }

    @GetMapping("/admin/user/edit/{licenseId}")
    public String editUser(@PathVariable String licenseId, Model model){
        User user = userRepository.findById(licenseId).orElseThrow();
        model.addAttribute("user", user);
        return "/admin/user/edit";
    }
}
