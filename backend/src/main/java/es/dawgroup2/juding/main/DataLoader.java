package es.dawgroup2.juding.main;

import es.dawgroup2.juding.auxTypes.attendances.Attendance;
import es.dawgroup2.juding.auxTypes.belts.Belt;
import es.dawgroup2.juding.auxTypes.gender.Gender;
import es.dawgroup2.juding.auxTypes.refereeRange.RefereeRange;
import es.dawgroup2.juding.auxTypes.roles.Role;
import es.dawgroup2.juding.competitions.Competition;
import es.dawgroup2.juding.competitions.CompetitionService;
import es.dawgroup2.juding.fight.FightService;
import es.dawgroup2.juding.main.image.ImageService;
import es.dawgroup2.juding.posts.Post;
import es.dawgroup2.juding.posts.PostService;
import es.dawgroup2.juding.users.User;
import es.dawgroup2.juding.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class DataLoader {

    @Autowired
    UserService userService;

    @Autowired
    CompetitionService competitionService;

    @Autowired
    DateService dateService;

    @Autowired
    ImageService imageService;

    @Autowired
    FightService fightService;

    @Autowired
    PostService postService;

    @PostConstruct
    public void dataLoader() throws IOException, ParseException {
        // STEP 1. Creating new competitors
        List<User> competitors = new ArrayList<>();
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
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[i - 1],
                    "Gimnasio",
                    i * 10,
                    null,
                    Set.of(Role.C))
            );
        }
        userService.saveAll(competitors);
        List<User> referees = new ArrayList<>();
        for (int i = 0; i <= 7; i++) {
            referees.add(new User(
                    "JU-987654321" + i,
                    "Nombre Árbitro" + i,
                    "Apellido Árbitro" + i,
                    (i % 2 == 0) ? Gender.H : Gender.M,
                    912378450 + i,
                    "arbitro" + i + "@arbitro.com",
                    dateService.stringToDate(i + "/6/2000"),
                    "9453123" + i + "S",
                    "arbitro" + i,
                    "arbitro" + i,
                    "Arbitro?",
                    "Arbitro",
                    imageService.uploadProfileImage("/static/sampleImages/1234567891.jpg"),
                    Belt.values()[10 + i],
                    null,
                    null,
                    RefereeRange.values()[i % 4],
                    Set.of(Role.R)));
        }
        User admin = new User(
                "JU-9876543231",
                "Nombre Admin1",
                "Apellido Admin1",
                Gender.M,
                912378586,
                "admin@admin.com",
                dateService.stringToDate("5/4/1980"),
                "94531234S",
                "admin",
                "admin",
                "admin?",
                "admin",
                imageService.uploadProfileImage("/static/sampleImages/1234567892.jpg"),
                Belt.N10,
                "Gimnasio",
                80,
                RefereeRange.C,
                Set.of(Role.R, Role.A));
        referees.add(admin);
        userService.saveAll(referees);

        // STEP 2. Saving a new competition
        List<Competition> competitions = new ArrayList<>();
        Competition trial1 = new Competition(
                "Competición de pruebas 1",
                "Datos adicionales 1",
                10,
                160,
                dateService.stringToTimestamp("20/03/2021 12:00"),
                dateService.stringToTimestamp("22/03/2021 19:00"),
                referees.get(0),
                Attendance.N
        );
        competitionService.add(trial1);
        competitions.add(trial1);

        // 3. Joining competitors into competition
        for (int i = 0; i < 16; i++) {
            competitionService.joinCompetition(competitions.get(0), competitors.get(i));
        }

        // 4. Post creation and saving
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(admin,
                "INFORMACIÓN COMPLETA curso de Kata del Club Pandy",
                "Ampliamos la información del Curso de Katas del Club Pandy, con estos documentos y vídeos-explicativos. También aprovechamos para recordar que la entrega de premios del sorteo de la 2ª Jornada se hará el mismo día 21 de marzo a las 11:00.<br>" +
                        "¡Recordad traer la papeleta ganadora!\n" +
                        "<a href=\"https://fmjudo.es/attachments/article/4801/Cto%20katas%20infantiles-3.pdf\">CUADRO DE CATEGORÍAS Y KATAS</a></p>\n" +
                        "<a href=\"https://www.youtube.com/watch?v=LhVvmFDLMo0\">VÍDEO EXPLICATIVO GRUPO 7</a></p>\n" +
                        "<a href=\"https://www.youtube.com/watch?v=VpLdKj5KvuM\">VÍDEO EXPLICATIVO GRUPO 6></a></p>\n" +
                        "<a href=\"https://youtu.be/uvqxiXiA9eM\">Seiryoku-Zenyo-Kokumin-Taiiku</a>\n" +
                        "Además, adjuntamos este documento informativo que complementa a la Circular Nº 27 sobre el próximo curso de Katas del Club Pandy.</p>\n" +
                        "<a href=\"https://fmjudo.es//attachments/article/4188/27%20Reuni%C3%B3n%20Profesores%20y%20Curso%20de%20Kata%20Club%20Pandy%20marzo.pdf\">Circular Nº 27 - 4ª Jornada - ACTIVIDADES DE KATA “CLUB PANDY”</a>\n",
                "/static/sampleImages/judoSample1.jpg",
                new Timestamp(System.currentTimeMillis())));
        postService.addAll(posts);
    }

}
