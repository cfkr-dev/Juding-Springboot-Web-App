package es.dawgroup2.juding.competitions;

import es.dawgroup2.juding.auxTypes.roles.Role;
import es.dawgroup2.juding.fight.Fight;
import es.dawgroup2.juding.fight.FightService;
import es.dawgroup2.juding.main.DateService;
import es.dawgroup2.juding.users.User;
import es.dawgroup2.juding.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompetitionService {

    @Autowired
    FightService fightService;
    @Autowired
    UserService userService;
    @Autowired
    DateService dateService;
    @Autowired
    private CompetitionRepository competitionRepository;

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
     * Get the current competitions for user
     *
     * @param user User
     * @return List of current competitions
     */
    public List<Competition> getCurrentCompetitions(User user) {
        if (user.isRole(Role.C)) {
            List<Competition> output = new ArrayList<>();
            int minWeight = (int) Math.floor((double) user.getWeight() / 10) * 10;
            int maxWeight = (user.getWeight() % 10 == 0) ? user.getWeight() + 10 : (int) (Math.ceil((double) user.getWeight() / 10)) * 10;
            List<Competition> allCurrents = competitionRepository.findByStartDateBeforeAndEndDateAfterAndMinWeightGreaterThanEqualAndMaxWeightGreaterThanEqual(new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), minWeight, maxWeight);
            for (Competition c : allCurrents) {
                if (fightService.checkParticipation(c, user))
                    output.add(c);
            }
            return output;
        } else if (user.isRole(Role.R)) {
            return competitionRepository.findByStartDateBeforeAndEndDateAfterAndReferee(new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), user);
        }
        return null;
    }

    /**
     * Get the future competitions for the specifications of a user
     *
     * @param user    User
     * @param control True if including user competitions, false if including the opposite set.
     * @return List of competitions
     */
    public List<Competition> getFutureFights(User user, boolean control) {
        if (user.isRole(Role.C)) {
            int minWeight = (int) Math.floor((double) user.getWeight() / 10) * 10;
            int maxWeight = (user.getWeight() % 10 == 0) ? user.getWeight() + 10 : (int) (Math.ceil((double) user.getWeight() / 10)) * 10;
            List<Competition> output = new ArrayList<>();
            for (Competition c : competitionRepository.findByStartDateAfterAndMinWeightGreaterThanEqualAndMaxWeightGreaterThanEqual(new Timestamp(System.currentTimeMillis()), minWeight, maxWeight)) {
                if (fightService.checkParticipation(c, user) == control)
                    output.add(c);
            }
            return output;
        } else if (user.isRole(Role.R)) {
            return competitionRepository.findByStartDateAfterAndReferee(new Timestamp(System.currentTimeMillis()), user);
        }
        return null;
    }

    /**
     * Get the past competitions for the specified user (either competitors or referees)
     *
     * @param user User
     * @return List of past competitions.
     */
    public List<Competition> getPastFights(User user) {
        if (user.isRole(Role.C)) {
            List<Competition> output = new ArrayList<>();
            for (Competition c : competitionRepository.findByEndDateBefore(new Timestamp(System.currentTimeMillis()))) {
                if (fightService.checkParticipation(c, user))
                    output.add(c);
            }
            return output;
        } else if (user.isRole(Role.R))
            return competitionRepository.findByEndDateBeforeAndReferee(new Timestamp(System.currentTimeMillis()), user);
        return null;
    }

    /**
     * Returns a page of competitions (each page contains 10 elements).
     *
     * @param num Number of page
     * @return Page
     */
    public Page<Competition> getCompetitionsInPages(int num) {
        return competitionRepository.findAll(PageRequest.of(num, 10));
    }

    /**
     * If competition did not exist previously creates a new competition, else updates it with the new parameters
     * @param idCompetition Id of the competition
     * @param shortName  The short name of the competition
     * @param additionalInfo Info about the competition
     * @param minWeight The minimum weight allowed in a competition
     * @param maxWeight The maximum weight allowed in a competition
     * @param startDate The start date of a competition
     * @param endDate The end date of a competition
     * @param referee The license of the referee in charge of the competition
     * @return The saved competition
     */
    public Competition save(String idCompetition, String shortName, String additionalInfo, int minWeight, int maxWeight, String startDate, String endDate, String referee) {
        Competition competition;
        try {
            competition= (idCompetition == null) ? new Competition() : competitionRepository.findById(Integer.parseInt(idCompetition)).orElseThrow();
            competition.setShortName(shortName)
                    .setAdditionalInfo(additionalInfo)
                    .setMinWeight(minWeight)
                    .setMaxWeight(maxWeight)
                    .setStartDate(dateService.stringToTimestamp(startDate))
                    .setEndDate(dateService.stringToTimestamp(endDate))
                    .setReferee(userService.getUserOrNull(referee));
        } catch (Exception e){
            return null;
        }
        if (competition.getIdCompetition() == 0) {
            competitionRepository.save(competition);
            generateNewFights(competition);
        }
        return competitionRepository.save(competition);
    }

    /**
     * Adds or updates a competition.
     * If competition did not exist previously (it's a new competition), this algorithm creates all the necessary fights.
     *
     * @param competition The created/updated competition
     */
    public Competition save(Competition competition) {
        if (!competitionRepository.existsById(competition.getIdCompetition())) {
            competitionRepository.save(competition);
            generateNewFights(competition);
        }
        return competitionRepository.save(competition);
    }

    /**
     *  Creates all the necessary fights of a new competition
     * @param competition A competition
     */
    private void generateNewFights(Competition competition){
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
    }

    /**
     * A competitor joins a competition.
     *
     * @param competition Competition to be joined
     * @param user        Competitor joining
     * @return True if joining was successful, false if not.
     */
    public boolean joinCompetition(Competition competition, User user) {
        if (competition != null && user != null) {
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
        }
        return false;
    }

    /**
     * This method registers in database the result of a fight given its context competition, the winner and the loser of the fight.
     * Automatically, winner is inserted into next-level fight.
     * If the finished fight was the last one, it also adds points for the best 4 users in the fight.
     *
     * @param competition ID of the competition.
     * @param winner      Winner of the fight.
     * @param loser       Loser of the fight.
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
                        userService.save(winner);
                        // Loser of this fight is silver medal
                        loser.addPoints(2);
                        userService.save(loser);
                        // Other participants of semifinals are bronze medals
                        User third1 = competition.getFights().get(1).getLoser();
                        third1.addPoints(1);
                        userService.save(third1);
                        User third2 = competition.getFights().get(2).getLoser();
                        third2.addPoints(1);
                        userService.save(third2);
                        // The other ones are getting 0 points
                        for (Fight f2 : competition.getFights()) {
                            if (f2.getLevelInTree() == 3) {
                                if (f2.getLoser() != winner && f2.getLoser() != loser && f2.getLoser() != third1 && f2.getLoser() != third2) {
                                    f2.getLoser().addPoints(0);
                                    userService.save(f2.getLoser());
                                }
                                if (f2.getWinner() != winner && f2.getWinner() != loser && f2.getWinner() != third1 && f2.getWinner() != third2) {
                                    f2.getWinner().addPoints(0);
                                    userService.save(f2.getWinner());
                                }
                            }
                        }
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
