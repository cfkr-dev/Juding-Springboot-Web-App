package es.dawgroup2.juding.competitions;

import es.dawgroup2.juding.fight.Fight;
import es.dawgroup2.juding.fight.FightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Competition findById(String idCompetition) {
        return competitionRepository.findById(Integer.parseInt(idCompetition)).orElseThrow();
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
    public void add(Competition competition) {
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
            }
            // 3.3. Final introducing semifinals pointers
            finalFight.setUpFight(semifinals.get(0)).setDownFight(semifinals.get(1));

            // STEP 4: Persisting all fights and competitions with their updates.
            fightService.saveAll(fights);
            // competitionRepository.save(competition) does not work ("unable to merge BLOB data" exception).
            Optional<Competition> comp = competitionRepository.findById(competition.getIdCompetition());
            comp.ifPresent(c -> competitionRepository.save(c.setFights(fights)));
        } else
            competitionRepository.save(competition);
    }
}
