package es.dawgroup2.juding.main;

import es.dawgroup2.juding.auxTypes.roles.Role;
import es.dawgroup2.juding.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class HeaderInflater {

    @Autowired
    UserService userService;

    private static final ArrayList<String> OWN_CSS = new ArrayList<>(
            Arrays.asList("adminScreen",
                    "beltAssignations",
                    "bootstrapAccomodations",
                    "competitionController",
                    "competitionScreeen",
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

    public String getHeader(String title, HttpServletRequest request, String... css) {
        StringBuilder sb = new StringBuilder();
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        sb.append("<!DOCTYPE html>\n" +
                "<html lang=\"es\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta content=\"width=device-width, initial-scale=1.0\" name=\"viewport\">\n" +
                "\n" +
                "    <title>")
                .append(title)
                .append(" - Juding</title>\n" +
                        "\n" +
                        "    <!-- Favicons -->\n" +
                        "    <link href=\"/assets/img/hero-img.png\" rel=\"icon\">\n" +
                        "\n" +
                        "    <!-- CSS Files -->\n");
        for (String s : css) {
            if (OWN_CSS.contains(s)) {
                sb.append("<link href=\"/assets/css/").append(s).append(".css\" rel=\"stylesheet\">\n");
            } else {
                sb.append("<link href=\"/assets/vendor/").append(s).append("\" rel=\"stylesheet\">\n");
            }
        }
        sb.append(
                "</head>\n" +
                        "<body>\n" +
                        "<!-- Navigation -->\n" +
                        "<header class=\"header fixed-top\" id=\"header\">\n" +
                        "    <div class=\"container-fluid container-xl d-flex align-items-center justify-content-between\">\n" +
                        "        <a class=\"logo d-flex align-items-center\" href=\"/myHome\">\n" +
                        "            <img alt=\"\" src=\"/assets/img/hero-img.png\">\n" +
                        "            <span>Juding</span>\n" +
                        "        </a>\n" +
                        "        <nav class=\"navbar\" id=\"navbar\">\n" +
                        "            <ul>\n" +
                        "                <li><a class=\"nav-link scrollto\" href=\"/myHome\">Inicio</a></li>\n" +
                        "                <li><a class=\"nav-link scrollto\" href=\"/ranking\">Ranking</a></li>\n" +
                        "                <li><a class=\"nav-link scrollto\" href=\"/myProfile\">Mi perfil</a></li>\n");
        if (userService.findByNickname(request.getUserPrincipal().getName()).getRoles().contains(Role.A)) {
            sb.append("                <li class=\"dropdown\">\n" +
                    "                    <a href=\"#\"><span>Administración</span> <i class=\"far fa-chevron-down\"></i></a>\n" +
                    "                    <ul>\n" +
                    "                        <li class=\"dropdown\">\n" +
                    "                            <a href=\"#\"><span>Usuarios</span> <i class=\"far fa-chevron-right\"></i></a>\n" +
                    "                            <ul>\n" +
                    "                                <li><a href=\"/admin/user/list/competitors\">Competidores</a></li>\n" +
                    "                                <li><a href=\"/admin/user/list/referees\">Árbitros</a></li>\n" +
                    "                            </ul>\n" +
                    "                        <li><a href=\"/admin/competition/list\">Competiciones</a></li>\n" +
                    "                        <li><a href=\"/admin/post/list\">Noticias</a></li>\n" +
                    "                    </ul>\n" +
                    "                </li>");
        }
        sb.append(
                "                <li>\n" +
                        "                    <form action=\"/logout\" method=\"post\">\n" +
                        "                        <input type=\"hidden\" name=\"_csrf\" value=\"")
                .append(token.getToken())
                .append("\">\n" +
                        "                        <button class=\"login scrollto\">Cerrar sesión</button>\n" +
                        "                    </form>\n" +
                        "                </li>\n" +
                        "            </ul>\n" +
                        "            <i class=\"far fa-bars mobile-nav-toggle\"></i>\n" +
                        "        </nav><!-- .navbar -->\n" +
                        "    </div>\n" +
                        "</header>");
        return sb.toString();
    }
}
