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

    /**
     * Checks if a License ID is already registered in database.
     *
     * @param licenseId License ID
     * @param req HTTP Servlet Request (for matching license ID with current user ID if logged in).
     * @return True if License ID is already registered, false otherwise
     */
    @GetMapping("/formCheck/licenseId")
    public boolean checkSignUpLicenseId(@RequestParam String licenseId, HttpServletRequest req) {
        if (req.getUserPrincipal() != null) {
            User curUser = userService.findByNickname(req.getUserPrincipal().getName());
            if (curUser.getLicenseId().equals(licenseId))
                return true;
        }
        return !userService.userExists(licenseId);
    }

    /**
     * Checks if a nickname is already registered in database.
     *
     * @param nickname Nickname
     * @param req HTTP Servlet Request (for matching nickname with current user ID if logged in).
     * @return True if nickname is already registered, false otherwise
     */
    @GetMapping("/formCheck/nickname")
    public boolean checkSignUpNickname(@RequestParam String nickname, HttpServletRequest req) {
        if (req.getUserPrincipal() != null) {
            if (req.getUserPrincipal().getName().equals(nickname))
                return true;
        }
        return !userService.userExistsByNickname(nickname);
    }

    /**
     * Checks if a DNI is already registered in the database for a determined role.
     *
     * @param dni DNI of a user.
     * @param role Role for which check is requested.
     * @return True if DNI is already registered for this role, false otherwise
     */
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
