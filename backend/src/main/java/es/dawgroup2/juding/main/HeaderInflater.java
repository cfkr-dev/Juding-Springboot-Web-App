package es.dawgroup2.juding.main;

import es.dawgroup2.juding.auxTypes.roles.Role;
import es.dawgroup2.juding.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
public class HeaderInflater {

    private static final ArrayList<String> OWN_CSS = new ArrayList<>(
            Arrays.asList("adminScreen",
                    "beltAssignations",
                    "bootstrapAccomodations",
                    "competitionController",
                    "competitionScreen",
                    "header",
                    "juridicTexts",
                    "loginAndRegistration",
                    "news",
                    "post",
                    "profiles",
                    "responsiveTable",
                    "securityQuestion",
                    "style")
    );

    @Autowired
    UserService userService;


    /**
     * Retrieves the attributes for the common header for all logged-in user pages.
     *
     * @param title   Title of the page.
     * @param request HTTP Servlet Request object (for getting the CSRF token).
     * @param css     CSS styles to be applied in page.
     * @return String with the header ready for templating.
     */
    public Map<String, Object> getHeader(String title, HttpServletRequest request, String... css) {
        Map<String, Object> map = new HashMap<>();
        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        List<String> cssList = new ArrayList<>();
        for (String s : css) {
            if (OWN_CSS.contains(s))
                cssList.add("/assets/css/" + s + ".css");
            else
                cssList.add("/assets/vendor/" + s);
        }
        map.put("header-title", title);
        map.put("header-token", csrfToken.getToken());
        map.put("header-isAdmin", userService.findByNickname(request.getUserPrincipal().getName()).getRoles().contains(Role.A));
        map.put("header-css", cssList);
        return map;
    }
}
