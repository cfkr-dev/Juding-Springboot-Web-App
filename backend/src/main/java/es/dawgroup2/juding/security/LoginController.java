package es.dawgroup2.juding.security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @RequestMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("login/{error}")
    public String loginError(@PathVariable(required = false) String error, Model model) {
        model.addAttribute("error",(error!=null));
        return "/login";
    }


}
