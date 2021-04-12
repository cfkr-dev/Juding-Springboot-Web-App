package es.dawgroup2.juding.users.rest;

import es.dawgroup2.juding.auxTypes.roles.Role;
import es.dawgroup2.juding.users.User;
import es.dawgroup2.juding.users.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ChartRestController {
    @Autowired
    UserService userService;

    /**
     * Returns the required information for building competitor charts FOR WEB BROWSER.
     * It is requested by an asynchronous request and JavaScript process it into the browser.
     *
     * @param licenseId License ID of competitor.
     * @return Information for charts in a list.
     */
    @Operation(summary = "Returns the required information for building competitor charts FOR REST API.", description = "It has to be in a different method because of JWT authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Information for charts in a list.",
                    content = {@Content(mediaType = "text/plain",
                            array = @ArraySchema(schema = @Schema(type = "integer")))}),
            @ApiResponse(responseCode = "403", description = "Not allowed (there is not logged in user).",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not able to return chart info (user is not a competitor, so it doesn't have chart information).",
                    content = @Content)
    })
    @GetMapping("/myCharts")
    public ResponseEntity<List<Integer>> competitorChartInformation(@Parameter(description = "License ID of competitor.") @RequestParam String licenseId) {
        User user = userService.getUserOrNull(licenseId);
        if (user != null)
            if (user.isRole(Role.C))
                return ResponseEntity.ok(user.getCompetitorMedals());
        return ResponseEntity.notFound().build();
    }

    /**
     * Returns the required information for building competitor charts FOR REST API.
     * It is requested by an asynchronous request and JavaScript process it into the browser.
     *
     * @return Information for charts in a list.
     */
    @Operation(summary = "Returns the required information for building competitor charts FOR REST API.", description = "It has to be in a different method because of JWT authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Information for charts in a list.",
                    content = {@Content(mediaType = "text/plain",
                            array = @ArraySchema(schema = @Schema(type = "integer")))}),
            @ApiResponse(responseCode = "403", description = "Not allowed (there is not logged in user).",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Not able to return chart info (user is not a competitor, so it doesn't have chart information).",
                    content = @Content)
    })
    @GetMapping("/api/me/myCharts")
    public List<Integer> competitorRestChartInformation(HttpServletRequest request) {
        User user = userService.findByNickname(request.getUserPrincipal().getName());
        if (user != null)
            if (user.isRole(Role.C))
                return user.getCompetitorMedals();
        return null;
    }
}
