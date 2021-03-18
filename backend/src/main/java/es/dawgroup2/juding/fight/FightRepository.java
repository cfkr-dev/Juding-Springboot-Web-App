package es.dawgroup2.juding.fight;

import es.dawgroup2.juding.competitions.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FightRepository extends JpaRepository<Fight,Integer> {
    List<Fight> findByCompetition(Competition idCompetition);


}
