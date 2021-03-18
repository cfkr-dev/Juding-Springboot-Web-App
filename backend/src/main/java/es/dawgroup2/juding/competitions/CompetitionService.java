package es.dawgroup2.juding.competitions;

import es.dawgroup2.juding.fight.Fight;
import es.dawgroup2.juding.fight.FightService;
import es.dawgroup2.juding.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompetitionService {

    @Autowired
    private CompetitionRepository competitionRepository;

    @Autowired
    FightService fightService;

    /**
     * Deletes a competition by its id
     *
     * @param idCompetition Id of the competition
     */
    public void deleteById(String idCompetition) {
        competitionRepository.deleteById(Integer.parseInt(idCompetition));
    }

    /**
     * Finds a competition by its id
     *
     * @param idCompetition Id of the competition
     * @return A competition
     */
    public Competition findById(int idCompetition) {
        Optional<Competition> comp = competitionRepository.findById(idCompetition);
        return comp.orElse(null);
    }

    /**
     * Finds all competitions that exist
     *
     * @return List of competitions
     */
    public List<Competition> findAll() {
        return competitionRepository.findAll();
    }

    /**
     * Updates the parameters of a competition
     *
     * @param competition A competition
     */
    public void updatingInfoCompetition(Competition competition) {
        competitionRepository.findById(competition.getIdCompetition()).orElseThrow();
        competitionRepository.save(competition);
    }

    /**
     * Adds or updates a competition.
     * If competition did not exist previously (it's a new competition), this algorithm creates all the necessary fights.
     *
     * @param competition The created/updated competition
     */
    public Competition add(Competition competition) {
        if (!competitionRepository.existsById(competition.getIdCompetition())) {
            competitionRepository.save(competition);
            List<Fight> fights = new ArrayList<>();

            // STEP 1: Generating new fights from root to leaves (final fight to 8th-fights)
            // 1.1. Creating final fight, persisting it and saving it into fights list
            Fight finalFight = new Fight(competition, 0, null, null, null, false, null, null);
            fightService.save(finalFight);
            fights.add(finalFight);

            // 1.2. Creating semifinal fights, relation with finalFight (these ones are children of final), persisting them and saving them into fights list
            List<Fight> semifinals = new ArrayList<>();
            for (int i = 0; i <= 1; i++)
                semifinals.add(new Fight(competition, 1, null, null, finalFight, false, null, null));
            fightService.saveAll(semifinals);
            fights.addAll(semifinals);

            // 1.3. Creating quarterfinal fights, relation with their semifinals, persisting them and saving them into fights list
            List<Fight> quarterfinals = new ArrayList<>();
            for (int i = 0; i <= 3; i++)
                quarterfinals.add(new Fight(competition, 2, null, null, semifinals.get(i / 2), false, null, null));
            fightService.saveAll(quarterfinals);
            fights.addAll(quarterfinals);

            // 1.4. Creating eigthth-final fights, relation with their quarterfinals, persisting them and saving them into fights list
            List<Fight> eighthfinals = new ArrayList<>();
            for (int i = 0; i <= 7; i++)
                eighthfinals.add(new Fight(competition, 3, null, null, quarterfinals.get(i / 2), false, null, null));
            fightService.saveAll(eighthfinals);
            fights.addAll(eighthfinals);

            // STEP 2: Attaching the list of fights into Competition object
            competition.setFights(fights);

            // STEP 3: Including fights relations from leaves to root (8th-finals to final fight)
            // 3.1. Quarterfinals introducing 8th-finals pointers for up and down fight
            int j = 0;
            for (int i = 0; i <= 3; i++) {
                quarterfinals.get(i).setUpFight(eighthfinals.get(j)).setDownFight(eighthfinals.get(j + 1));
                j = j + 2;
            }
            // 3.2. Semifinals introducing quarterfinals pointers for up and down fight
            j = 0;
            for (int i = 0; i <= 1; i++) {
                semifinals.get(i).setUpFight(quarterfinals.get(j)).setDownFight(quarterfinals.get(j + 1));
                j = j + 2;
            }
            // 3.3. Final introducing semifinals pointers
            finalFight.setUpFight(semifinals.get(0)).setDownFight(semifinals.get(1));

            // STEP 4: Persisting all fights and competitions with their updates.
            fightService.saveAll(fights);
            return competitionRepository.save(competition); // does not work ("unable to merge BLOB data" exception).
//            Optional<Competition> comp = competitionRepository.findById(competition.getIdCompetition());
//            return comp.map(value -> competitionRepository.save(value.setFights(fights))).orElse(null);
        } else
            return competitionRepository.save(competition);
    }

    /**
     * A competitor joins a competition
     *
     * @param competition Competition to be joined
     * @param user        Competitor joining
     */
    public boolean joinCompetition(Competition competition, User user) {
        // Competition must have not started
        if (new Timestamp(System.currentTimeMillis()).compareTo(competition.getStartDate()) < 0) {
            // It is known that getFights() saves a tree ADT into an array like:
            // 0 -> final fight, 1-2 -> semifinals, 3-6 -> quarterfinals, 7-14 -> 8th-finals
            List<Fight> fights = competition.getFights();
            for (int i = 7; i <= 14; i++) {
                if (fights.get(i).getWinner() == null) {
                    // Save and go
                    fights.get(i).setWinner(user);
                    return true;
                }
                if (fights.get(i).getLoser() == null) {
                    fights.get(i).setLoser(user);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * A referee registers winner and loser of a fight
     */
    public void fightFinished(Competition competition, User winner, User loser) {
        // 1. Looking for the fight that contains both winner and loser
        for (Fight f : competition.getFights()) {
            // Only looking at not-completed fights
            if (!f.isFinished()) {
                if ((f.getLoser() == loser && f.getWinner() == winner) || (f.getWinner() == loser && f.getLoser() == winner)) {
                    // Setting new values
                    f.setFinished(true).setWinner(winner).setLoser(loser);
                    // If it is last-level fight, it's time to save first, second and thirds medals.
                    if (f.getLevelInTree() == 0) {
                        // Winner of this fight is gold medal
                        winner.addPoints(3);
                        // Loser of this fight is silver medal
                        loser.addPoints(2);
                        // Other participants of semifinals are bronze medals
                        competition.getFights().get(1).getLoser().addPoints(1);
                        competition.getFights().get(2).getLoser().addPoints(1);
                    } else {
                        // Now, it is necessary to find next level fight which
                        for (Fight f2 : competition.getFights()) {
                            if (f2.getLevelInTree() == f.getLevelInTree() - 1) {
                                if (f2.getUpFight() == f || f2.getDownFight() == f) {
                                    if (f2.getLoser() == null) f2.setLoser(winner);
                                    else f2.setWinner(winner);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
