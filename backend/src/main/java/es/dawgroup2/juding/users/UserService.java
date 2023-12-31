package es.dawgroup2.juding.users;

import es.dawgroup2.juding.auxTypes.belts.BeltService;
import es.dawgroup2.juding.auxTypes.gender.GenderService;
import es.dawgroup2.juding.auxTypes.refereeRange.RefereeRange;
import es.dawgroup2.juding.auxTypes.refereeRange.RefereeRangeService;
import es.dawgroup2.juding.auxTypes.roles.Role;
import es.dawgroup2.juding.main.DateService;
import es.dawgroup2.juding.main.image.ImageService;
import es.dawgroup2.juding.main.rest.RankingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BeltService beltService;

    @Autowired
    GenderService genderService;

    @Autowired
    ImageService imageService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RefereeRangeService refereeRangeService;

    @Autowired
    DateService dateService;

    @Autowired
    private JavaMailSender emailSender;

    /**
     * Retrieves the list of competitors registered in the app.
     *
     * @return List of competitors.
     */
    public List<User> getCompetitors() {
        return userRepository.findByRolesContaining(Role.C);
    }

    /**
     * Returns a page of competitors (each page contains 10 elements).
     *
     * @param num Number of page
     * @return Page
     */
    public Page<User> getCompetitorsInPages(int num) {
        return userRepository.findByRolesContaining(Role.C, PageRequest.of(num, 10));
    }

    /**
     * Retrieves the list of active referees registered in the app.
     *
     * @return List of active referees.
     */
    public List<User> getActiveReferees() {
        return userRepository.findByRolesContainingAndRefereeRangeNot(Role.R, RefereeRange.S);
    }

    /**
     * Retrieves a list of active referees registered in the app in HTML options format.
     *
     * @return List in a String.
     */
    public String getActiveRefereesList() {
        return getActiveRefereesListAux("");
    }

    /**
     * Retrieves active referees in pages.
     *
     * @param num Num of page
     * @return Page of referees
     */
    public Page<User> getActiveRefereesInPages(int num) {
        return userRepository.findByRolesContainingAndRefereeRangeNot(Role.R, RefereeRange.S, PageRequest.of(num, 10));
    }

    /**
     * Retrieves a list of active referees registered in the app in HTML options format.
     * It includes also a {@code selected} attribute for specified referee.
     *
     * @param activeLicenseId License ID of active referee.
     * @return List in a String.
     */
    public String getActiveRefereesList(String activeLicenseId) {
        return getActiveRefereesListAux(activeLicenseId);
    }

    private String getActiveRefereesListAux(String activeLicenseId) {
        StringBuilder sb = new StringBuilder();
        List<User> referees = getActiveReferees();
        for (User u : referees) {
            sb.append("<option value=\"")
                    .append(u.getLicenseId())
                    .append("\"");
            if (u.getLicenseId().equals(activeLicenseId))
                sb.append(" selected");
            sb.append(">")
                    .append(u.getName())
                    .append(" ")
                    .append(u.getSurname())
                    .append(" (")
                    .append(u.getLicenseId())
                    .append(")</option>");
        }
        return sb.toString();
    }

    /**
     * Retrieves the list of active referee applications in course.
     *
     * @return List of active applications.
     */
    public List<User> getRefereeApplications() {
        return userRepository.findByRolesContainingAndRefereeRange(Role.R, RefereeRange.S);
    }

    /**
     * Retrieves the count of active referee applications.
     *
     * @return Number of pending referee applications.
     */
    public int refereePendingApplications() {
        return userRepository.countUsersByRolesAndRefereeRange(Role.R, RefereeRange.S);
    }

    /**
     * Looks for a user in the repository and returns it (null if it does not exist).
     *
     * @param licenseId License ID (PK).
     * @return User object or null if it does not exist.
     */
    public User getUserOrNull(String licenseId) {
        Optional<User> opt = userRepository.findById(licenseId);
        return opt.orElse(null);
    }

    /**
     * Find a user by his nickname
     *
     * @param nickname Nickname
     * @return User (null otherwise)
     */
    public User findByNickname(String nickname) {
        return userRepository.findByNickname(nickname).orElse(null);
    }

    /**
     * Checks if user exists (true if exists, false if not).
     *
     * @param licenseId License ID that has to be checked
     * @return True if user exists (false otherwise).
     */
    public boolean userExists(String licenseId) {
        return getUserOrNull(licenseId) != null;
    }

    /**
     * Checks if user exists by a given nickname on database.
     *
     * @param nickname Nickname that has to be checked
     * @return {@code True} if exists, {@code False} otherwise.
     */
    public boolean userExistsByNickname(String nickname) {
        Optional<User> userByNickname = userRepository.findByNickname(nickname);
        return userByNickname.isPresent();
    }

    /**
     * Retrieves the ranking with its necessary fields and in descendant order.
     *
     * @return Returns ranking using {@link RankingDTO Ranking Data Transfer Object}.
     */
    public List<RankingDTO> getRanking() {
        List<RankingDTO> out = new ArrayList<>();
        List<?> list = userRepository.getRanking();
        for (Object o : list)
            out.add(new RankingDTO((Object[]) o));
        return out;
    }

    /**
     * Checks if user can sign up by DNI.
     *
     * @param dni  DNI
     * @param role Current sign up form role
     * @return {@code True} if sign up is possible, {@code False} otherwise.
     */
    public boolean checkSignUpValidityByDni(String dni, Role role) {
        List<User> userByDni = userRepository.findByDni(dni);
        if (userByDni.size() == 0) {
            return true;
        } else if (userByDni.size() == 1) {
            return (!userByDni.get(0).getRoles().contains(role));
        } else {
            return false;
        }
    }

    /**
     * Return the list of roles of a user attending to its license ID.
     *
     * @param licenseId License ID (PK).
     * @return List of roles (null if it does not exist).
     */
    public Set<Role> getUserRolesOrNull(String licenseId) {
        User user = getUserOrNull(licenseId);
        if (user != null)
            return user.getRoles();
        else
            return null;
    }

    /**
     * Admitting a referee.
     *
     * @param licenseId License ID of a referee.
     * @return Admitted user entity.
     */
    public User admitReferee(String licenseId) {
        User user = getUserOrNull(licenseId);
        if (user == null) return null;
        save(user.setRefereeRange(RefereeRange.E));

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
            helper.setFrom("Federación de Judo Comunidad de Madrid <juding.noreply@gmail.com>");
            emailSender.send(mimeMessage);
        } catch (Exception e) {
            return null;
        }
        return user;
    }

    /**
     * Checks if the user who wants to change some data doesn't use the same nickname as another user.
     *
     * @param licenseId License id of the competitor.
     * @param nickname  Nickname that the user wants to use.
     * @return True if the nickname is already used by another user.
     */
    public boolean matchingLicenseAndNickname(String licenseId, String nickname) {
        User user;
        User user2;
        Optional<User> userOpt = userRepository.findById(licenseId);
        Optional<User> userOpt2 = userRepository.findByNickname(nickname);
        if (userOpt.isPresent()) {
            if (userOpt2.isPresent()) {
                user = userOpt.get();
                user2 = userOpt2.get();
                return !user.getLicenseId().equals(user2.getLicenseId());
            }
        }
        return false;
    }

    /**
     * Checks if the new user who wants to create his profile doesn't use the same licence id, the nickname or the Dni as another user
     *
     * @param licenseId License id of the competitor that the user wants to use
     * @param nickname  Nickname that the user wants to use
     * @return 3 if every check was successful, 0 if no check was successful, 1 if nickname was not present but license ID was and 2 if nickname was present but license ID was not present.
     */
    public int matchingLicenseOrNickname(String licenseId, String nickname) {
        if (userRepository.findById(licenseId).isPresent())
            return (userRepository.findByNickname(nickname).isPresent()) ? 0 : 1;
        else
            return (userRepository.findByNickname(nickname).isPresent()) ? 2 : 3;
    }

    /**
     * Saving user into repository.
     *
     * @param user User.
     * @return User object that was saved.
     */
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Creates a user attending to received parameters and then saves it into database.
     *
     * @param name         Name.
     * @param surname      Surname
     * @param gender       Gender.
     * @param phone        Phone.
     * @param email        Email.
     * @param birthDate    Birth date.
     * @param dni          DNI.
     * @param licenseId    License ID.
     * @param nickname     Nickname.
     * @param belt         Belt.
     * @param role         Role.
     * @param gym          Gym.
     * @param weight       Weight.
     * @param refereeRange Referee range.
     * @return User object that was saved.
     */
    public User save(String name, String surname, String gender, String phone, String email, String birthDate, String dni, String licenseId, String nickname, String password, String securityQuestion, String securityAnswer, MultipartFile image, String belt, Role role, String gym, Integer weight, String refereeRange) {
        // If user already existed, must be retrieved and changed
        User user;
        Optional<User> optUser = userRepository.findById(licenseId);
        if (optUser.isPresent()) {
            user = optUser.get();
        } else {
            user = new User();
            user.setLicenseId(licenseId).addRole(role);
        }

        if (name != null && !name.isBlank()) user.setName(name);
        if (surname != null && !surname.isBlank()) user.setSurname(surname);
        if (gender != null && !gender.isBlank()) user.setGender(genderService.findGenderById(gender));
        if (email != null && !email.isBlank()) user.setEmail(email);
        try {
            user.setPhone((phone == null || phone.isBlank()) ? null : Integer.parseInt(phone));
            if (birthDate != null && !birthDate.isBlank()) user.setBirthDate(dateService.stringToDate(birthDate));
            if (image != null)
                if (!image.isEmpty()) {
                    user.setImageFile(imageService.uploadProfileImage(image));
                    user.setMimeProfileImage(image.getContentType());
                }
        } catch (Exception e) {
            return null;
        }
        if (dni != null && !dni.isBlank()) user.setDni(dni);
        if (nickname != null && !nickname.isBlank()) user.setNickname(nickname);
        if (password != null && !password.isBlank()) user.setPassword(passwordEncoder.encode(password));
        if (securityQuestion != null && !securityQuestion.isBlank()) user.setSecurityQuestion(securityQuestion);
        if (securityAnswer != null && !securityAnswer.isBlank()) user.setSecurityAnswer(securityAnswer);
        if (belt != null && !belt.isBlank()) user.setBelt(beltService.findBeltById(belt));
        if (gym != null && !gym.isBlank()) user.setGym(gym);
        if (weight != null) user.setWeight(weight);
        if (refereeRange != null && !refereeRange.isBlank())
            user.setRefereeRange(refereeRangeService.findRefereeRangeById(refereeRange));
        return userRepository.save(user);
    }

    /**
     * Saving many users into repository.
     *
     * @param users List of users.
     * @return User iterable with saved items.
     */
    public List<User> saveAll(List<User> users) {
        return userRepository.saveAll(users);
    }

    /**
     * Deleting user from the repository attending to its license ID.
     *
     * @param licenseId License ID of user to be deleted from repository.
     * @return Deleted user (if existed).
     */
    public User delete(String licenseId) {
        Optional<User> user = userRepository.findById(licenseId);
        user.ifPresent(value -> userRepository.delete(value));
        return user.orElse(null);
    }

    /**
     * Deleting user from the repository.
     *
     * @param user User to be deleted.
     * @return Deleted user.
     */
    public User delete(User user) {
        userRepository.delete(user);
        return user;
    }
}
