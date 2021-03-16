package es.dawgroup2.juding.fight;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FightService {
    @Autowired
    FightRepository fightRepository;

    /**
     * Finds all the fights
     *
     * @return List of the fight
     */
    public List<Fight> findAll() {
        return fightRepository.findAll();
    }

    /**
     * Finds the fight by its id
     *
     * @param idFight Id of the fight
     * @return The fight
     */
    public Fight findById(int idFight) {
        return fightRepository.findById(idFight).orElseThrow();
    }

    /**
     * Saves a new fight and retrieves it.
     *
     * @param fight Fight to be saved.
     * @return Saved fight.
     */
    public Fight save(Fight fight) {
        return fightRepository.save(fight);
    }

    /**
     * Saves new fights and retrieves them.
     *
     * @param fights Fights to be saved.
     * @return Saved fights.
     */
    public List<Fight> saveAll(List<Fight> fights) {
        return fightRepository.saveAll(fights);
    }

    /**
     * Deletes a fight by its id
     *
     * @param idFight Id of the fight
     */
    public void deleteById(int idFight) {
        fightRepository.deleteById(idFight);
    }

    /**
     * Finds all the fights belonging to the same Competition
     *
     * @param idCompetition Id of the competition
     * @return List of fights
     */
    public List<Fight> findByIdCompetition(String idCompetition) {
        return fightRepository.findByIdCompetition(idCompetition);
    }

    public void deleteAll(List<Fight> fights) {
        fightRepository.deleteAll(fights);
    }

}
