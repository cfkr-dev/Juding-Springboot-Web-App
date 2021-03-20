package es.dawgroup2.juding.users;

import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordRecoveryController {
    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * Returns the dynamic view associated with first screen of password recovery process.
     * @param error Error (optional value).
     * @param model Model.
     * @return Dynamic view of first screen.
     */
    @GetMapping(value = {"passwordRecovery/1", "/passwordRecovery/1/{error}"})
    public String firstScreen(@PathVariable(required = false) String error, Model model) {
        if (error != null)
            if (error.equals("error"))
                model.addAttribute("isError", true);
            else if (error.equals("answerMismatch"))
                model.addAttribute("answerMismatch", true);
        return "/passwordRecovery/first";
    }

    /**
     * Checks if user with license ID specified exists in the application and, if it does, it redirects to second step.
     * @param licenseId License ID.
     * @return Redirection to second screen or to error screen.
     */
    @PostMapping("/passwordRecovery/1")
    public String checkFirstScreen(@RequestParam String licenseId) {
        if (userService.userExists(licenseId))
            return "redirect:/passwordRecovery/2/" + licenseId;
        else
            return "redirect:/passwordRecovery/1/error?";
    }

    /**
     * Returns the dynamic view associated with second screen of password recovery process.
     * @param incomingValue The incoming value for page (license ID if successful process or error otherwise).
     * @param model Model.
     * @return Dynamic view of second screen.
     */
    @GetMapping(value = {"/passwordRecovery/2/{incomingValue}"})
    public String secondScreen(@PathVariable(required = false) String incomingValue, Model model) {
        if (!incomingValue.equals("error")) {
            User curUser = userService.getUserOrNull(incomingValue);
            if (curUser != null) {
                model.addAttribute("securityQuestion", curUser.getSecurityQuestion())
                        .addAttribute("licenseId", curUser.getLicenseId());
                return "/passwordRecovery/second";
            }
        }
        return "redirect:/passwordRecovery/1/error?";
    }

    /**
     * Checks if the answer given by the user to the question is the same that is saved in repository.
     * @param licenseId License ID of user.
     * @param securityAnswer Security answer given by the user.
     * @return Redirection to third page or to error page in case of error.
     */
    @PostMapping("/passwordRecovery/2")
    public String checkSecondScreen(@RequestParam String licenseId, @RequestParam String securityAnswer) {
        User curUser = userService.getUserOrNull(licenseId);
        if (curUser.getSecurityAnswer().equals(securityAnswer)){
            return "redirect:/passwordRecovery/3/" + licenseId;
        } else {
            return "redirect:/passwordRecovery/1/answerMismatch?";
        }
    }

    /**
     * Returns the dynamic view associated with third screen of password recovery process.
     * @param licenseId License ID of user.
     * @param model Model.
     * @return Dynamic view of third screen.
     */
    @GetMapping("/passwordRecovery/3/{licenseId}")
    public String thirdScreen(@PathVariable String licenseId, Model model) {
        model.addAttribute("licenseId", licenseId);
        return "/passwordRecovery/third";
    }

    /**
     * Saves new password for user and redirects to login if successful.
     * @param licenseId License ID of user.
     * @param password New set password.
     * @param model Model.
     * @return Redirection to login screen.
     */
    @PostMapping("/passwordRecovery/3")
    public String checkThirdScreen(@RequestParam String licenseId, @RequestParam String password, Model model){
        User curUser = userService.getUserOrNull(licenseId);
        if (curUser != null){
            userService.save(curUser.setPassword(passwordEncoder.encode(password)));
        }
        return "redirect:/login?";
    }
}
