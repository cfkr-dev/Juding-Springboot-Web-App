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
     * Retrieves how many participants are enrolled in a competition.
     *
     * @param competition Competition
     * @return Number of participants
     */
    public int countParticipants(Competition competition) {
        int count = 16;
        for (Fight f : fightRepository.getNullFights(competition)) {
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
        return fightRepository.countByCompetitionAndLevelInTreeAndWinnerOrLoser(competition, 3, user) > 0;
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
}
