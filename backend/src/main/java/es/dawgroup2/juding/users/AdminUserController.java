package es.dawgroup2.juding.users;

import es.dawgroup2.juding.auxTypes.belts.BeltService;
import es.dawgroup2.juding.auxTypes.gender.GenderService;
import es.dawgroup2.juding.auxTypes.refereeRange.RefereeRange;
import es.dawgroup2.juding.auxTypes.refereeRange.RefereeRangeService;
import es.dawgroup2.juding.auxTypes.roles.Role;
import es.dawgroup2.juding.main.DateService;
import es.dawgroup2.juding.main.HeaderInflater;
import es.dawgroup2.juding.main.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Set;

@Controller
public class AdminUserController {
    @Autowired
    HeaderInflater headerInflater;

    @Autowired
    UserService userService;

    @Autowired
    BeltService beltService;

    @Autowired
    GenderService genderService;

    @Autowired
    RefereeRangeService refereeRangeService;

    @Autowired
    DateService dateService;

    @Autowired
    ImageService imageService;

    @Autowired
    private JavaMailSender emailSender;

    /**
     * Retrieves a view with a list of users (both competitors and referees) according to their role.
     * Besides, when referees are listed, it is included a list of pending applications.
     *
     * @param stringRole Role of listed users (given by URL).
     * @param model      Model.
     * @return Dynamic view with a list of users.
     */
    @GetMapping("/admin/user/list/{stringRole}")
    public String userList(@PathVariable String stringRole, HttpServletRequest request, Model model) {
        model.addAllAttributes(headerInflater.getHeader("Lista de usuarios", request, "bootstrap/css/bootstrap.min.css", "aos/aos.css", "font-awesome/css/all.css", "style", "header", "profiles", "bootstrapAccomodations", "responsiveTable", "adminScreen", "beltAssignations"));
        if (stringRole.equals("competitors")) {
            Page<User> firstPage = userService.getCompetitorsInPages(0);
            model.addAttribute("competitors", true)
                    .addAttribute("empty", firstPage.getTotalElements() == 0)
                    .addAttribute("userPage", firstPage.getContent())
                    .addAttribute("morePages", firstPage.hasNext())
                    .addAttribute("totalPages", firstPage.getTotalPages());
        } else if (stringRole.equals("referees")) {
            if (userService.refereePendingApplications() > 0) {
                model.addAttribute("pendingApplications", true).addAttribute("pendingList", userService.getRefereeApplications());
            } else {
                model.addAttribute("pendingApplications", false);
            }
            Page<User> firstPage = userService.getActiveRefereesInPages(0);
            model.addAttribute("competitors", false)
                    .addAttribute("userPage", firstPage.getContent())
                    .addAttribute("morePages", firstPage.hasNext())
                    .addAttribute("totalPages", firstPage.getTotalPages());
        } else {
            return "redirect:/error/404";
        }
        return "/admin/user/list";
    }

    /**
     * Returns a inflated page of users (even competitors or referees).
     *
     * @param stringRole Role of user in string format.
     * @param page       Number of page requested.
     * @param model      Model.
     * @return Inflated page.
     */
    @GetMapping("/admin/{stringRole}/list/{page}")
    public String getUserPage(@PathVariable String stringRole, @PathVariable String page, Model model) {
        if (stringRole.equals("competitors")) {
            Page<User> userPage = userService.getCompetitorsInPages(Integer.parseInt(page));
            model.addAttribute("competitors", true)
                    .addAttribute("userPage", userPage.getContent());
            return "/admin/user/inflatedListCompetitor";
        } else {
            Page<User> userPage = userService.getActiveRefereesInPages(Integer.parseInt(page));
            model.addAttribute("competitors", false)
                    .addAttribute("userPage", userPage.getContent());
            return "/admin/user/inflatedListReferee";
        }
    }

