package es.dawgroup2.juding.main;

import es.dawgroup2.juding.auxTypes.attendances.Attendance;
import es.dawgroup2.juding.auxTypes.belts.Belt;
import es.dawgroup2.juding.auxTypes.gender.Gender;
import es.dawgroup2.juding.auxTypes.roles.Role;
import es.dawgroup2.juding.competitions.Competition;
import es.dawgroup2.juding.competitions.CompetitionService;
import es.dawgroup2.juding.main.image.ImageService;
import es.dawgroup2.juding.users.User;
import es.dawgroup2.juding.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class DataLoader {

    private List<User> competitors;
    private List<Competition> competitions;

    @Autowired
    UserService userService;

    @Autowired
    CompetitionService competitionService;

    @Autowired
    DateService dateService;

    @Autowired
    ImageService imageService;

    @PostConstruct
    public void dataLoader() throws IOException, ParseException {
        competitors = new ArrayList<>();
        for (int i = 1; i <= 16; i++) {
            competitors.add(new User(
                    Integer.toString(1234567890 + i),
                    "Nombre" + i,
                    "Apellido" + i,
                    (i % 2 == 0) ? Gender.H : Gender.M,
                    912345670 + i,
                    "correo" + i + "@correo.com",
                    dateService.stringToDate(i + "/03/2000"),
                    (1234567 + i) + "Z",
                    "competidor" + i,
                    "competidor" + i,
                    "Competidor?",
                    "Competidor",
                    imageService.uploadProfileImage("/static/1234567890.jpg"),
                    Belt.values()[i - 1],
                    "Gimnasio",
                    i * 10,
                    null,
                    Set.of(Role.C))
            );
        }

        userService.saveAll(competitors);

        User referee = new User(
                "JU-9876543211",
                "Nombre Árbitro1",
                "Apellido Árbitro1",
                Gender.H,
                912378456,
                "arbitro@arbitro.com",
                dateService.stringToDate("1/1/2000"),
                "94531234S",
                "arbitro1",
                "arbitro1",
                "Arbitro?",
                "Arbitro",
                imageService.uploadProfileImage("/static/1234567891.jpg"),
                Belt.N9,
                "Gimnasio",
                170,
                null,
                Set.of(Role.R));
        userService.save(referee);

        competitions = new ArrayList<>();
        Competition trial1 = new Competition(
                "Competición de pruebas 1",
                "Datos adicionales 1",
                10,
                160,
                dateService.stringToTimestamp("20/03/2021 12:00"),
                dateService.stringToTimestamp("22/03/2021 19:00"),
                referee,
                Attendance.N,
                "/static/ey.jpg"
        );
        competitionService.add(trial1);
        competitions.add(trial1);

        for (int i = 0; i < 16; i++){
            competitionService.joinCompetition(competitions.get(0), competitors.get(i));
        }
        competitionService.add(competitions.get(0));
    }

}
