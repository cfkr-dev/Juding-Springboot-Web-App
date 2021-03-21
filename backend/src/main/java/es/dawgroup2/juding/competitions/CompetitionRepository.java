package es.dawgroup2.juding.competitions;

import es.dawgroup2.juding.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface CompetitionRepository extends JpaRepository<Competition, Integer> {
    List<Competition> findByEndDateBefore(Timestamp endDate);

    List<Competition> findByEndDateBeforeAndReferee(Timestamp endDate, User referee);

    List<Competition> findByStartDateAfterAndReferee(Timestamp startDate, User referee);

    List<Competition> findByStartDateBeforeAndEndDateAfterAndMinWeightGreaterThanEqualAndMaxWeightGreaterThanEqual(Timestamp startDate, Timestamp endDate, int minWeight, int maxWeight);

    List<Competition> findByStartDateBeforeAndEndDateAfterAndReferee(Timestamp startDate, Timestamp endDate, User referee);

    List<Competition> findByStartDateAfterAndMinWeightGreaterThanEqualAndMaxWeightGreaterThanEqual(Timestamp startDate, int minWeight, int maxWeight);
}
