package es.dawgroup2.juding.users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByNickname(String nickname);

    List<User> findByWeightBetween(int min, int max);
}
