package es.dawgroup2.juding.fight;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FightService {
    @Autowired
    FightRepository fightRepository;

    /**
     * Finds all the fights
     * @return List of the fight
     */
    public List<Fight> findAll() {
        return fightRepository.findAll();
    }

    /**
     * Finds the fight by its id
     * @param idFight Id of the fight
     * @return The fight
     */
    public Fight findById(int idFight) {
        return fightRepository.findById(idFight).orElseThrow();
    }

    /**
     * Deletes a fight by its id
     * @param idFight Id of the fight
     */
    public void deleteById(int idFight) {
        fightRepository.deleteById(idFight);
    }

}
