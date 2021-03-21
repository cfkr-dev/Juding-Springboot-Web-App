package es.dawgroup2.juding.users;

import es.dawgroup2.juding.auxTypes.roles.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChartController {
    @Autowired
    UserService userService;

    /**
     * Returns the required information for building competitor charts.
     * It is requested by an asynchronous request and JavaScript process it into the browser.
     *
     * @param licenseId License ID of competitor.
     * @return Information for charts in a list.
     */
    @GetMapping("/myCharts")
    public List<Integer> competitorChartInformation(@RequestParam String licenseId) {
        User user = userService.getUserOrNull(licenseId);
        if (user != null)
            if (user.isRole(Role.C))
                return user.getCompetitorMedals();
        return null;
    }
}
