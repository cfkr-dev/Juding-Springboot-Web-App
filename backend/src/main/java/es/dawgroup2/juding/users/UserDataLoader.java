package es.dawgroup2.juding.users;

import es.dawgroup2.juding.belts.Belt;
import es.dawgroup2.juding.main.DateService;
import es.dawgroup2.juding.users.gender.Gender;
import es.dawgroup2.juding.users.refereeRange.RefereeRange;
import es.dawgroup2.juding.users.roles.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.sql.Date;
import java.text.ParseException;
import java.util.List;

@Component

public class UserDataLoader {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DateService dateService;

    /**
     * Generating user sample data and introducing it into database.
     * @throws IOException Exception for input-output that can be caused by image uploads.
     */
    @PostConstruct
    public void userLoader() throws IOException, ParseException {
        // Passwords and security answers should be encrypted (Spring Security stuff is not introduced in this moment).
        userRepository.save(new User("1234567890", "Diego", "Guerrero", "d.guerrero.2018@alumnos.urjc.es", 601234567, Gender.H, dateService.stringToDate("30/10/2000"), "12345678Z", "Madrid Centro", 80, Belt.B, "/static/1234567890.jpg", "d.guerrero", "diego", "Diego?", "Diego", List.of(Role.C)));
        userRepository.save(new User("1234567891", "Berta", "Pérez", "b.perezpe.2018@alumnos.urjc.es", 678945122, Gender.M, dateService.stringToDate("21/03/2000"), "12331231B", "Madrid Centro", 70, Belt.NV, "/static/1234567891.jpg", "b.perezpe", "berta", "Berta?", "Berta", List.of(Role.C)));
        userRepository.save(new User("1234567892", "Ismael", "González", "i.gonzalezs.2018@alumnos.urjc.es", 689523154, Gender.H, dateService.stringToDate("15/04/2000"), "12345534P", "Madrid Centro", 60, Belt.AmN, "/static/1234567892.jpg", "i.gonzalezs", "ismael", "Ismael?", "Ismael", List.of(Role.C)));
        userRepository.save(new User("JU-1234567893", "José Luis", "Toledano", "jl.toledano.2018@alumnos.urjc.es", 678956320, Gender.H, dateService.stringToDate("18/09/2000"), "00412323W", "Madrid Centro", 50, Belt.N4, "/static/JU-1234567893.jpg", "jl.toledano", "joseluis", "JoseLuis?", "JoseLuis", List.of(Role.R)).setRefereeRange(RefereeRange.E));
        userRepository.save(new User("JU-1234567894", "José Luis", "Pendiente", "diegogcarrasco@gmail.com", 678956321, Gender.H, dateService.stringToDate("18/09/1999"), "00412354W", "Madrid Centro", 40, Belt.N7, "/static/1234567890.jpg", "jl.pendiente", "jlpendiente", "JL2?", "JL2", List.of(Role.R)).setRefereeRange(RefereeRange.S));
    }
}
