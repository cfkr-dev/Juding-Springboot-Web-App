package es.dawgroup2.juding.main;

import es.dawgroup2.juding.auxTypes.roles.Role;
import es.dawgroup2.juding.competitions.CompetitionService;
import es.dawgroup2.juding.users.User;
import es.dawgroup2.juding.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/formCheck")
public class FormValidationController {

    @Autowired
    UserService userService;

    @Autowired
    CompetitionService competitionService;


    /**
     * Checks if the data introduced by the user are not duplicated in the database
     * @param licenseId Id of the user
     * @param nickname Nickname of the user
     * @return An integer depending on the use case
     */
    @GetMapping("/signup")
    public int signUp(@RequestParam String licenseId, @RequestParam String nickname) {
        return userService.matchingLicenceOrNickname(licenseId, nickname);
    }

    /**
     * Checks if the data introduced by the user are not duplicated in the database
     * @param licenceId Id of the user
     * @param nickname Nickname of the user
     * @return An integer depending on the use case
     */
    @GetMapping("/update")
    public boolean update(@RequestParam String licenceId, @RequestParam String nickname) {
        return userService.matchingLicenceAndNickname(licenceId, nickname);
    }

    /**
     * Checks if the values introduced by the user are correct
     * @param startDate The start date of a competition
     * @param endDate The end date of a competition
     * @param minWeight MinWeight introduced
     * @param maxWeight MaxWeight introduced
     * @return An integer depending on the use case
     */
    @GetMapping("/checkingNewCompetition")
    public int checkingNewCompetition(@RequestParam String startDate, String endDate, String minWeight, String maxWeight) {
        if (competitionService.checkingMinAndMaxWeight(minWeight, maxWeight)) {
            if (!competitionService.checkingDates(startDate, endDate)) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return competitionService.checkingDates(startDate, endDate) ? 3 : 2;
        }
    }

    /**
     * Checks if the values introduced by the user are correct
     * @param startDate The start date of a competition
     * @param endDate The end date of a competition
     * @param minWeight MinWeight introduced
     * @param maxWeight MaxWeight introduced
     * @return An integer depending on the use case
     */
    @GetMapping("/checkingUpdatedCompetition")
    public int checkingUpdatedCompetitionData(@RequestParam String startDate, String endDate, String minWeight, String maxWeight) {
        if (competitionService.checkingMinAndMaxWeight(minWeight, maxWeight)) {
            if (!competitionService.checkingDatesAlt(startDate, endDate)) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return competitionService.checkingDatesAlt(startDate, endDate) ? 3 : 2;
        }
    }
}