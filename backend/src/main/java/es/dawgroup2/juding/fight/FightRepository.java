package es.dawgroup2.juding.fight;

import es.dawgroup2.juding.competitions.Competition;
import es.dawgroup2.juding.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FightRepository extends JpaRepository<Fight,Integer> {
    List<Fight> findByCompetition(Competition idCompetition);

    @Query("SELECT f FROM Fight f WHERE f.competition = ?1 AND f.levelInTree = 3 AND (f.winner IS NULL OR f.loser IS NULL)")
    List<Fight> getNullFights(Competition competition);

    @Query("SELECT COUNT(f) FROM Fight f WHERE f.competition = ?1 AND f.levelInTree = ?2 AND (f.winner = ?3 OR f.loser = ?3)")
    int countByCompetitionAndLevelInTreeAndWinnerOrLoser(Competition idCompetition, int levelInTree, User user);
}
