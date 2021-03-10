package es.dawgroup2.juding.users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    List<User> findByRole(int role);

    List<User> findByRoleAndRefereeRangeGreaterThanEqual(int role, int refereeRange);

    List<User> findByRoleAndRefereeRange(int role, int refereeRange);

    Optional<User> findByNickname(String nickname);

    List<User> findByWeightBetween(int min, int max);

    int countUsersByRoleAndRefereeRange(int role, int refereeRange);
}
