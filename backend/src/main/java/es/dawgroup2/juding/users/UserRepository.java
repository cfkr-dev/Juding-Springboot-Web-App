package es.dawgroup2.juding.users;

import es.dawgroup2.juding.auxTypes.refereeRange.RefereeRange;
import es.dawgroup2.juding.auxTypes.roles.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    List<User> findByRolesContaining(Role role);

    Page<User> findByRolesContaining(Role role, Pageable page);

    List<User> findByRolesContainingAndRefereeRangeNot(Role role, RefereeRange refereeRange);

    Page<User> findByRolesContainingAndRefereeRangeNot(Role role, RefereeRange refereeRange, Pageable page);

    List<User> findByRolesContainingAndRefereeRange(Role role, RefereeRange refereeRange);

    List<User> findByDni(String dni);

    Optional<User> findByNickname(String nickname);

    List<User> findByWeightBetween(int min, int max);

    int countUsersByRolesAndRefereeRange(Role role, RefereeRange refereeRange);

    @Query(nativeQuery = true, value = "SELECT u.nickname, SUM(ucm.competitor_medals) AS sum, COUNT(ucm.competitor_medals) AS num, u.license_id, u.name, u.surname, u.dni, u.belt FROM user u JOIN user_competitor_medals ucm ON u.license_id = ucm.user_license_id GROUP BY license_id ORDER BY sum DESC, num DESC")
    List<?> getRanking();
}
