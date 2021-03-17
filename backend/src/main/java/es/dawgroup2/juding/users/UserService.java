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
     * @return List of competitors.
     */
    public List<User> getCompetitors(){
        return userRepository.findByRolesContaining(Role.C);
    }

    /**
     * Retrieves the list of active referees registered in the app.
     * @return List of active referees.
     */
    public List<User> getActiveReferees(){
        return userRepository.findByRolesContainingAndRefereeRangeNot(Role.R, RefereeRange.S);
    }

    /**
     * Retrieves the list of active referee applications in course.
     * @return List of active applications.
     */
    public List<User> getRefereeApplications(){
        return userRepository.findByRolesContainingAndRefereeRange(Role.R, RefereeRange.S);
    }

    /**
     * Retrieves the count of active referee applications.
     * @return Number of pending referee applications.
     */
    public int refereePendingApplications(){
        return userRepository.countUsersByRolesAndRefereeRange(Role.R, RefereeRange.S);
    }

    /**
     * Looks for a user in the repository and returns it (null if it does not exist).
     * @param licenseId License ID (PK).
     * @return User object or null if it does not exist.
     */
    public User getUserOrNull(String licenseId) {
        Optional<User> opt = userRepository.findById(licenseId);
        return opt.orElse(null);
    }

    /**
     * Checks if user exists (true if exists, false if not).
     * @param licenseId License ID that has to be checked
     * @return True if user exists (false otherwise).
     */
    public boolean userExists(String licenseId){
        return getUserOrNull(licenseId) != null;
    }

    /**
     * Return the list of roles of a user attending to its license ID.
     * @param licenseId License ID (PK).
     * @return List of roles (null if it does not exist).
     */
    public Set getUserRolesOrNull(String licenseId){
        User user = getUserOrNull(licenseId);
        if (user != null)
            return user.getRoles();
        else
            return null;
    }

    /**
     * Saving user into repository.
     * @param user User.
     * @return User object that was saved.
     */
    public User save(User user){
        return userRepository.save(user);
    }

    /**
     * Saving many users into repository.
     * @param users List of users.
     * @return User iterable with saved items.
     */
    public List<User> saveAll(List<User> users){
        return userRepository.saveAll(users);
    }

    /**
     * Deleting user from the repository.
     * @param user User to be deleted from repository.
     */
    public void delete(User user){
        if (user != null)
            userRepository.delete(user);
    }
}
