package es.dawgroup2.juding.competitions;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompetitionRepository extends JpaRepository<Competition, Integer> {
}
