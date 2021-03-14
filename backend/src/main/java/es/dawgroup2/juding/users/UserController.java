package es.dawgroup2.juding.users;

import es.dawgroup2.juding.belts.BeltService;
import es.dawgroup2.juding.users.gender.GenderService;
import es.dawgroup2.juding.users.refereeRange.RefereeRange;
import es.dawgroup2.juding.users.refereeRange.RefereeRangeService;
import es.dawgroup2.juding.users.roles.Role;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    BeltService beltService;

    @Autowired
    GenderService genderService;

    @Autowired
    RefereeRangeService refereeRangeService;

    @Autowired
    private JavaMailSender emailSender;

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
        model.addAttribute("user", user)
                .addAttribute("beltSelector", beltService.getSelectField(user.getBelt()))
                .addAttribute("genderRadioInput", genderService.getRadioField(user.getGender()))
                .addAttribute("isCompetitor", user.isRole(Role.C));
        if (user.isRole(Role.R)) {
            model.addAttribute("refereeRangeSelector", refereeRangeService.generateActiveRangesSelect(user.getRefereeRange(), true));
        }
        return "/admin/user/edit";
    }


    /*
     * SAVES
     */
    @PostMapping("/admin/user/edit/save")
    public String savingUser(@RequestParam String licenseId,
                             @RequestParam String name,
                             @RequestParam String surname,
                             @RequestParam String dni,
                             @RequestParam int phone,
                             @RequestParam String email,
                             @RequestParam String birthdate,
                             @RequestParam String nickname,
                             MultipartFile profileImage,
                             @RequestParam String gender,
                             @RequestParam int weight,
                             @RequestParam String gym,
                             @RequestParam String belt,
                             @RequestParam(required = false) String refereeRange) throws IOException {
        User currentUser = userService.getUserOrNull(licenseId);
        currentUser.setName(name)
                .setSurname(surname)
                .setDni(dni)
                .setPhone(phone)
                .setEmail(email)
                .setNickname(nickname)
                .setGender(genderService.findGenderById(gender))
                .setWeight(weight)
                .setGym(gym)
                .setBelt(beltService.findBeltById(belt));
        if (refereeRange != null)
            if (!refereeRange.isEmpty())
                currentUser.setRefereeRange(refereeRangeService.findRefereeRangeById(refereeRange));
        if (profileImage != null)
            if (!profileImage.isEmpty())
                currentUser.setProfileImage(BlobProxy.generateProxy(profileImage.getInputStream(), profileImage.getSize()));
        userService.save(currentUser);
        if (currentUser.isRole(Role.C))
            return "redirect:/admin/user/list/competitors";
        else
            return "redirect:/admin/user/list/referees";
    }

    @GetMapping("/admin/user/admitReferee/{licenseId}")
    public String admitReferee(@PathVariable String licenseId) {
        User user = userService.getUserOrNull(licenseId);
        userService.save(user.setRefereeRange(RefereeRange.E));

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String htmlMsg = "Estimado <strong>" + user.getName() + " " + user.getSurname() + "</strong>.<br>" +
                "<br>" +
                "Le anunciamos que ha sido <strong>ADMITIDO</strong> como árbitro en la Federación de Judo de la Comunidad de Madrid.<br>" +
                "Ya puede acceder a la aplicación oficial haciendo uso de las credenciales que proporcionó al registrarse.<br>" +
                "<br>" +
                "Reciba un cordial saludo.<br>" +
                "<br>" +
                "Federación de Judo de la Comunidad de Madrid.";
        try {
            helper.setText(htmlMsg, true); // Use this or above line.
            helper.setTo(user.getName() + " " + user.getSurname() + " <" + user.getEmail() + ">");
            helper.setSubject("Solicitud de admisión de árbitros");
            helper.setFrom("Federación de Judo CAM <juding.noreply@gmail.com>");
            emailSender.send(mimeMessage);
        } catch (Exception e) {
            return "redirect:/error/500";
        }

        return "redirect:/admin/user/list/referees";
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