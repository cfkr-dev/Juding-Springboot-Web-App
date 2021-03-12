package es.dawgroup2.juding.competitions;

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

import javax.mail.Multipart;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Controller
public class CompetitionController {


    @Autowired
    CompetitionService competitionService;

    @GetMapping("/admin/competition/list")
    public String competitionList(Model model) {
        List<Competition> competitionList = competitionService.findAll();
        model.addAttribute("competitionList", competitionList);
        return "/admin/competition/list";
    }

    @GetMapping("/admin/competition/edit/{idCompetition}")
    public String editCompetition(@PathVariable String idCompetition, Model model) {
        Competition competition = competitionService.findById(idCompetition);
        String status = competition.translatingRefereeStatus(competition.getRefereeStatus());
        model.addAttribute("status",status);
        model.addAttribute("competition", competition);
        return "/admin/competition/edit";
    }

    @PostMapping("/admin/competition/edit")
    public String updatingCompetitionInfo(@RequestParam String idCompetition, @RequestParam String shortName, @RequestParam String additionalInfo, @RequestParam int minWeight, @RequestParam int maxWeight, @RequestParam Timestamp startDate, @RequestParam Timestamp endDate, @RequestParam String referee, @RequestParam String refereeStatus, MultipartFile imageFile) throws IOException, SQLException {
        Competition competition = competitionService.findById(idCompetition);
        if (!imageFile.isEmpty()) {
            competition.setImageFile(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
        } else {
            if (competition.getImageFile() != null)
                competition.setImageFile(BlobProxy.generateProxy(competition.getImageFile().getBinaryStream(), competition.getImageFile().length()));
        }
        int status= competition.encodeRefereeStatus(refereeStatus);
        competition.setIdCompetition(Integer.parseInt(idCompetition));
        competition.setShortName(shortName);
        competition.setAdditionalInfo(additionalInfo);
        competition.setMinWeight(minWeight);
        competition.setMaxWeight(maxWeight);
        competition.setReferee(referee);
        competition.setRefereeStatus(status);
        competition.setStartDate(startDate);
        competition.setEndDate(endDate);
        competitionService.updatingInfoCompetition(competition);
        return "redirect:/admin/competition/list";

    }

    @GetMapping("/admin/competition/deleteCompetition/{idCompetition}")
    public String showCompetitionToDelete(Model model, @PathVariable String idCompetition) {
        Competition competition = competitionService.findById(idCompetition);
        model.addAttribute("competition", competition);
        competitionService.deleteById(idCompetition);
        return "redirect:/admin/competition/list";
    }

    @GetMapping("/admin/competition/newCompetition")
    public String newCompetition(Model model) {
        return "/admin/competition/newCompetition";
    }

    @PostMapping("/admin/competition/newCompetition")
    public String addACompetition(Competition competition,@RequestParam String status, @RequestParam MultipartFile imageFile) throws IOException {
        if (!imageFile.isEmpty()) {
            competition.setImageFile(BlobProxy.generateProxy(imageFile.getInputStream(), imageFile.getSize()));
        }
        int refereeStatus= competition.encodeRefereeStatus(status);
        competition.setRefereeStatus(refereeStatus);
        competitionService.add(competition);
        return "redirect:/admin/competition/list";
    }

    @GetMapping("/competition/detail/{idCompetition}")
    public String showCompetition(Model model, @PathVariable String idCompetition) {
        Competition competition = competitionService.findById(idCompetition);
        String state = competition.translatingDates(competition.getStartDate(), competition.getEndDate());
        model.addAttribute("state", state);
        model.addAttribute("competition", competition);
        return "/competition/detail";
    }

    @GetMapping("/image/{idCompetition}")
    public ResponseEntity<Object> downloadImage(@PathVariable String idCompetition) throws SQLException {
        Competition competition = competitionService.findById(idCompetition);
        if (competition.getImageFile() != null) {

            Resource file = new InputStreamResource(competition.getImageFile().getBinaryStream());

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .contentLength(competition.getImageFile().length()).body(file);

        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /*@PostMapping("/admin/competition/edit")
    public String updatingCompetitionInfo(Competition competition){
        competitionService.updatingInfoCompetition(competition);
        return "redirect:/admin/competition/list";
    }*/

}
