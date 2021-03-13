package es.dawgroup2.juding.competitions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompetitionService {

    @Autowired
    private CompetitionRepository competitionRepository;

    /**
     * Deletes a competition by its id
     * @param idCompetition Id of the competition
     */
    public void deleteById(String idCompetition) {
        competitionRepository.deleteById(Integer.parseInt(idCompetition));
    }

    /**
     * Finds a competition by its id
     * @param idCompetition Id of the competition
     * @return A competition
     */
    public Competition findById(String idCompetition) {
        return competitionRepository.findById(Integer.parseInt(idCompetition)).orElseThrow();
    }

    /**
     * Finds all competitions that exist
     * @return List of competitions
     */
    public List<Competition> findAll() {
        return competitionRepository.findAll();
    }

    /**
     * Updates the parameters of a competition
     * @param competition A competition
     */
    public void updatingInfoCompetition(Competition competition) {
        competitionRepository.findById(competition.getIdCompetition()).orElseThrow();
        competitionRepository.save(competition);
    }

    /**
     * Adds a new competition
     * @param competition A competition
     */
    public void add(Competition competition) {
        competitionRepository.save(competition);
    }
}
