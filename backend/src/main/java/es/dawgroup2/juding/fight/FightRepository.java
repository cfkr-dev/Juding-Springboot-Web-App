package es.dawgroup2.juding.fight;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FightRepository extends JpaRepository<Fight,Integer> {
    List<Fight> findByIdCompetition(String idCompetition);
}
