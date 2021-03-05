package es.dawgroup2.juding.users;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/myProfile")
    public String viewProfile(Model model){
        return "user/viewProfile";
    }
}