    /**
     * Returns a view with a form for editing user information (with common values and also fields for specific-role information).
     *
     * @param licenseId License ID (PK) of edited user.
     * @param model     Model.
     * @return Dynamic view with form.
     */
    @GetMapping("/admin/user/edit/{licenseId}")
    public String editUser(@PathVariable String licenseId, HttpServletRequest request, Model model) {
        User user = userService.getUserOrNull(licenseId);
        model.addAllAttributes(headerInflater.getHeader("Editar usuario", request, "bootstrap/css/bootstrap.min.css", "bootstrap-datepicker/bootstrap-datepicker.css", "font-awesome/css/all.css", "header", "bootstrapAccomodations"))
                .addAttribute("user", user)
                .addAttribute("beltSelector", beltService.getSelectField(user.getBelt(), false))
                .addAttribute("genderSelection", genderService.getRadioField(user.getGender()))
                .addAttribute("isCompetitor", user.isRole(Role.C));
        if (user.isRole(Role.R)) {
            model.addAttribute("refereeRangeSelector", refereeRangeService.generateActiveRangesSelect(user.getRefereeRange(), true));
        }
        return "/admin/user/edit";
    }

    /**
     * This method receives information from {@link #editUser(String, HttpServletRequest, Model) editUser} generated view and save them into database.
     * Different information types are properly processed before sending them to the repository
     *
     * @param name         Name
     * @param surname      Surname
     * @param gender       Gender
     * @param phone        Phone
     * @param email        Email
     * @param birthDate    Birth date
     * @param dni          DNI
     * @param licenseId    License ID (PK)
     * @param nickname     Nick name
     * @param belt         Belt
     * @param gym          Gym
     * @param weight       Weight
     * @param refereeRange Referee range (if it's a referee)
     * @return Redirection to control panel.
     */
    @PostMapping("/admin/user/edit/save")
    public String savingUser(@RequestParam String name,
                             @RequestParam String surname,
                             @RequestParam String gender,
                             @RequestParam String phone,
                             @RequestParam String email,
                             @RequestParam String birthDate,
                             @RequestParam String dni,
                             @RequestParam String licenseId,
                             @RequestParam String nickname,
                             @RequestParam String belt,
                             @RequestParam(required = false) String gym,
                             @RequestParam(required = false) Integer weight,
                             @RequestParam(required = false) String refereeRange
    ) throws ParseException {
        User currentUser = userService.getUserOrNull(licenseId);
        currentUser.setName(name)
                .setSurname(surname)
                .setDni(dni)
                .setPhone((phone.equals("")) ? null : Integer.parseInt(phone))
                .setEmail(email)
                .setNickname(nickname)
                .setBirthDate(dateService.stringToDate(birthDate))
                .setGender(genderService.findGenderById(gender))
                .setWeight(weight)
                .setGym(gym)
                .setBelt(beltService.findBeltById(belt));
        if (refereeRange != null)
            if (!refereeRange.isEmpty())
                currentUser.setRefereeRange(refereeRangeService.findRefereeRangeById(refereeRange));
        userService.save(currentUser);
        if (currentUser.isRole(Role.C))
            return "redirect:/admin/user/list/competitors";
        else
            return "redirect:/admin/user/list/referees";
    }

    /**
     * Method used when a referee application is trammited and accepted. It sends an e-mail to the applicant and changes
     * its range.
     *
     * @param licenseId License ID (PK).
     * @return Redirection to list of users (see {@link #userList(String, HttpServletRequest, Model) userList}).
     */
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


    /**
     * Deletes a user from the repository attending to the license ID given in the URL.
     *
     * @param licenseId License ID (PK).
     * @return Redirection to list of users (see {@link #userList(String, HttpServletRequest, Model) userList}).
     */
    @GetMapping("/admin/user/delete/{licenseId}")
    public String deleteUser(@PathVariable String licenseId) {
        Set<Role> rolesOfUser = userService.getUserRolesOrNull(licenseId);
        userService.delete(userService.getUserOrNull(licenseId));
        if (rolesOfUser.contains(Role.C))
            return "redirect:/admin/user/list/competitors";
        else
            return "redirect:/admin/user/list/referees";
    }
}
