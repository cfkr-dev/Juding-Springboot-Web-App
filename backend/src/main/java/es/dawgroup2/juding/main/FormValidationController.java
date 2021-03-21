package es.dawgroup2.juding.main;

import es.dawgroup2.juding.auxTypes.roles.Role;
import es.dawgroup2.juding.users.User;
import es.dawgroup2.juding.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class FormValidationController {

    @Autowired
    UserService userService;

    @GetMapping("/formCheck/licenseId")
    public boolean checkSignUpLicenseId(@RequestParam String licenseId, HttpServletRequest req) {
        if (req.getUserPrincipal() != null) {
            User curUser = userService.findByNickname(req.getUserPrincipal().getName());
            if (curUser.getLicenseId().equals(licenseId))
                return true;
        }
        return !userService.userExists(licenseId);
    }

    @GetMapping("/formCheck/nickname")
    public boolean checkSignUpNickname(@RequestParam String nickname, HttpServletRequest req) {
        if (req.getUserPrincipal() != null) {
            if (req.getUserPrincipal().getName().equals(nickname))
                return true;
        }
        return !userService.userExistsByNickname(nickname);
    }

    @GetMapping("/formCheck/dni")
    public boolean checkSignUpDNI(@RequestParam String dni, @RequestParam String role) {
        for (Role r : Role.values()) {
            if (r.getLongName().equals(role)) {
                return userService.checkSignUpValidityByDni(dni, r);
            }
        }
        return false;
    }
}
