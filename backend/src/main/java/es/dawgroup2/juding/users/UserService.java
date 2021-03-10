package es.dawgroup2.juding.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /*
     * FETCHING USERS BY THEIR ROLE
     */
    public List<User> getCompetitors(){
        return userRepository.findByRole(1);
    }

    public List<User> getActiveReferees(){
        return userRepository.findByRoleAndRefereeRangeGreaterThanEqual(2, 1);
    }

    public List<User> getRefereeApplications(){
        return userRepository.findByRoleAndRefereeRange(2, 0);
    }

    public int refereePendingApplications(){
        return userRepository.countUsersByRoleAndRefereeRange(2, 0);
    }
}
