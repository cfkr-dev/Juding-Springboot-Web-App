package es.dawgroup2.juding.users;

import es.dawgroup2.juding.belts.Belt;
import es.dawgroup2.juding.users.refereeRange.RefereeRange;
import es.dawgroup2.juding.users.roles.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /*
     * FETCHING USERS BY THEIR ROLE
     */
    public List<User> getCompetitors(){
        return userRepository.findByRolesContaining(Role.C);
    }

    public List<User> getActiveReferees(){
        return userRepository.findByRolesContainingAndRefereeRangeNot(Role.R, RefereeRange.S);
    }

    public List<User> getRefereeApplications(){
        return userRepository.findByRolesContainingAndRefereeRange(Role.R, RefereeRange.S);
    }

    public int refereePendingApplications(){
        return userRepository.countUsersByRolesAndRefereeRange(Role.R, RefereeRange.S);
    }

    public User getUserOrNull(String licenseId) {
        Optional<User> opt = userRepository.findById(licenseId);
        return opt.orElse(null);
    }

    public List<Role> getUserRolesOrNull(String licenseId){
        User user = getUserOrNull(licenseId);
        if (user != null)
            return user.getRoles();
        else
            return null;
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public void delete(User user){
        if (user != null)
            userRepository.delete(user);
    }
}