package es.dawgroup2.juding.competitions;

import es.dawgroup2.juding.attendances.Attendance;
import es.dawgroup2.juding.attendances.AttendanceService;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Controller
public class CompetitionController {


    @Autowired
    CompetitionService competitionService;

    @Autowired
    AttendanceService attendanceService;

    /**
     *
     * @param model model of the view
     * @return the view of the competition list
     */
    @GetMapping("/admin/competition/list")
    public String competitionList(Model model) {
        List<Competition> competitionList = competitionService.findAll();
        model.addAttribute("competitionList", competitionList);
        return "/admin/competition/list";
    }

    /**
     *
     * @param idCompetition id of the competition
     * @param model model of the view
     * @return view of the competition to edit
     */
    @GetMapping("/admin/competition/edit/{idCompetition}")
    public String editCompetition(@PathVariable String idCompetition, Model model) {
        Competition competition = competitionService.findById(idCompetition);
        model.addAttribute("competition", competition)
                .addAttribute("attendance", attendanceService.getAttendanceToString(competition.getRefereeStatus()));
        return "/admin/competition/edit";
    }

    /**
     *
     * @param model model of the view
     * @return view of the competition to add
     */
    @GetMapping("/admin/competition/newCompetition")
    public String newCompetition(Model model) {
        return "/admin/competition/newCompetition";
    }

    /**
     *
     * @param model model of the view
     * @param idCompetition id of the competition
     * @return view of the competition screen
     */
    @GetMapping("/competition/detail/{idCompetition}")
    public String showCompetition(Model model, @PathVariable String idCompetition) {
        Competition competition = competitionService.findById(idCompetition);
        String state = competition.translatingDates(competition.getStartDate(), competition.getEndDate());
        model.addAttribute("state", state);
        model.addAttribute("competition", competition);
        return "/competition/detail";
    }

    /**
     * Generates a new competition
     * @param shortName The short name of the competition
     * @param additionalInfo Info about the competition
     * @param minWeight The minimum weight allowed in a competition
     * @param maxWeight The maximum weight allowed in a competition
     * @param startDate The start date of a competition
     * @param endDate The end date of a competition
     * @param referee referee The license of the referee in charge of the competition
     * @param status A String of that show the status of the referee attendance
     * @param imageFile Image that represent the competition
     * @return The new competition
     * @throws IOException
     */
    @PostMapping("/admin/competition/newCompetition")
    public String addACompetition(@RequestParam String shortName,
                                  @RequestParam String additionalInfo,
                                  @RequestParam int minWeight,
                                  @RequestParam int maxWeight,
                                  @RequestParam Timestamp startDate,
                                  @RequestParam Timestamp endDate,
                                  @RequestParam String referee,
                                  @RequestParam String status,
                                  MultipartFile imageFile) throws IOException {
        Competition competition=new Competition();
        competition.setShortName(shortName)
                .setAdditionalInfo(additionalInfo)
                .setMinWeight(minWeight)
                .setMaxWeight(maxWeight)
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setReferee(referee)
                .setRefereeStatus(attendanceService.findAttendanceById(status));
        if (imageFile != null) {
            if (!imageFile.isEmpty()) {
                competition.setImageFile(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
            }
        }
        competitionService.add(competition);
        return "redirect:/admin/competition/list";
    }

    /**
     * Form to edit a competition
     * @param idCompetition Id of a competition
     * @param shortName The name of a competitiom
     * @param additionalInfo Information of a competition
     * @param minWeight The minimum weight allowed in a competition
     * @param maxWeight The maximum weight allowed in a competition
     * @param startDate The start date of a competition
     * @param endDate The end date of a competition
     * @param referee The license of the referee in charge of the competition
     * @param refereeStatus A String of that show the status of the referee attendance
     * @param imageFile Image that represent the competition
     * @return The competition edited
     * @throws IOException
     * @throws SQLException
     */
    @PostMapping("/admin/competition/edit")
    public String updatingCompetitionInfo(@RequestParam String idCompetition,
                                          @RequestParam String shortName,
                                          @RequestParam String additionalInfo,
                                          @RequestParam int minWeight,
                                          @RequestParam int maxWeight,
                                          @RequestParam Timestamp startDate,
                                          @RequestParam Timestamp endDate,
                                          @RequestParam String referee,
                                          @RequestParam String refereeStatus,
                                          MultipartFile imageFile) throws IOException, SQLException {
        Competition competition = competitionService.findById(idCompetition);
        if (imageFile != null) {
            if (!imageFile.isEmpty()) {
                competition.setImageFile(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
            }
        }
        competition.setShortName(shortName)
                .setAdditionalInfo(additionalInfo)
                .setMinWeight(minWeight)
                .setMaxWeight(maxWeight)
                .setReferee(referee)
                .setRefereeStatus(attendanceService.findAttendanceById(refereeStatus))
                .setStartDate(startDate)
                .setEndDate(endDate);
        competitionService.updatingInfoCompetition(competition);
        return "redirect:/admin/competition/list";

    }

    /**
     * This method deletes the competition selected
     * @param model model of the view
     * @param idCompetition id of the competition
     * @return view of the competition list
     */
    @GetMapping("/admin/competition/deleteCompetition/{idCompetition}")
    public String showCompetitionToDelete(Model model, @PathVariable String idCompetition) {
        Competition competition = competitionService.findById(idCompetition);
        model.addAttribute("competition", competition);
        competitionService.deleteById(idCompetition);
        return "redirect:/admin/competition/list";
    }

}
