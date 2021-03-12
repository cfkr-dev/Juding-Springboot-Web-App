package es.dawgroup2.juding.users;

import es.dawgroup2.juding.belts.Belt;
import es.dawgroup2.juding.users.gender.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.sql.Date;

@Component

public class UserDataLoader {

    @Autowired
    UserRepository userRepository;

    @PostConstruct
    public void userLoader() throws IOException {
        // Passwords and security answers should be encrypted (Spring Security stuff is not introduced in this moment).
        userRepository.save(new User("1234567890", "Diego", "Guerrero", "d.guerrero.2018@alumnos.urjc.es", 601234567, Gender.H, new Date(953596800), "12345678Z", "Madrid Centro", 80, Belt.B, "/static/1234567890.jpg", "d.guerrero", "diego", "Question?", "Answer", 1));
        userRepository.save(new User("1234567891", "Berta", "Pérez", "b.perezpe.2018@alumnos.urjc.es", 678945122, Gender.M, new Date(953596800), "12331231B", "Madrid Centro", 70, Belt.NV, "/static/1234567891.jpg", "b.perezpe", "berta", "Question?", "Answer", 1));
        userRepository.save(new User("1234567892", "Ismael", "González", "i.gonzalezs.2018@alumnos.urjc.es", 689523154, Gender.H, new Date(953596800), "12345534P", "Madrid Centro", 60, Belt.AmN, "/static/1234567892.jpg", "i.gonzalezs", "ismael", "Question?", "Answer", 1));
        userRepository.save(new User("JU-1234567893", "José Luis", "Toledano", "jl.toledano.2018@alumnos.urjc.es", 678956320, Gender.H, new Date(953596800), "00412323W", "Madrid Centro", 50, Belt.N4, "/static/JU-1234567893.jpg", "jl.toledano", "joseluis", "Question?", "Answer", 2).setRefereeRange(1));
        userRepository.save(new User("JU-1234567894", "José Luis", "Pendiente", "jl.pendiente.2018@alumnos.urjc.es", 678956321, Gender.H, new Date(953596800), "00412354W", "Madrid Centro", 40, Belt.N7, "/static/1234567890.jpg", "jl.pendiente", "jlpendiente", "Question?", "Answer", 2).setRefereeRange(0));
    }
}
