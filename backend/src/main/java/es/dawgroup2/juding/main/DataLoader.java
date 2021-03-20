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
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostConstruct
    public void dataLoader() throws IOException, ParseException {
        // STEP 1. Creating new users
            //Competitors
            List<User> competitors = new ArrayList<>();
            /*for (int i = 1; i <= 16; i++) {
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
                        passwordEncoder.encode("competidor" + i),
                        "Competidor?",
                        "Competidor",
                        imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                        Belt.values()[i - 1],
                        "Gimnasio",
                        i * 10,
                        null,
                        Set.of(Role.C))
                );
            }*/
            competitors.add(new User(
                    "0000000001",
                    "Diego",
                    "Guerrero Carrasco",
                    Gender.H,
                    910000001,
                    "d.carrasco@gmail.com",
                    dateService.stringToDate("30/10/2000"),
                    "0000000001A",
                    "dieguin",
                    passwordEncoder.encode("dieguin"),
                    "¿Cuál es tu asignatura favorita?",
                    "DAW",
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[10],
                    "Gym Olympo",
                    79,
                    null,
                    Set.of(Role.C))
            );

            competitors.add(new User(
                    "0000000002",
                    "José Luis",
                    "Toledano Díaz",
                    Gender.H,
                    910000002,
                    "jl.toledano@gmail.com",
                    dateService.stringToDate("18/09/2000"),
                    "0000000002A",
                    "joselu",
                    passwordEncoder.encode("joselu"),
                    "¿A quién se le ocurrió el nombre de la aplicación?",
                    "A mi",
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[11],
                    "Gym Olympo",
                    77,
                    null,
                    Set.of(Role.C))
            );

            competitors.add(new User(
                    "0000000003",
                    "Ismael",
                    "González Sastre",
                    Gender.H,
                    910000003,
                    "i.gonzalez@gmail.com",
                    dateService.stringToDate("15/04/2000"),
                    "0000000003A",
                    "ismón",
                    passwordEncoder.encode("ismón"),
                    "¿Modo oscuro o modo claro?",
                    "Modo oscuro",
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[4],
                    "Gym Olympo",
                    68,
                    null,
                    Set.of(Role.C))
            );

            competitors.add(new User(
                    "0000000004",
                    "Alberto",
                    "Pérez Pérez",
                    Gender.H,
                    910000004,
                    "a.perez@gmail.com",
                    dateService.stringToDate("21/03/2000"),
                    "0000000004A",
                    "albertimus",
                    passwordEncoder.encode("albertimus"),
                    "¿Modo oscuro o modo claro?",
                    "Modo oscuro",
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[2],
                    "Gym Olympo",
                    71,
                    null,
                    Set.of(Role.C))
            );

            competitors.add(new User(
                    "0000000005",
                    "Jorge",
                    "Adame Prudencio",
                    Gender.H,
                    910000005,
                    "a.prudencio@gmail.com",
                    dateService.stringToDate("23/11/2000"),
                    "0000000005A",
                    "jorgeous",
                    passwordEncoder.encode("jorgeous"),
                    "¿De dónde eres?",
                    "Parla",
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[6],
                    "Katan",
                    69,
                    null,
                    Set.of(Role.C))
            );

            competitors.add(new User(
                    "0000000006",
                    "Flavia",
                    "Vásquez Gutiérrez",
                    Gender.M,
                    910000006,
                    "f.vazquez@gmail.com",
                    dateService.stringToDate("29/11/2000"),
                    "0000000006A",
                    "flafla",
                    passwordEncoder.encode("flafla"),
                    "¿De dónde eres?",
                    "Parla",
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[3],
                    "Katan",
                    62,
                    null,
                    Set.of(Role.C))
            );

            competitors.add(new User(
                    "0000000007",
                    "Jesús",
                    "Tocas Atarama",
                    Gender.H,
                    910000007,
                    "j.tocas@gmail.com",
                    dateService.stringToDate("29/11/1999"),
                    "0000000007A",
                    "Jebas",
                    passwordEncoder.encode("Jebas"),
                    "¿Cómo tienes el pelo?",
                    "Muy largo",
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[9],
                    "Katan",
                    62,
                    null,
                    Set.of(Role.C))
            );

            competitors.add(new User(
                    "0000000008",
                    "Debora",
                    "Israel Villanueva",
                    Gender.M,
                    910000008,
                    "d.israel@gmail.com",
                    dateService.stringToDate("29/11/1999"),
                    "0000000008A",
                    "Debi",
                    passwordEncoder.encode("Debi"),
                    "¿Dónde estudias?",
                    "Universodad",
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[0],
                    "Katan",
                    61,
                    null,
                    Set.of(Role.C))
            );

            competitors.add(new User(
                    "0000000009",
                    "Andrea",
                    "Nuzzi Herrero",
                    Gender.M,
                    910000009,
                    "a.nuzzi@gmail.com",
                    dateService.stringToDate("08/03/2000"),
                    "0000000009A",
                    "Andy",
                    passwordEncoder.encode("Andy"),
                    "¿Qué deporte practicas?",
                    "Judo",
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[12],
                    "JudoLeganes",
                    62,
                    null,
                    Set.of(Role.C))
            );

            competitors.add(new User(
                    "0000000010",
                    "Antonio",
                    "Agudo Esperanza",
                    Gender.H,
                    910000010,
                    "a.agudo@gmail.com",
                    dateService.stringToDate("08/03/2000"),
                    "0000000010A",
                    "Agudino",
                    passwordEncoder.encode("Agudino"),
                    "¿Juego favorito?",
                    "League of legends",
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[16],
                    "JudoLeganes",
                    62,
                    null,
                    Set.of(Role.C))
            );

            competitors.add(new User(
                    "0000000011",
                    "Marcos",
                    "Robles Rodríguez",
                    Gender.H,
                    910000011,
                    "m.robles@gmail.com",
                    dateService.stringToDate("08/03/2000"),
                    "0000000011A",
                    "MarcusMaximo",
                    passwordEncoder.encode("MarcusMaximo"),
                    "¿Qué es mejor el DOTA o el LOL?",
                    "DOTA",
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[20],
                    "JudoLeganes",
                    73,
                    null,
                    Set.of(Role.C))
            );

            competitors.add(new User(
                    "0000000012",
                    "Miriam",
                    "De Francisco Alonso",
                    Gender.M,
                    910000012,
                    "m.defrancisco@gmail.com",
                    dateService.stringToDate("08/03/2000"),
                    "0000000012A",
                    "MiriMiri",
                    passwordEncoder.encode("MiriMiri"),
                    "¿Estilo de música favorito?",
                    "K-pop",
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[1],
                    "JudoLeganes",
                    62,
                    null,
                    Set.of(Role.C))
            );

            competitors.add(new User(
                    "0000000013",
                    "Adrian",
                    "Sierra Robles",
                    Gender.H,
                    910000013,
                    "a.sierra@gmail.com",
                    dateService.stringToDate("08/03/2000"),
                    "0000000013A",
                    "AdriSierras",
                    passwordEncoder.encode("AdriSierras"),
                    "¿Dónde trabajas?",
                    "McDonals",
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[1],
                    "VillaJudo",
                    75,
                    null,
                    Set.of(Role.C))
            );

            competitors.add(new User(
                    "0000000014",
                    "Iván",
                    "Toledano Díaz",
                    Gender.H,
                    910000014,
                    "i.toledano@gmail.com",
                    dateService.stringToDate("22/06/2008"),
                    "0000000014A",
                    "Itodi",
                    passwordEncoder.encode("Itodi"),
                    "¿Cuál es tu hermano favorito?",
                    "José Luis",
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[19],
                    "VillaJudo",
                    61,
                    null,
                    Set.of(Role.C))
            );

            competitors.add(new User(
                    "0000000015",
                    "Fernando",
                    "Sánchez Calvo",
                    Gender.H,
                    910000015,
                    "f.sanchez@gmail.com",
                    dateService.stringToDate("22/06/2008"),
                    "0000000015A",
                    "Ferdinan",
                    passwordEncoder.encode("Ferdinan"),
                    "¿Afición favorita?",
                    "Teatro",
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[17],
                    "VillaJudo",
                    61,
                    null,
                    Set.of(Role.C))
            );

            competitors.add(new User(
                    "0000000016",
                    "Ana",
                    "Izquierdo Olmos",
                    Gender.M,
                    910000016,
                    "a.izquierdo@gmail.com",
                    dateService.stringToDate("07/05/2002"),
                    "0000000016A",
                    "AnaCuarzo",
                    passwordEncoder.encode("AnaCuarzo"),
                    "¿Cuál es tu pueblo?",
                    "Navarrevisca",
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[5],
                    "VillaJudo",
                    63,
                    null,
                    Set.of(Role.C))
            );

            competitors.add(new User(
                    "0000000017",
                    "José Luis",
                    "Toledano Hernández",
                    Gender.H,
                    910000017,
                    "jl.hernandez@gmail.com",
                    dateService.stringToDate("23/06/1975"),
                    "0000000017A",
                    "Jolutoher",
                    passwordEncoder.encode("Jolutoher"),
                    "Hobbie",
                    "Punto de cruz",
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[8],
                    "Ciedrum",
                    74,
                    null,
                    Set.of(Role.C))
            );

            competitors.add(new User(
                    "0000000018",
                    "Carlos",
                    "Horcajada Romo",
                    Gender.M,
                    910000018,
                    "c.horcajada@gmail.com",
                    dateService.stringToDate("23/06/1975"),
                    "0000000018A",
                    "Carlitos",
                    passwordEncoder.encode("Carlitos"),
                    "¿Hiciste la mili?",
                    "Sí",
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[7],
                    "Ciedrum",
                    67,
                    null,
                    Set.of(Role.C))
            );

            competitors.add(new User(
                    "0000000019",
                    "Allan Robert",
                    "Cob Bellido",
                    Gender.H,
                    910000019,
                    "a.cob@gmail.com",
                    dateService.stringToDate("23/06/1975"),
                    "0000000019A",
                    "Allanzot",
                    passwordEncoder.encode("Allanzot"),
                    "¿Qué idiomas dominas?",
                    "Inglés y español",
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[6],
                    "Ciedrum",
                    73,
                    null,
                    Set.of(Role.C))
            );

            competitors.add(new User(
                    "0000000020",
                    "Enrique",
                    "Carmona Blázquez",
                    Gender.H,
                    910000020,
                    "e.carmona@gmail.com",
                    dateService.stringToDate("23/06/1975"),
                    "0000000020A",
                    "Enriquitin",
                    passwordEncoder.encode("Enriquitin"),
                    "¿Qué deporte practicas?",
                    "Karate y judo",
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[18],
                    "Ciedrum",
                    69,
                    null,
                    Set.of(Role.C))
            );

            competitors.add(new User(
                    "0000000021",
                    "Alfredo",
                    "Mendez Hernández",
                    Gender.H,
                    910000021,
                    "a.mendez@gmail.com",
                    dateService.stringToDate("23/06/1975"),
                    "0000000021A",
                    "Alfred",
                    passwordEncoder.encode("Alfred"),
                    "¿Qué asignatura te gusta?",
                    "Seguridad Informática",
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[13],
                    "Elarion",
                    77,
                    null,
                    Set.of(Role.C))
            );

            competitors.add(new User(
                    "0000000022",
                    "Alessandro",
                    "Nuzzi Herrero",
                    Gender.H,
                    910000022,
                    "s.nuzzi@gmail.com",
                    dateService.stringToDate("15/02/2001"),
                    "0000000022A",
                    "Sandrito",
                    passwordEncoder.encode("Sandrito"),
                    "¿Qué asignatura te gusta?",
                    "Estadística",
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[12],
                    "Elarion",
                    77,
                    null,
                    Set.of(Role.C))
            );

            competitors.add(new User(
                    "0000000023",
                    "Jesús",
                    "Mendoza Díaz",
                    Gender.H,
                    910000023,
                    "j.mendoza@gmail.com",
                    dateService.stringToDate("15/02/1994"),
                    "0000000023A",
                    "Chichu",
                    passwordEncoder.encode("Chichu"),
                    "¿Dónde vives?",
                    "Ibros",
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[15],
                    "Elarion",
                    74,
                    null,
                    Set.of(Role.C))
            );

            competitors.add(new User(
                    "0000000024",
                    "Juan",
                    "Mendoza Díaz",
                    Gender.H,
                    910000024,
                    "j.mendozadiaz@gmail.com",
                    dateService.stringToDate("15/02/1988"),
                    "0000000024A",
                    "ChichuSoldao",
                    passwordEncoder.encode("ChichuSoldao"),
                    "¿Dónde trabajas?",
                    "Ejército",
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[16],
                    "Elarion",
                    78,
                    null,
                    Set.of(Role.C))
            );

            competitors.add(new User(
                    "0000000025",
                    "Carolina",
                    "Rus Díaz",
                    Gender.M,
                    910000025,
                    "c.rus@gmail.com",
                    dateService.stringToDate("27/07/1988"),
                    "0000000025A",
                    "Carol",
                    passwordEncoder.encode("Carol"),
                    "¿Dónde vives?",
                    "Parla",
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[3],
                    "Budokan",
                    67,
                    null,
                    Set.of(Role.C))
            );

            competitors.add(new User(
                    "0000000026",
                    "Sandra",
                    "Rus Díaz",
                    Gender.M,
                    910000026,
                    "s.rus@gmail.com",
                    dateService.stringToDate("1/07/1994"),
                    "0000000026A",
                    "Sandrem",
                    passwordEncoder.encode("Sandrem"),
                    "¿Dónde vives?",
                    "Parla",
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[6],
                    "Budokan",
                    62,
                    null,
                    Set.of(Role.C))
            );

            competitors.add(new User(
                    "0000000027",
                    "Natividad",
                    "Díaz Navarro",
                    Gender.M,
                    910000027,
                    "n.diaz@gmail.com",
                    dateService.stringToDate("1/07/1994"),
                    "0000000027A",
                    "Nati",
                    passwordEncoder.encode("Nati"),
                    "¿Película favorita?",
                    "Pretty woman",
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[2],
                    "Budokan",
                    67,
                    null,
                    Set.of(Role.C))
            );

            competitors.add(new User(
                    "0000000028",
                    "Anakin",
                    "SkyWalker",
                    Gender.H,
                    910000028,
                    "a.skywalker@gmail.com",
                    dateService.stringToDate("1/07/1997"),
                    "0000000028A",
                    "Ani",
                    passwordEncoder.encode("Ani"),
                    "¿Película favorita?",
                    "Star Wars",
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[10],
                    "Budokan",
                    76,
                    null,
                    Set.of(Role.C))
            );

            competitors.add(new User(
                    "0000000029",
                    "Marcos",
                    "Rocha Morales",
                    Gender.H,
                    910000029,
                    "m.rocha@gmail.com",
                    dateService.stringToDate("22/11/2000"),
                    "0000000029A",
                    "Marquiños",
                    passwordEncoder.encode("Marquiños"),
                    "¿Asignatura favorita?",
                    "Matemáticas",
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[4],
                    "Katón",
                    71,
                    null,
                    Set.of(Role.C))
            );

            competitors.add(new User(
                    "0000000030",
                    "Ana",
                    "Martínez Jiménez",
                    Gender.M,
                    910000030,
                    "m.rocha@gmail.com",
                    dateService.stringToDate("21/06/2000"),
                    "0000000030A",
                    "AnaBanana",
                    passwordEncoder.encode("AnaBanana"),
                    "¿Asignatura favorita?",
                    "Francés",
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[1],
                    "Katón",
                    65,
                    null,
                    Set.of(Role.C))
            );

            competitors.add(new User(
                    "0000000031",
                    "Andrea",
                    "Iglesias de la Fuente",
                    Gender.M,
                    910000031,
                    "a.iglesias@gmail.com",
                    dateService.stringToDate("08/11/2000"),
                    "0000000031A",
                    "AndreChurches",
                    passwordEncoder.encode("AndreChurches"),
                    "¿Que te gusta más?",
                    "Festejar",
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[5],
                    "Katón",
                    62,
                    null,
                    Set.of(Role.C))
            );

            competitors.add(new User(
                    "0000000032",
                    "Alfonsa",
                    "Zurdo Domingo",
                    Gender.M,
                    910000032,
                    "a.zurdos@gmail.com",
                    dateService.stringToDate("21/12/1942"),
                    "0000000032A",
                    "Alf",
                    passwordEncoder.encode("Alf"),
                    "¿Quién es tu nieta?",
                    "Andrea",
                    imageService.uploadProfileImage("/static/sampleImages/1234567890.jpg"),
                    Belt.values()[14],
                    "Katón",
                    73,
                    null,
                    Set.of(Role.C))
            );

            userService.saveAll(competitors);

            //Referees
            List<User> referees = new ArrayList<>();
            /*for (int i = 0; i <= 7; i++) {
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
                        passwordEncoder.encode("arbitro" + i),
                        "Arbitro?",
                        "Arbitro",
                        imageService.uploadProfileImage("/static/sampleImages/1234567891.jpg"),
                        Belt.values()[10 + i],
                        null,
                        null,
                        RefereeRange.values()[i % 4],
                        Set.of(Role.R)));
            }*/

            referees.add(new User(
                    "JU-0000000001",
                    "Tar",
                    "Mairon",
                    Gender.H,
                    910000033,
                    "t.mairon@arbitro.com",
                    dateService.stringToDate("2/6/1950"),
                    "0000033A",
                    "Sauron",
                    passwordEncoder.encode("Sauron"),
                    "¿Qué quieres?",
                    "La tierra media",
                    imageService.uploadProfileImage("/static/sampleImages/1234567891.jpg"),
                    Belt.values()[16],
                    null,
                    null,
                    RefereeRange.values()[0],
                    Set.of(Role.R))
            );

            referees.add(new User(
                    "JU-0000000002",
                    "Padme",
                    "Amidala",
                    Gender.M,
                    910000034,
                    "p.amidala@arbitro.com",
                    dateService.stringToDate("7/10/1966"),
                    "0000034A",
                    "Pad",
                    passwordEncoder.encode("Pad"),
                    "¿Qué buscas?",
                    "La paz",
                    imageService.uploadProfileImage("/static/sampleImages/1234567891.jpg"),
                    Belt.values()[13],
                    "Gym Olympo",
                    null,
                    RefereeRange.values()[1],
                    Set.of(Role.R))
            );

            referees.add(new User(
                    "JU-0000000003",
                    "Ana Maria",
                    "Herrero Zurdo",
                    Gender.M,
                    910000035,
                    "am.herrero@arbitro.com",
                    dateService.stringToDate("21/11/1966"),
                    "0000035A",
                    "AnaMary",
                    passwordEncoder.encode("AnaMary"),
                    "¿Qué te gusta hacer?",
                    "Leer",
                    imageService.uploadProfileImage("/static/sampleImages/1234567891.jpg"),
                    Belt.values()[11],
                    null,
                    null,
                    RefereeRange.values()[2],
                    Set.of(Role.R))
            );

            referees.add(new User(
                    "JU-0000000004",
                    "Claudio",
                    "Nuzzi Morales",
                    Gender.H,
                    910000036,
                    "c.nuzzi@arbitro.com",
                    dateService.stringToDate("07/07/1968"),
                    "0000036A",
                    "Clau",
                    passwordEncoder.encode("Clau"),
                    "¿Qué te gusta hacer?",
                    "Cocinar",
                    imageService.uploadProfileImage("/static/sampleImages/1234567891.jpg"),
                    Belt.values()[18],
                    null,
                    null,
                    RefereeRange.values()[3],
                    Set.of(Role.R))
            );

            //Administrators
            User admin = new User(
                    "JU-0000000005",
                    "Cronos",
                    "Tempo Limit",
                    Gender.M,
                    910000037,
                    "c.tempo@admin.com",
                    dateService.stringToDate("15/06/1001"),
                    "00000037A",
                    "Cron",
                    passwordEncoder.encode("Cron"),
                    "¿Quién es el padre de los dioses?",
                    "Soy yo",
                    imageService.uploadProfileImage("/static/sampleImages/1234567892.jpg"),
                    Belt.values()[20],
                    "Temple",
                    null,
                    RefereeRange.C,
                    Set.of(Role.R, Role.A));

            referees.add(admin);
            userService.saveAll(referees);

        // STEP 2. Saving news competitions
        List<Competition> competitions = new ArrayList<>();
        Competition trial1 = new Competition(
                "Competición de inauguración 1",
                "Está competición es una celebración debido al nacimiento de la aplicación web Juding por lo que está abierta a todas las edades y es mixta.",
                70,
                80,
                dateService.stringToTimestamp("19/03/2021 12:00"),
                dateService.stringToTimestamp("24/03/2021 19:00"),
                referees.get(0),
                Attendance.N
        );
        competitionService.add(trial1);
        competitions.add(trial1);

        Competition trial2 = new Competition(
                "Competición de inauguración 2",
                "Está competición es una celebración debido al nacimiento de la aplicación web Juding por lo que está abierta a todas las edades y es mixta.",
                60,
                70,
                dateService.stringToTimestamp("19/03/2021 12:00"),
                dateService.stringToTimestamp("24/03/2021 19:00"),
                referees.get(0),
                Attendance.N
        );
        competitionService.add(trial2);
        competitions.add(trial2);

        Competition trial3 = new Competition(
                "Competición de inauguración 1 Revancha",
                "Aprovecha esta nueva oportunidad para ganar si no pudiste la anterior vez o para mantenerte en el podium.",
                70,
                80,
                dateService.stringToTimestamp("19/03/2021 12:00"),
                dateService.stringToTimestamp("24/03/2021 19:00"),
                referees.get(0),
                Attendance.N
        );
        competitionService.add(trial3);
        competitions.add(trial3);

        Competition trial4 = new Competition(
                "Competición de inauguración 1 Revancha",
                "Aprovecha esta nueva oportunidad para ganar si no pudiste la anterior vez o para mantenerte en el podium.",
                60,
                70,
                dateService.stringToTimestamp("19/03/2021 12:00"),
                dateService.stringToTimestamp("24/03/2021 19:00"),
                referees.get(0),
                Attendance.N
        );
        competitionService.add(trial4);
        competitions.add(trial4);

        Competition trial5 = new Competition(
                "Una nueva era 1",
                "Las inauguraciones ya terminaron y ahora es tu turno de de demostrar los frutos de tu nuevo entrenamiento. Dalo todo en el tatami",
                70,
                80,
                dateService.stringToTimestamp("19/03/2021 12:00"),
                dateService.stringToTimestamp("24/03/2021 19:00"),
                referees.get(0),
                Attendance.N
        );
        competitionService.add(trial5);
        competitions.add(trial5);

        Competition trial6 = new Competition(
                "Una nueva era 2",
                "Las inauguraciones ya terminaron y ahora es tu turno de de demostrar los frutos de tu nuevo entrenamiento. Dalo todo en el tatami",
                60,
                70,
                dateService.stringToTimestamp("19/03/2021 12:00"),
                dateService.stringToTimestamp("24/03/2021 19:00"),
                referees.get(0),
                Attendance.N
        );
        competitionService.add(trial6);
        competitions.add(trial6);

        Competition trial7 = new Competition(
                "Futuros maestros 1",
                "Alcanza la cima del ranking de competidores y destaca ante los ojos de los seleccionadores nacionales",
                70,
                80,
                dateService.stringToTimestamp("19/03/2021 12:00"),
                dateService.stringToTimestamp("24/03/2021 19:00"),
                referees.get(0),
                Attendance.N
        );
        competitionService.add(trial7);
        competitions.add(trial7);

        Competition trial8 = new Competition(
                "Futuros maestros 2",
                "Alcanza la cima del ranking de competidores y destaca ante los ojos de los seleccionadores nacionales",
                60,
                70,
                dateService.stringToTimestamp("19/03/2021 12:00"),
                dateService.stringToTimestamp("24/03/2021 19:00"),
                referees.get(0),
                Attendance.N
        );
        competitionService.add(trial8);
        competitions.add(trial8);

        Competition trial9 = new Competition(
                "Campeonato benéfico 1",
                "Nuestro competidores son los mejores y ya dan mucho espectáculo. Hemos abierto una nueva tienda de regalos y todo lo que saquemos de la venta de productos durante la competición será donado a distintas ong",
                70,
                80,
                dateService.stringToTimestamp("19/03/2021 12:00"),
                dateService.stringToTimestamp("24/03/2021 19:00"),
                referees.get(0),
                Attendance.N
        );
        competitionService.add(trial9);
        competitions.add(trial9);

        Competition trial10 = new Competition(
                "Campeonato benéfico 2",
                "Nuestro competidores son los mejores y ya dan mucho espectáculo. Hemos abierto una nueva tienda de regalos y todo lo que saquemos de la venta de productos durante la competición será donado a distintas ong",
                60,
                70,
                dateService.stringToTimestamp("19/03/2021 12:00"),
                dateService.stringToTimestamp("24/03/2021 19:00"),
                referees.get(0),
                Attendance.N
        );
        competitionService.add(trial10);
        competitions.add(trial10);

        Competition trial11 = new Competition(
                "El futuro apremia 1",
                "Se terminó esta temporada por el momento pero dentro de unos meses empieza la siguiente. Inscríbete ya y entrena duro hasta la fecha.",
                70,
                80,
                dateService.stringToTimestamp("19/03/2021 12:00"),
                dateService.stringToTimestamp("24/03/2021 19:00"),
                referees.get(0),
                Attendance.N
        );
        competitionService.add(trial11);
        competitions.add(trial11);

        Competition trial12 = new Competition(
                "El futuro apremia 2",
                "Se terminó esta temporada por el momento pero dentro de unos meses empieza la siguiente. Inscríbete ya y entrena duro hasta la fecha.",
                60,
                70,
                dateService.stringToTimestamp("19/04/2021 12:00"),
                dateService.stringToTimestamp("24/04/2021 19:00"),
                referees.get(0),
                Attendance.N
        );
        competitionService.add(trial12);
        competitions.add(trial12);


        // 3. Joining competitors into competition
        /*for (int i = 0; i < 16; i++) {
            competitionService.joinCompetition(competitions.get(0), competitors.get(i));
        }*/
        //TRIAL1
        competitionService.joinCompetition(competitions.get(0),competitors.get(12));
        competitionService.joinCompetition(competitions.get(0),competitors.get(20));
        competitionService.joinCompetition(competitions.get(0),competitors.get(9));
        competitionService.joinCompetition(competitions.get(0),competitors.get(14));
        competitionService.joinCompetition(competitions.get(0),competitors.get(27));
        competitionService.joinCompetition(competitions.get(0),competitors.get(22));
        competitionService.joinCompetition(competitions.get(0),competitors.get(10));
        competitionService.joinCompetition(competitions.get(0),competitors.get(16));
        competitionService.joinCompetition(competitions.get(0),competitors.get(1));
        competitionService.joinCompetition(competitions.get(0),competitors.get(18));
        competitionService.joinCompetition(competitions.get(0),competitors.get(21));
        competitionService.joinCompetition(competitions.get(0),competitors.get(3));
        competitionService.joinCompetition(competitions.get(0),competitors.get(23));
        competitionService.joinCompetition(competitions.get(0),competitors.get(31));
        competitionService.joinCompetition(competitions.get(0),competitors.get(28));
        competitionService.joinCompetition(competitions.get(0),competitors.get(0));

        competitionService.add(competitions.get(0));

        //TRIAL2
        competitionService.joinCompetition(competitions.get(1),competitors.get(11));
        competitionService.joinCompetition(competitions.get(1),competitors.get(7));
        competitionService.joinCompetition(competitions.get(1),competitors.get(19));
        competitionService.joinCompetition(competitions.get(1),competitors.get(25));
        competitionService.joinCompetition(competitions.get(1),competitors.get(26));
        competitionService.joinCompetition(competitions.get(1),competitors.get(5));
        competitionService.joinCompetition(competitions.get(1),competitors.get(29));
        competitionService.joinCompetition(competitions.get(1),competitors.get(15));
        competitionService.joinCompetition(competitions.get(1),competitors.get(6));
        competitionService.joinCompetition(competitions.get(1),competitors.get(13));
        competitionService.joinCompetition(competitions.get(1),competitors.get(24));
        competitionService.joinCompetition(competitions.get(1),competitors.get(2));
        competitionService.joinCompetition(competitions.get(1),competitors.get(4));
        competitionService.joinCompetition(competitions.get(1),competitors.get(17));
        competitionService.joinCompetition(competitions.get(1),competitors.get(8));
        competitionService.joinCompetition(competitions.get(1),competitors.get(30));

        competitionService.add(competitions.get(1));

        //TRIAL3
        competitionService.joinCompetition(competitions.get(2),competitors.get(10));
        competitionService.joinCompetition(competitions.get(2),competitors.get(16));
        competitionService.joinCompetition(competitions.get(2),competitors.get(22));
        competitionService.joinCompetition(competitions.get(2),competitors.get(20));
        competitionService.joinCompetition(competitions.get(2),competitors.get(12));
        competitionService.joinCompetition(competitions.get(2),competitors.get(14));
        competitionService.joinCompetition(competitions.get(2),competitors.get(27));
        competitionService.joinCompetition(competitions.get(2),competitors.get(9));
        competitionService.joinCompetition(competitions.get(2),competitors.get(31));
        competitionService.joinCompetition(competitions.get(2),competitors.get(28));
        competitionService.joinCompetition(competitions.get(2),competitors.get(18));
        competitionService.joinCompetition(competitions.get(2),competitors.get(1));
        competitionService.joinCompetition(competitions.get(2),competitors.get(0));
        competitionService.joinCompetition(competitions.get(2),competitors.get(3));
        competitionService.joinCompetition(competitions.get(2),competitors.get(21));
        competitionService.joinCompetition(competitions.get(2),competitors.get(23));

        competitionService.add(competitions.get(2));

        //TRIAL4
        competitionService.joinCompetition(competitions.get(3),competitors.get(26));
        competitionService.joinCompetition(competitions.get(3),competitors.get(19));
        competitionService.joinCompetition(competitions.get(3),competitors.get(7));
        competitionService.joinCompetition(competitions.get(3),competitors.get(4));
        competitionService.joinCompetition(competitions.get(3),competitors.get(13));
        competitionService.joinCompetition(competitions.get(3),competitors.get(25));
        competitionService.joinCompetition(competitions.get(3),competitors.get(5));
        competitionService.joinCompetition(competitions.get(3),competitors.get(11));
        competitionService.joinCompetition(competitions.get(3),competitors.get(30));
        competitionService.joinCompetition(competitions.get(3),competitors.get(8));
        competitionService.joinCompetition(competitions.get(3),competitors.get(24));
        competitionService.joinCompetition(competitions.get(3),competitors.get(15));
        competitionService.joinCompetition(competitions.get(3),competitors.get(2));
        competitionService.joinCompetition(competitions.get(3),competitors.get(29));
        competitionService.joinCompetition(competitions.get(3),competitors.get(16));
        competitionService.joinCompetition(competitions.get(3),competitors.get(17));

        competitionService.add(competitions.get(3));

        //TRIAL5
        competitionService.joinCompetition(competitions.get(4),competitors.get(14));
        competitionService.joinCompetition(competitions.get(4),competitors.get(18));
        competitionService.joinCompetition(competitions.get(4),competitors.get(3));
        competitionService.joinCompetition(competitions.get(4),competitors.get(10));
        competitionService.joinCompetition(competitions.get(4),competitors.get(16));
        competitionService.joinCompetition(competitions.get(4),competitors.get(12));
        competitionService.joinCompetition(competitions.get(4),competitors.get(23));
        competitionService.joinCompetition(competitions.get(4),competitors.get(28));
        competitionService.joinCompetition(competitions.get(4),competitors.get(1));
        competitionService.joinCompetition(competitions.get(4),competitors.get(31));
        competitionService.joinCompetition(competitions.get(4),competitors.get(20));
        competitionService.joinCompetition(competitions.get(4),competitors.get(22));
        competitionService.joinCompetition(competitions.get(4),competitors.get(27));
        competitionService.joinCompetition(competitions.get(4),competitors.get(21));
        competitionService.joinCompetition(competitions.get(4),competitors.get(0));
        competitionService.joinCompetition(competitions.get(4),competitors.get(9));

        competitionService.add(competitions.get(4));

        //TRIAL6
        competitionService.joinCompetition(competitions.get(5),competitors.get(11));
        competitionService.joinCompetition(competitions.get(5),competitors.get(17));
        competitionService.joinCompetition(competitions.get(5),competitors.get(6));
        competitionService.joinCompetition(competitions.get(5),competitors.get(24));
        competitionService.joinCompetition(competitions.get(5),competitors.get(29));
        competitionService.joinCompetition(competitions.get(5),competitors.get(19));
        competitionService.joinCompetition(competitions.get(5),competitors.get(30));
        competitionService.joinCompetition(competitions.get(5),competitors.get(15));
        competitionService.joinCompetition(competitions.get(5),competitors.get(8));
        competitionService.joinCompetition(competitions.get(5),competitors.get(13));
        competitionService.joinCompetition(competitions.get(5),competitors.get(25));
        competitionService.joinCompetition(competitions.get(5),competitors.get(26));
        competitionService.joinCompetition(competitions.get(5),competitors.get(4));
        competitionService.joinCompetition(competitions.get(5),competitors.get(7));
        competitionService.joinCompetition(competitions.get(5),competitors.get(2));
        competitionService.joinCompetition(competitions.get(5),competitors.get(5));

        competitionService.add(competitions.get(5));

        //TRIAL7
        competitionService.joinCompetition(competitions.get(6),competitors.get(10));
        competitionService.joinCompetition(competitions.get(6),competitors.get(27));
        competitionService.joinCompetition(competitions.get(6),competitors.get(31));
        competitionService.joinCompetition(competitions.get(6),competitors.get(28));
        competitionService.joinCompetition(competitions.get(6),competitors.get(20));
        competitionService.joinCompetition(competitions.get(6),competitors.get(16));
        competitionService.joinCompetition(competitions.get(6),competitors.get(14));
        competitionService.joinCompetition(competitions.get(6),competitors.get(23));
        competitionService.joinCompetition(competitions.get(6),competitors.get(18));
        competitionService.joinCompetition(competitions.get(6),competitors.get(3));
        competitionService.joinCompetition(competitions.get(6),competitors.get(22));
        competitionService.joinCompetition(competitions.get(6),competitors.get(12));
        competitionService.joinCompetition(competitions.get(6),competitors.get(1));
        competitionService.joinCompetition(competitions.get(6),competitors.get(20));
        competitionService.joinCompetition(competitions.get(6),competitors.get(0));
        competitionService.joinCompetition(competitions.get(6),competitors.get(9));

        competitionService.add(competitions.get(6));

        //TRIAL8
        competitionService.joinCompetition(competitions.get(7),competitors.get(13));
        competitionService.joinCompetition(competitions.get(7),competitors.get(19));
        competitionService.joinCompetition(competitions.get(7),competitors.get(6));
        competitionService.joinCompetition(competitions.get(7),competitors.get(8));
        competitionService.joinCompetition(competitions.get(7),competitors.get(24));
        competitionService.joinCompetition(competitions.get(7),competitors.get(17));
        competitionService.joinCompetition(competitions.get(7),competitors.get(29));
        competitionService.joinCompetition(competitions.get(7),competitors.get(15));
        competitionService.joinCompetition(competitions.get(7),competitors.get(25));
        competitionService.joinCompetition(competitions.get(7),competitors.get(11));
        competitionService.joinCompetition(competitions.get(7),competitors.get(5));
        competitionService.joinCompetition(competitions.get(7),competitors.get(26));
        competitionService.joinCompetition(competitions.get(7),competitors.get(30));
        competitionService.joinCompetition(competitions.get(7),competitors.get(7));
        competitionService.joinCompetition(competitions.get(7),competitors.get(2));
        competitionService.joinCompetition(competitions.get(7),competitors.get(4));

        competitionService.add(competitions.get(7));

        //TRIAL9
        competitionService.joinCompetition(competitions.get(8),competitors.get(14));
        competitionService.joinCompetition(competitions.get(8),competitors.get(21));
        competitionService.joinCompetition(competitions.get(8),competitors.get(18));
        competitionService.joinCompetition(competitions.get(8),competitors.get(10));
        competitionService.joinCompetition(competitions.get(8),competitors.get(9));
        competitionService.joinCompetition(competitions.get(8),competitors.get(23));
        competitionService.joinCompetition(competitions.get(8),competitors.get(12));
        competitionService.joinCompetition(competitions.get(8),competitors.get(16));
        competitionService.joinCompetition(competitions.get(8),competitors.get(3));
        competitionService.joinCompetition(competitions.get(8),competitors.get(20));
        competitionService.joinCompetition(competitions.get(8),competitors.get(0));
        competitionService.joinCompetition(competitions.get(8),competitors.get(22));
        competitionService.joinCompetition(competitions.get(8),competitors.get(1));
        competitionService.joinCompetition(competitions.get(8),competitors.get(31));
        competitionService.joinCompetition(competitions.get(8),competitors.get(27));
        competitionService.joinCompetition(competitions.get(8),competitors.get(28));

        competitionService.add(competitions.get(8));

        //TRIAL10
        competitionService.joinCompetition(competitions.get(9),competitors.get(7));
        competitionService.joinCompetition(competitions.get(9),competitors.get(5));
        competitionService.joinCompetition(competitions.get(9),competitors.get(13));
        competitionService.joinCompetition(competitions.get(9),competitors.get(17));
        competitionService.joinCompetition(competitions.get(9),competitors.get(24));
        competitionService.joinCompetition(competitions.get(9),competitors.get(19));
        competitionService.joinCompetition(competitions.get(9),competitors.get(26));
        competitionService.joinCompetition(competitions.get(9),competitors.get(30));
        competitionService.joinCompetition(competitions.get(9),competitors.get(11));
        competitionService.joinCompetition(competitions.get(9),competitors.get(15));
        competitionService.joinCompetition(competitions.get(9),competitors.get(25));
        competitionService.joinCompetition(competitions.get(9),competitors.get(4));
        competitionService.joinCompetition(competitions.get(9),competitors.get(2));
        competitionService.joinCompetition(competitions.get(9),competitors.get(6));
        competitionService.joinCompetition(competitions.get(9),competitors.get(8));
        competitionService.joinCompetition(competitions.get(9),competitors.get(29));

        competitionService.add(competitions.get(9));

        //TRIAL11
        competitionService.joinCompetition(competitions.get(10),competitors.get(20));
        competitionService.joinCompetition(competitions.get(10),competitors.get(27));
        competitionService.joinCompetition(competitions.get(10),competitors.get(31));
        competitionService.joinCompetition(competitions.get(10),competitors.get(16));
        competitionService.joinCompetition(competitions.get(10),competitors.get(14));
        competitionService.joinCompetition(competitions.get(10),competitors.get(21));
        competitionService.joinCompetition(competitions.get(10),competitors.get(12));
        competitionService.joinCompetition(competitions.get(10),competitors.get(18));
        competitionService.joinCompetition(competitions.get(10),competitors.get(1));
        competitionService.joinCompetition(competitions.get(10),competitors.get(10));
        competitionService.joinCompetition(competitions.get(10),competitors.get(9));
        competitionService.joinCompetition(competitions.get(10),competitors.get(22));
        competitionService.joinCompetition(competitions.get(10),competitors.get(28));
        competitionService.joinCompetition(competitions.get(10),competitors.get(23));
        competitionService.joinCompetition(competitions.get(10),competitors.get(0));
        //DEMO ADDICTION
        //competitionService.joinCompetition(competitions.get(10),competitors.get(3));

        competitionService.add(competitions.get(10));

        //TRIAL12
        competitionService.joinCompetition(competitions.get(11),competitors.get(15));
        competitionService.joinCompetition(competitions.get(11),competitors.get(25));
        competitionService.joinCompetition(competitions.get(11),competitors.get(29));
        competitionService.joinCompetition(competitions.get(11),competitors.get(11));
        competitionService.joinCompetition(competitions.get(11),competitors.get(24));
        competitionService.joinCompetition(competitions.get(11),competitors.get(17));
        competitionService.joinCompetition(competitions.get(11),competitors.get(5));
        competitionService.joinCompetition(competitions.get(11),competitors.get(7));
        competitionService.joinCompetition(competitions.get(11),competitors.get(2));
        competitionService.joinCompetition(competitions.get(11),competitors.get(6));
        competitionService.joinCompetition(competitions.get(11),competitors.get(13));
        competitionService.joinCompetition(competitions.get(11),competitors.get(26));
        competitionService.joinCompetition(competitions.get(11),competitors.get(8));
        competitionService.joinCompetition(competitions.get(11),competitors.get(30));
        competitionService.joinCompetition(competitions.get(11),competitors.get(19));
        //DEMO ADDICTION
        //competitionService.joinCompetition(competitions.get(11),competitors.get(4));

        competitionService.add(competitions.get(11));
        // 3.2. Simulating tournaments
            //TRIAL1
            // 8th-finals
            competitionService.fightFinished(competitions.get(0), competitors.get(20), competitors.get(12));
            competitionService.fightFinished(competitions.get(0), competitors.get(9), competitors.get(14));
            competitionService.fightFinished(competitions.get(0), competitors.get(22), competitors.get(27));
            competitionService.fightFinished(competitions.get(0), competitors.get(10), competitors.get(16));
            competitionService.fightFinished(competitions.get(0), competitors.get(18), competitors.get(1));
            competitionService.fightFinished(competitions.get(0), competitors.get(3), competitors.get(21));
            competitionService.fightFinished(competitions.get(0), competitors.get(31), competitors.get(23));
            competitionService.fightFinished(competitions.get(0), competitors.get(28), competitors.get(0));
            // Quarterfinals
            competitionService.fightFinished(competitions.get(0), competitors.get(9), competitors.get(20));
            competitionService.fightFinished(competitions.get(0), competitors.get(22), competitors.get(10));
            competitionService.fightFinished(competitions.get(0), competitors.get(3), competitors.get(18));
            competitionService.fightFinished(competitions.get(0), competitors.get(28), competitors.get(31));
            // Semifinals
            competitionService.fightFinished(competitions.get(0), competitors.get(22), competitors.get(9));
            competitionService.fightFinished(competitions.get(0), competitors.get(3), competitors.get(28));
            // Finals
            competitionService.fightFinished(competitions.get(0), competitors.get(3), competitors.get(22));
            competitionService.add(competitions.get(0));

            //TRIAL2
            // 8th-finals
            competitionService.fightFinished(competitions.get(1), competitors.get(7), competitors.get(11));
            competitionService.fightFinished(competitions.get(1), competitors.get(25), competitors.get(19));
            competitionService.fightFinished(competitions.get(1), competitors.get(5), competitors.get(26));
            competitionService.fightFinished(competitions.get(1), competitors.get(15), competitors.get(29));
            competitionService.fightFinished(competitions.get(1), competitors.get(13), competitors.get(6));
            competitionService.fightFinished(competitions.get(1), competitors.get(24), competitors.get(2));
            competitionService.fightFinished(competitions.get(1), competitors.get(4), competitors.get(17));
            competitionService.fightFinished(competitions.get(1), competitors.get(8), competitors.get(30));
            // Quarterfinals
            competitionService.fightFinished(competitions.get(1), competitors.get(7), competitors.get(25));
            competitionService.fightFinished(competitions.get(1), competitors.get(5), competitors.get(15));
            competitionService.fightFinished(competitions.get(1), competitors.get(24), competitors.get(13));
            competitionService.fightFinished(competitions.get(1), competitors.get(4), competitors.get(24));
            // Semifinals
            competitionService.fightFinished(competitions.get(1), competitors.get(5), competitors.get(7));
            competitionService.fightFinished(competitions.get(1), competitors.get(4), competitors.get(24));
            // Finals
            competitionService.fightFinished(competitions.get(1), competitors.get(5), competitors.get(4));
            competitionService.add(competitions.get(1));

            //TRIAL3
            // 8th-finals
            competitionService.fightFinished(competitions.get(2), competitors.get(10), competitors.get(16));
            competitionService.fightFinished(competitions.get(2), competitors.get(20), competitors.get(22));
            competitionService.fightFinished(competitions.get(2), competitors.get(12), competitors.get(14));
            competitionService.fightFinished(competitions.get(2), competitors.get(9), competitors.get(27));
            competitionService.fightFinished(competitions.get(2), competitors.get(28), competitors.get(31));
            competitionService.fightFinished(competitions.get(2), competitors.get(1), competitors.get(18));
            competitionService.fightFinished(competitions.get(2), competitors.get(3), competitors.get(0));
            competitionService.fightFinished(competitions.get(2), competitors.get(21), competitors.get(23));
            // Quarterfinals
            competitionService.fightFinished(competitions.get(2), competitors.get(20), competitors.get(10));
            competitionService.fightFinished(competitions.get(2), competitors.get(9), competitors.get(12));
            competitionService.fightFinished(competitions.get(2), competitors.get(1), competitors.get(28));
            competitionService.fightFinished(competitions.get(2), competitors.get(3), competitors.get(21));
            // Semifinals
            competitionService.fightFinished(competitions.get(2), competitors.get(9), competitors.get(20));
            competitionService.fightFinished(competitions.get(2), competitors.get(3), competitors.get(1));
            // Finals
            competitionService.fightFinished(competitions.get(2), competitors.get(9), competitors.get(3));
            competitionService.add(competitions.get(2));

            //TRIAL4
            // 8th-finals
            competitionService.fightFinished(competitions.get(3), competitors.get(19), competitors.get(26));
            competitionService.fightFinished(competitions.get(3), competitors.get(4), competitors.get(7));
            competitionService.fightFinished(competitions.get(3), competitors.get(25), competitors.get(13));
            competitionService.fightFinished(competitions.get(3), competitors.get(11), competitors.get(5));
            competitionService.fightFinished(competitions.get(3), competitors.get(8), competitors.get(30));
            competitionService.fightFinished(competitions.get(3), competitors.get(15), competitors.get(24));
            competitionService.fightFinished(competitions.get(3), competitors.get(2), competitors.get(29));
            competitionService.fightFinished(competitions.get(3), competitors.get(17), competitors.get(16));
            // Quarterfinals
            competitionService.fightFinished(competitions.get(3), competitors.get(4), competitors.get(19));
            competitionService.fightFinished(competitions.get(3), competitors.get(11), competitors.get(25));
            competitionService.fightFinished(competitions.get(3), competitors.get(15), competitors.get(8));
            competitionService.fightFinished(competitions.get(3), competitors.get(2), competitors.get(17));
            // Semifinals
            competitionService.fightFinished(competitions.get(3), competitors.get(11), competitors.get(4));
            competitionService.fightFinished(competitions.get(3), competitors.get(2), competitors.get(15));
            // Finals
            competitionService.fightFinished(competitions.get(3), competitors.get(2), competitors.get(11));
            competitionService.add(competitions.get(3));

            //TRIAL5
            // 8th-finals
            competitionService.fightFinished(competitions.get(4), competitors.get(14), competitors.get(18));
            competitionService.fightFinished(competitions.get(4), competitors.get(3), competitors.get(10));
            competitionService.fightFinished(competitions.get(4), competitors.get(12), competitors.get(16));
            competitionService.fightFinished(competitions.get(4), competitors.get(28), competitors.get(23));
            competitionService.fightFinished(competitions.get(4), competitors.get(1), competitors.get(31));
            competitionService.fightFinished(competitions.get(4), competitors.get(22), competitors.get(20));
            competitionService.fightFinished(competitions.get(4), competitors.get(21), competitors.get(27));
            competitionService.fightFinished(competitions.get(4), competitors.get(9), competitors.get(0));
            // Quarterfinals
            competitionService.fightFinished(competitions.get(4), competitors.get(3), competitors.get(14));
            competitionService.fightFinished(competitions.get(4), competitors.get(12), competitors.get(28));
            competitionService.fightFinished(competitions.get(4), competitors.get(1), competitors.get(22));
            competitionService.fightFinished(competitions.get(4), competitors.get(9), competitors.get(21));
            // Semifinals
            competitionService.fightFinished(competitions.get(4), competitors.get(12), competitors.get(3));
            competitionService.fightFinished(competitions.get(4), competitors.get(9), competitors.get(1));
            // Finals
            competitionService.fightFinished(competitions.get(4), competitors.get(9), competitors.get(12));
            competitionService.add(competitions.get(4));

            //TRIAL6
            // 8th-finals
            competitionService.fightFinished(competitions.get(5), competitors.get(11), competitors.get(17));
            competitionService.fightFinished(competitions.get(5), competitors.get(6), competitors.get(24));
            competitionService.fightFinished(competitions.get(5), competitors.get(19), competitors.get(29));
            competitionService.fightFinished(competitions.get(5), competitors.get(15), competitors.get(30));
            competitionService.fightFinished(competitions.get(5), competitors.get(13), competitors.get(8));
            competitionService.fightFinished(competitions.get(5), competitors.get(26), competitors.get(25));
            competitionService.fightFinished(competitions.get(5), competitors.get(4), competitors.get(7));
            competitionService.fightFinished(competitions.get(5), competitors.get(5), competitors.get(2));
            // Quarterfinals
            competitionService.fightFinished(competitions.get(5), competitors.get(6), competitors.get(11));
            competitionService.fightFinished(competitions.get(5), competitors.get(15), competitors.get(19));
            competitionService.fightFinished(competitions.get(5), competitors.get(26), competitors.get(13));
            competitionService.fightFinished(competitions.get(5), competitors.get(4), competitors.get(5));
            // Semifinals
            competitionService.fightFinished(competitions.get(5), competitors.get(6), competitors.get(15));
            competitionService.fightFinished(competitions.get(5), competitors.get(4), competitors.get(26));
            // Finals
            competitionService.fightFinished(competitions.get(5), competitors.get(6), competitors.get(4));
            competitionService.add(competitions.get(5));

            //TRIAL7
            // 8th-finals
            competitionService.fightFinished(competitions.get(6), competitors.get(27), competitors.get(10));
            competitionService.fightFinished(competitions.get(6), competitors.get(28), competitors.get(31));
            competitionService.fightFinished(competitions.get(6), competitors.get(16), competitors.get(20));
            competitionService.fightFinished(competitions.get(6), competitors.get(14), competitors.get(23));
            competitionService.fightFinished(competitions.get(6), competitors.get(18), competitors.get(3));
            competitionService.fightFinished(competitions.get(6), competitors.get(12), competitors.get(22));
            competitionService.fightFinished(competitions.get(6), competitors.get(1), competitors.get(20));
            competitionService.fightFinished(competitions.get(6), competitors.get(9), competitors.get(0));
            // Quarterfinals
            competitionService.fightFinished(competitions.get(6), competitors.get(28), competitors.get(27));
            competitionService.fightFinished(competitions.get(6), competitors.get(14), competitors.get(16));
            competitionService.fightFinished(competitions.get(6), competitors.get(12), competitors.get(18));
            competitionService.fightFinished(competitions.get(6), competitors.get(9), competitors.get(12));
            // Semifinals
            competitionService.fightFinished(competitions.get(6), competitors.get(14), competitors.get(28));
            competitionService.fightFinished(competitions.get(6), competitors.get(9), competitors.get(12));
            // Finals
            competitionService.fightFinished(competitions.get(6), competitors.get(9), competitors.get(14));
            competitionService.add(competitions.get(6));

            //TRIAL8
            // 8th-finals
            competitionService.fightFinished(competitions.get(7), competitors.get(19), competitors.get(13));
            competitionService.fightFinished(competitions.get(7), competitors.get(0), competitors.get(6));
            competitionService.fightFinished(competitions.get(7), competitors.get(17), competitors.get(24));
            competitionService.fightFinished(competitions.get(7), competitors.get(15), competitors.get(29));
            competitionService.fightFinished(competitions.get(7), competitors.get(11), competitors.get(25));
            competitionService.fightFinished(competitions.get(7), competitors.get(26), competitors.get(5));
            competitionService.fightFinished(competitions.get(7), competitors.get(7), competitors.get(30));
            competitionService.fightFinished(competitions.get(7), competitors.get(2), competitors.get(4));
            // Quarterfinals
            competitionService.fightFinished(competitions.get(7), competitors.get(8), competitors.get(19));
            competitionService.fightFinished(competitions.get(7), competitors.get(15), competitors.get(17));
            competitionService.fightFinished(competitions.get(7), competitors.get(26), competitors.get(11));
            competitionService.fightFinished(competitions.get(7), competitors.get(2), competitors.get(7));
            // Semifinals
            competitionService.fightFinished(competitions.get(7), competitors.get(8), competitors.get(15));
            competitionService.fightFinished(competitions.get(7), competitors.get(2), competitors.get(26));
            // Finals
            competitionService.fightFinished(competitions.get(7), competitors.get(8), competitors.get(2));
            competitionService.add(competitions.get(7));

            //TRIAL9
            // 8th-finals
            competitionService.fightFinished(competitions.get(8), competitors.get(21), competitors.get(14));
            competitionService.fightFinished(competitions.get(8), competitors.get(18), competitors.get(10));
            competitionService.fightFinished(competitions.get(8), competitors.get(23), competitors.get(9));
            competitionService.fightFinished(competitions.get(8), competitors.get(12), competitors.get(16));
            competitionService.fightFinished(competitions.get(8), competitors.get(3), competitors.get(20));
            competitionService.fightFinished(competitions.get(8), competitors.get(0), competitors.get(22));
            competitionService.fightFinished(competitions.get(8), competitors.get(31), competitors.get(1));
            competitionService.fightFinished(competitions.get(8), competitors.get(28), competitors.get(27));

            competitionService.add(competitions.get(8));

            //TRIAL10
            // 8th-finals
            competitionService.fightFinished(competitions.get(9), competitors.get(7), competitors.get(5));
            competitionService.fightFinished(competitions.get(9), competitors.get(17), competitors.get(13));
            competitionService.fightFinished(competitions.get(9), competitors.get(19), competitors.get(24));
            competitionService.fightFinished(competitions.get(9), competitors.get(30), competitors.get(26));
            competitionService.fightFinished(competitions.get(9), competitors.get(11), competitors.get(15));
            competitionService.fightFinished(competitions.get(9), competitors.get(4), competitors.get(25));
            competitionService.fightFinished(competitions.get(9), competitors.get(6), competitors.get(2));
            competitionService.fightFinished(competitions.get(9), competitors.get(29), competitors.get(8));

            competitionService.add(competitions.get(9));

        // 4. Post creation and saving
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(admin,
                "INFORMACIÓN COMPLETA curso de Kata del Club Pandy",
                "Ampliamos la información del Curso de Katas del Club Pandy, con estos documentos y vídeos-explicativos. También aprovechamos para recordar que la entrega de premios del sorteo de la 2ª Jornada se hará el mismo día 21 de marzo a las 11:00.<br>" +
                        "¡Recordad traer la papeleta ganadora!\n" +
                        "<a href=\"https://fmjudo.es/attachments/article/4801/Cto%20katas%20infantiles-3.pdf\">CUADRO DE CATEGORÍAS Y KATAS</a></p>\n" +
                        "<a href=\"https://www.youtube.com/watch?v=LhVvmFDLMo0\">VÍDEO EXPLICATIVO GRUPO 7</a></p>\n" +
                        "<a href=\"https://www.youtube.com/watch?v=VpLdKj5KvuM\">VÍDEO EXPLICATIVO GRUPO 6</a></p>\n" +
                        "<a href=\"https://youtu.be/uvqxiXiA9eM\">Seiryoku-Zenyo-Kokumin-Taiiku</a>\n" +
                        "Además, adjuntamos este documento informativo que complementa a la Circular Nº 27 sobre el próximo curso de Katas del Club Pandy.</p>\n" +
                        "<a href=\"https://fmjudo.es//attachments/article/4188/27%20Reuni%C3%B3n%20Profesores%20y%20Curso%20de%20Kata%20Club%20Pandy%20marzo.pdf\">Circular Nº 27 - 4ª Jornada - ACTIVIDADES DE KATA “CLUB PANDY”</a>\n",
                "/static/sampleImages/judoSample1.jpg",
                new Timestamp(System.currentTimeMillis())));
        posts.add(new Post(admin,
                "INFORMACIÓN COMPLETA curso de Kata del Club Pandy",
                "Ampliamos la información del Curso de Katas del Club Pandy, con estos documentos y vídeos-explicativos. También aprovechamos para recordar que la entrega de premios del sorteo de la 2ª Jornada se hará el mismo día 21 de marzo a las 11:00.<br>" +
                        "¡Recordad traer la papeleta ganadora!\n" +
                        "<a href=\"https://fmjudo.es/attachments/article/4801/Cto%20katas%20infantiles-3.pdf\">CUADRO DE CATEGORÍAS Y KATAS</a></p>\n" +
                        "<a href=\"https://www.youtube.com/watch?v=LhVvmFDLMo0\">VÍDEO EXPLICATIVO GRUPO 7</a></p>\n" +
                        "<a href=\"https://www.youtube.com/watch?v=VpLdKj5KvuM\">VÍDEO EXPLICATIVO GRUPO 6</a></p>\n" +
                        "<a href=\"https://youtu.be/uvqxiXiA9eM\">Seiryoku-Zenyo-Kokumin-Taiiku</a>\n" +
                        "Además, adjuntamos este documento informativo que complementa a la Circular Nº 27 sobre el próximo curso de Katas del Club Pandy.</p>\n" +
                        "<a href=\"https://fmjudo.es//attachments/article/4188/27%20Reuni%C3%B3n%20Profesores%20y%20Curso%20de%20Kata%20Club%20Pandy%20marzo.pdf\">Circular Nº 27 - 4ª Jornada - ACTIVIDADES DE KATA “CLUB PANDY”</a>\n",
                "/static/sampleImages/judoSample1.jpg",
                new Timestamp(System.currentTimeMillis())));
        posts.add(new Post(admin,
                "INFORMACIÓN COMPLETA curso de Kata del Club Pandy",
                "Ampliamos la información del Curso de Katas del Club Pandy, con estos documentos y vídeos-explicativos. También aprovechamos para recordar que la entrega de premios del sorteo de la 2ª Jornada se hará el mismo día 21 de marzo a las 11:00.<br>" +
                        "¡Recordad traer la papeleta ganadora!\n" +
                        "<a href=\"https://fmjudo.es/attachments/article/4801/Cto%20katas%20infantiles-3.pdf\">CUADRO DE CATEGORÍAS Y KATAS</a></p>\n" +
                        "<a href=\"https://www.youtube.com/watch?v=LhVvmFDLMo0\">VÍDEO EXPLICATIVO GRUPO 7</a></p>\n" +
                        "<a href=\"https://www.youtube.com/watch?v=VpLdKj5KvuM\">VÍDEO EXPLICATIVO GRUPO 6</a></p>\n" +
                        "<a href=\"https://youtu.be/uvqxiXiA9eM\">Seiryoku-Zenyo-Kokumin-Taiiku</a>\n" +
                        "Además, adjuntamos este documento informativo que complementa a la Circular Nº 27 sobre el próximo curso de Katas del Club Pandy.</p>\n" +
                        "<a href=\"https://fmjudo.es//attachments/article/4188/27%20Reuni%C3%B3n%20Profesores%20y%20Curso%20de%20Kata%20Club%20Pandy%20marzo.pdf\">Circular Nº 27 - 4ª Jornada - ACTIVIDADES DE KATA “CLUB PANDY”</a>\n",
                "/static/sampleImages/judoSample1.jpg",
                new Timestamp(System.currentTimeMillis())));
        posts.add(new Post(admin,
                "INFORMACIÓN COMPLETA curso de Kata del Club Pandy",
                "Ampliamos la información del Curso de Katas del Club Pandy, con estos documentos y vídeos-explicativos. También aprovechamos para recordar que la entrega de premios del sorteo de la 2ª Jornada se hará el mismo día 21 de marzo a las 11:00.<br>" +
                        "¡Recordad traer la papeleta ganadora!\n" +
                        "<a href=\"https://fmjudo.es/attachments/article/4801/Cto%20katas%20infantiles-3.pdf\">CUADRO DE CATEGORÍAS Y KATAS</a></p>\n" +
                        "<a href=\"https://www.youtube.com/watch?v=LhVvmFDLMo0\">VÍDEO EXPLICATIVO GRUPO 7</a></p>\n" +
                        "<a href=\"https://www.youtube.com/watch?v=VpLdKj5KvuM\">VÍDEO EXPLICATIVO GRUPO 6</a></p>\n" +
                        "<a href=\"https://youtu.be/uvqxiXiA9eM\">Seiryoku-Zenyo-Kokumin-Taiiku</a>\n" +
                        "Además, adjuntamos este documento informativo que complementa a la Circular Nº 27 sobre el próximo curso de Katas del Club Pandy.</p>\n" +
                        "<a href=\"https://fmjudo.es//attachments/article/4188/27%20Reuni%C3%B3n%20Profesores%20y%20Curso%20de%20Kata%20Club%20Pandy%20marzo.pdf\">Circular Nº 27 - 4ª Jornada - ACTIVIDADES DE KATA “CLUB PANDY”</a>\n",
                "/static/sampleImages/judoSample1.jpg",
                new Timestamp(System.currentTimeMillis())));
        posts.add(new Post(admin,
                "INFORMACIÓN COMPLETA curso de Kata del Club Pandy",
                "Ampliamos la información del Curso de Katas del Club Pandy, con estos documentos y vídeos-explicativos. También aprovechamos para recordar que la entrega de premios del sorteo de la 2ª Jornada se hará el mismo día 21 de marzo a las 11:00.<br>" +
                        "¡Recordad traer la papeleta ganadora!\n" +
                        "<a href=\"https://fmjudo.es/attachments/article/4801/Cto%20katas%20infantiles-3.pdf\">CUADRO DE CATEGORÍAS Y KATAS</a></p>\n" +
                        "<a href=\"https://www.youtube.com/watch?v=LhVvmFDLMo0\">VÍDEO EXPLICATIVO GRUPO 7</a></p>\n" +
                        "<a href=\"https://www.youtube.com/watch?v=VpLdKj5KvuM\">VÍDEO EXPLICATIVO GRUPO 6</a></p>\n" +
                        "<a href=\"https://youtu.be/uvqxiXiA9eM\">Seiryoku-Zenyo-Kokumin-Taiiku</a>\n" +
                        "Además, adjuntamos este documento informativo que complementa a la Circular Nº 27 sobre el próximo curso de Katas del Club Pandy.</p>\n" +
                        "<a href=\"https://fmjudo.es//attachments/article/4188/27%20Reuni%C3%B3n%20Profesores%20y%20Curso%20de%20Kata%20Club%20Pandy%20marzo.pdf\">Circular Nº 27 - 4ª Jornada - ACTIVIDADES DE KATA “CLUB PANDY”</a>\n",
                "/static/sampleImages/judoSample1.jpg",
                new Timestamp(System.currentTimeMillis())));
        posts.add(new Post(admin,
                "INFORMACIÓN COMPLETA curso de Kata del Club Pandy",
                "Ampliamos la información del Curso de Katas del Club Pandy, con estos documentos y vídeos-explicativos. También aprovechamos para recordar que la entrega de premios del sorteo de la 2ª Jornada se hará el mismo día 21 de marzo a las 11:00.<br>" +
                        "¡Recordad traer la papeleta ganadora!\n" +
                        "<a href=\"https://fmjudo.es/attachments/article/4801/Cto%20katas%20infantiles-3.pdf\">CUADRO DE CATEGORÍAS Y KATAS</a></p>\n" +
                        "<a href=\"https://www.youtube.com/watch?v=LhVvmFDLMo0\">VÍDEO EXPLICATIVO GRUPO 7</a></p>\n" +
                        "<a href=\"https://www.youtube.com/watch?v=VpLdKj5KvuM\">VÍDEO EXPLICATIVO GRUPO 6</a></p>\n" +
                        "<a href=\"https://youtu.be/uvqxiXiA9eM\">Seiryoku-Zenyo-Kokumin-Taiiku</a>\n" +
                        "Además, adjuntamos este documento informativo que complementa a la Circular Nº 27 sobre el próximo curso de Katas del Club Pandy.</p>\n" +
                        "<a href=\"https://fmjudo.es//attachments/article/4188/27%20Reuni%C3%B3n%20Profesores%20y%20Curso%20de%20Kata%20Club%20Pandy%20marzo.pdf\">Circular Nº 27 - 4ª Jornada - ACTIVIDADES DE KATA “CLUB PANDY”</a>\n",
                "/static/sampleImages/judoSample1.jpg",
                new Timestamp(System.currentTimeMillis())));
        posts.add(new Post(admin,
                "INFORMACIÓN COMPLETA curso de Kata del Club Pandy",
                "Ampliamos la información del Curso de Katas del Club Pandy, con estos documentos y vídeos-explicativos. También aprovechamos para recordar que la entrega de premios del sorteo de la 2ª Jornada se hará el mismo día 21 de marzo a las 11:00.<br>" +
                        "¡Recordad traer la papeleta ganadora!\n" +
                        "<a href=\"https://fmjudo.es/attachments/article/4801/Cto%20katas%20infantiles-3.pdf\">CUADRO DE CATEGORÍAS Y KATAS</a></p>\n" +
                        "<a href=\"https://www.youtube.com/watch?v=LhVvmFDLMo0\">VÍDEO EXPLICATIVO GRUPO 7</a></p>\n" +
                        "<a href=\"https://www.youtube.com/watch?v=VpLdKj5KvuM\">VÍDEO EXPLICATIVO GRUPO 6</a></p>\n" +
                        "<a href=\"https://youtu.be/uvqxiXiA9eM\">Seiryoku-Zenyo-Kokumin-Taiiku</a>\n" +
                        "Además, adjuntamos este documento informativo que complementa a la Circular Nº 27 sobre el próximo curso de Katas del Club Pandy.</p>\n" +
                        "<a href=\"https://fmjudo.es//attachments/article/4188/27%20Reuni%C3%B3n%20Profesores%20y%20Curso%20de%20Kata%20Club%20Pandy%20marzo.pdf\">Circular Nº 27 - 4ª Jornada - ACTIVIDADES DE KATA “CLUB PANDY”</a>\n",
                "/static/sampleImages/judoSample1.jpg",
                new Timestamp(System.currentTimeMillis())));
        posts.add(new Post(admin,
                "INFORMACIÓN COMPLETA curso de Kata del Club Pandy",
                "Ampliamos la información del Curso de Katas del Club Pandy, con estos documentos y vídeos-explicativos. También aprovechamos para recordar que la entrega de premios del sorteo de la 2ª Jornada se hará el mismo día 21 de marzo a las 11:00.<br>" +
                        "¡Recordad traer la papeleta ganadora!\n" +
                        "<a href=\"https://fmjudo.es/attachments/article/4801/Cto%20katas%20infantiles-3.pdf\">CUADRO DE CATEGORÍAS Y KATAS</a></p>\n" +
                        "<a href=\"https://www.youtube.com/watch?v=LhVvmFDLMo0\">VÍDEO EXPLICATIVO GRUPO 7</a></p>\n" +
                        "<a href=\"https://www.youtube.com/watch?v=VpLdKj5KvuM\">VÍDEO EXPLICATIVO GRUPO 6</a></p>\n" +
                        "<a href=\"https://youtu.be/uvqxiXiA9eM\">Seiryoku-Zenyo-Kokumin-Taiiku</a>\n" +
                        "Además, adjuntamos este documento informativo que complementa a la Circular Nº 27 sobre el próximo curso de Katas del Club Pandy.</p>\n" +
                        "<a href=\"https://fmjudo.es//attachments/article/4188/27%20Reuni%C3%B3n%20Profesores%20y%20Curso%20de%20Kata%20Club%20Pandy%20marzo.pdf\">Circular Nº 27 - 4ª Jornada - ACTIVIDADES DE KATA “CLUB PANDY”</a>\n",
                "/static/sampleImages/judoSample1.jpg",
                new Timestamp(System.currentTimeMillis())));
        posts.add(new Post(admin,
                "INFORMACIÓN COMPLETA curso de Kata del Club Pandy",
                "Ampliamos la información del Curso de Katas del Club Pandy, con estos documentos y vídeos-explicativos. También aprovechamos para recordar que la entrega de premios del sorteo de la 2ª Jornada se hará el mismo día 21 de marzo a las 11:00.<br>" +
                        "¡Recordad traer la papeleta ganadora!\n" +
                        "<a href=\"https://fmjudo.es/attachments/article/4801/Cto%20katas%20infantiles-3.pdf\">CUADRO DE CATEGORÍAS Y KATAS</a></p>\n" +
                        "<a href=\"https://www.youtube.com/watch?v=LhVvmFDLMo0\">VÍDEO EXPLICATIVO GRUPO 7</a></p>\n" +
                        "<a href=\"https://www.youtube.com/watch?v=VpLdKj5KvuM\">VÍDEO EXPLICATIVO GRUPO 6</a></p>\n" +
                        "<a href=\"https://youtu.be/uvqxiXiA9eM\">Seiryoku-Zenyo-Kokumin-Taiiku</a>\n" +
                        "Además, adjuntamos este documento informativo que complementa a la Circular Nº 27 sobre el próximo curso de Katas del Club Pandy.</p>\n" +
                        "<a href=\"https://fmjudo.es//attachments/article/4188/27%20Reuni%C3%B3n%20Profesores%20y%20Curso%20de%20Kata%20Club%20Pandy%20marzo.pdf\">Circular Nº 27 - 4ª Jornada - ACTIVIDADES DE KATA “CLUB PANDY”</a>\n",
                "/static/sampleImages/judoSample1.jpg",
                new Timestamp(System.currentTimeMillis())));
        posts.add(new Post(admin,
                "INFORMACIÓN COMPLETA curso de Kata del Club Pandy",
                "Ampliamos la información del Curso de Katas del Club Pandy, con estos documentos y vídeos-explicativos. También aprovechamos para recordar que la entrega de premios del sorteo de la 2ª Jornada se hará el mismo día 21 de marzo a las 11:00.<br>" +
                        "¡Recordad traer la papeleta ganadora!\n" +
                        "<a href=\"https://fmjudo.es/attachments/article/4801/Cto%20katas%20infantiles-3.pdf\">CUADRO DE CATEGORÍAS Y KATAS</a></p>\n" +
                        "<a href=\"https://www.youtube.com/watch?v=LhVvmFDLMo0\">VÍDEO EXPLICATIVO GRUPO 7</a></p>\n" +
                        "<a href=\"https://www.youtube.com/watch?v=VpLdKj5KvuM\">VÍDEO EXPLICATIVO GRUPO 6</a></p>\n" +
                        "<a href=\"https://youtu.be/uvqxiXiA9eM\">Seiryoku-Zenyo-Kokumin-Taiiku</a>\n" +
                        "Además, adjuntamos este documento informativo que complementa a la Circular Nº 27 sobre el próximo curso de Katas del Club Pandy.</p>\n" +
                        "<a href=\"https://fmjudo.es//attachments/article/4188/27%20Reuni%C3%B3n%20Profesores%20y%20Curso%20de%20Kata%20Club%20Pandy%20marzo.pdf\">Circular Nº 27 - 4ª Jornada - ACTIVIDADES DE KATA “CLUB PANDY”</a>\n",
                "/static/sampleImages/judoSample1.jpg",
                new Timestamp(System.currentTimeMillis())));
        posts.add(new Post(admin,
                "INFORMACIÓN COMPLETA curso de Kata del Club Pandy",
                "Ampliamos la información del Curso de Katas del Club Pandy, con estos documentos y vídeos-explicativos. También aprovechamos para recordar que la entrega de premios del sorteo de la 2ª Jornada se hará el mismo día 21 de marzo a las 11:00.<br>" +
                        "¡Recordad traer la papeleta ganadora!\n" +
                        "<a href=\"https://fmjudo.es/attachments/article/4801/Cto%20katas%20infantiles-3.pdf\">CUADRO DE CATEGORÍAS Y KATAS</a></p>\n" +
                        "<a href=\"https://www.youtube.com/watch?v=LhVvmFDLMo0\">VÍDEO EXPLICATIVO GRUPO 7</a></p>\n" +
                        "<a href=\"https://www.youtube.com/watch?v=VpLdKj5KvuM\">VÍDEO EXPLICATIVO GRUPO 6</a></p>\n" +
                        "<a href=\"https://youtu.be/uvqxiXiA9eM\">Seiryoku-Zenyo-Kokumin-Taiiku</a>\n" +
                        "Además, adjuntamos este documento informativo que complementa a la Circular Nº 27 sobre el próximo curso de Katas del Club Pandy.</p>\n" +
                        "<a href=\"https://fmjudo.es//attachments/article/4188/27%20Reuni%C3%B3n%20Profesores%20y%20Curso%20de%20Kata%20Club%20Pandy%20marzo.pdf\">Circular Nº 27 - 4ª Jornada - ACTIVIDADES DE KATA “CLUB PANDY”</a>\n",
                "/static/sampleImages/judoSample1.jpg",
                new Timestamp(System.currentTimeMillis())));
        posts.add(new Post(admin,
                "INFORMACIÓN COMPLETA curso de Kata del Club Pandy",
                "Ampliamos la información del Curso de Katas del Club Pandy, con estos documentos y vídeos-explicativos. También aprovechamos para recordar que la entrega de premios del sorteo de la 2ª Jornada se hará el mismo día 21 de marzo a las 11:00.<br>" +
                        "¡Recordad traer la papeleta ganadora!\n" +
                        "<a href=\"https://fmjudo.es/attachments/article/4801/Cto%20katas%20infantiles-3.pdf\">CUADRO DE CATEGORÍAS Y KATAS</a></p>\n" +
                        "<a href=\"https://www.youtube.com/watch?v=LhVvmFDLMo0\">VÍDEO EXPLICATIVO GRUPO 7</a></p>\n" +
                        "<a href=\"https://www.youtube.com/watch?v=VpLdKj5KvuM\">VÍDEO EXPLICATIVO GRUPO 6</a></p>\n" +
                        "<a href=\"https://youtu.be/uvqxiXiA9eM\">Seiryoku-Zenyo-Kokumin-Taiiku</a>\n" +
                        "Además, adjuntamos este documento informativo que complementa a la Circular Nº 27 sobre el próximo curso de Katas del Club Pandy.</p>\n" +
                        "<a href=\"https://fmjudo.es//attachments/article/4188/27%20Reuni%C3%B3n%20Profesores%20y%20Curso%20de%20Kata%20Club%20Pandy%20marzo.pdf\">Circular Nº 27 - 4ª Jornada - ACTIVIDADES DE KATA “CLUB PANDY”</a>\n",
                "/static/sampleImages/judoSample1.jpg",
                new Timestamp(System.currentTimeMillis())));
        postService.addAll(posts);
    }

}
