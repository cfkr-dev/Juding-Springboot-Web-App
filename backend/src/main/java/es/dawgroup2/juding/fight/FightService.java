package es.dawgroup2.juding.fight;

import es.dawgroup2.juding.competitions.Competition;
import es.dawgroup2.juding.users.User;
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
     * Retrieves how many participants are inscribed in a competition
     * @param competition Competition
     * @return Number of participants
     */
    public int countParticipants(Competition competition){
        int count = 16;
        for (Fight f : fightRepository.getNullFights(competition)){
            if (f.getWinner() == null) count--;
            if (f.getLoser() == null) count--;
        }
        return count;
    }

    /**
     * Checks if user has partaken, partakes or will partake in a competition.
     *
     * @param competition Competition
     * @param user        User
     * @return True if partakes, false otherwise.
     */
    public boolean checkParticipation(Competition competition, User user) {
        int a = fightRepository.countByCompetitionAndLevelInTreeAndWinnerOrLoser(competition, 3, user);
        return a > 0;
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
     * @param competition Competition
     * @return List of fights
     */
    public List<Fight> findByIdCompetition(Competition competition) {
        return fightRepository.findByCompetition(competition);
    }

    public void deleteAll(List<Fight> fights) {
        fightRepository.deleteAll(fights);
    }

}
