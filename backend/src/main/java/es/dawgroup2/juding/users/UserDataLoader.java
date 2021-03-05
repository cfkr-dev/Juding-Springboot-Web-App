package es.dawgroup2.juding.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Date;

@Component
public class UserDataLoader {
    @Autowired
    UserRepository userRepository;

    @PostConstruct
    public void userLoader(){
        // Passwords and security answers should be encrypted (Spring Security stuff is not introduced in this moment).
        userRepository.save(new User("1234567890", "Diego", "Guerrero", "d.guerrero.2018@alumnos.urjc.es", 601234567, 'H', new Date(972864000), "12345678Z", "Madrid Centro", 80, "BAm", "d.guerrero", "diego", "Question?", "Answer", 1));
        userRepository.save(new User("1234567891", "Alberto", "Pérez", "a.perezpe.2018@alumnos.urjc.es", 678945122, 'H', new Date(953596800), "12331231-B", "Madrid Centro", 80, "V", "a.perezpe", "alberto", "Question?", "Answer", 1));
        userRepository.save(new User("1234567892", "Ismael", "González", "i.gonzalezs.2018@alumnos.urjc.es", 689523154, 'H', new Date(955756800), "12345534P", "Madrid Centro", 80, "AzM", "i.gonzalezs", "ismael", "Question?", "Answer", 1));
        userRepository.save(new User("JU-1234567893", "José Luis", "Toledano", "jl.toledano.2018@alumnos.urjc.es", 678956320, 'H', new Date(969235200), "00412323W", "Madrid Centro", 80, "N3", "jl.toledano", "joseluis", "Question?", "Answer", 2));
    }
}
