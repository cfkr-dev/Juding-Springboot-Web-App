package es.dawgroup2.juding.main;

import es.dawgroup2.juding.users.UserService;
import es.dawgroup2.juding.users.roles.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FormValidationController {

    @Autowired
    UserService userService;

    @PostMapping("/formCheck/signUp/licenseId")
    public boolean checkSignUpLicenseId(@RequestParam String licenseId){
        return !userService.userExists(licenseId);
    }

    @PostMapping("/formCheck/signUp/nickname")
    public boolean checkSignUpNickname(@RequestParam String nickname){
        return !userService.userExistsByNickname(nickname);
    }

    @PostMapping("/formCheck/signUp/dni")
    public boolean checkSignUpDNI(@RequestParam String dni, @RequestParam String role){
        for (Role r : Role.values()){
            if (r.getLongName().equals(role)){
                return userService.checkSignUpValidityByDni(dni, r);
            }
        }
        return false;
    }
}
