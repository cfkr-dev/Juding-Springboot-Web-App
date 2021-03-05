package es.dawgroup2.juding.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

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
}
