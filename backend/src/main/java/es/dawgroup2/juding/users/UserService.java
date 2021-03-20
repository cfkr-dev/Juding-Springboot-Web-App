package es.dawgroup2.juding.users;

import es.dawgroup2.juding.auxTypes.refereeRange.RefereeRange;
import es.dawgroup2.juding.auxTypes.roles.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves the list of competitors registered in the app.
     *
     * @return List of competitors.
     */
    public List<User> getCompetitors() {
        return userRepository.findByRolesContaining(Role.C);
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
                    .append(u.getLicenseId());
            if (u.getLicenseId().equals(activeLicenseId))
                sb.append(" selected");
            sb.append("\">")
                    .append(u.getName())
                    .append(" ")
                    .append(u.getSurname())
                    .append("</option>");
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
     * Retrieves the ranking with its necessary fields and in descendant order
     * @return
     */
    public List<?> getRanking(){
        return userRepository.getRanking();
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
     * Saving user into repository.
     *
     * @param user User.
     * @return User object that was saved.
     */
    public User save(User user) {
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
     * Deleting user from the repository.
     *
     * @param user User to be deleted from repository.
     */
    public void delete(User user) {
        if (user != null)
            userRepository.delete(user);
    }
}
