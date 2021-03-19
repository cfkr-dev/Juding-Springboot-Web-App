package es.dawgroup2.juding.users;

import es.dawgroup2.juding.auxTypes.roles.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChartController {
    @Autowired
    UserService userService;

    @GetMapping("/myCharts")
    public List<Integer> competitorChartInformation(@RequestParam String licenseId) {
        User user = userService.getUserOrNull(licenseId);
        if (user != null)
            if (user.isRole(Role.C))
                return user.getCompetitorMedals();
        return null;
    }
}
