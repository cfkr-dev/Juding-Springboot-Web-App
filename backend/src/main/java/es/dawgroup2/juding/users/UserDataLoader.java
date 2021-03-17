package es.dawgroup2.juding.users;

import es.dawgroup2.juding.auxTypes.belts.Belt;
import es.dawgroup2.juding.competitions.Competition;
import es.dawgroup2.juding.competitions.CompetitionService;
import es.dawgroup2.juding.main.DateService;
import es.dawgroup2.juding.main.image.ImageService;
import es.dawgroup2.juding.posts.Post;
import es.dawgroup2.juding.posts.PostService;
import es.dawgroup2.juding.auxTypes.gender.Gender;
import es.dawgroup2.juding.auxTypes.refereeRange.RefereeRange;
import es.dawgroup2.juding.auxTypes.roles.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

@Component

public class UserDataLoader {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImageService imageService;

    @Autowired
    DateService dateService;

    @Autowired
    PostService postService;

    @Autowired
    CompetitionService competitionService;

    /**
     * Generating user sample data and introducing it into database.
     *
     * @throws IOException Exception for input-output that can be caused by image uploads.
     */
    @PostConstruct
    public void userLoader() throws IOException, ParseException {
        // Passwords and security answers should be encrypted (Spring Security stuff is not introduced in this moment).
        User graham = new User("1234567890", "Diego", "Guerrero", Gender.H, 601234567, "d.guerrero.2018@alumnos.urjc.es", dateService.stringToDate("30/10/2000"), "12345678Z", "d.guerrero", "d.guerrero", "Diego?", "Diego", imageService.uploadProfileImage("/static/1234567890.jpg"), Belt.BAm, "Madrid Centro", 80 , null, Set.of(Role.C));
        userRepository.save(graham);
        userRepository.save(new User("1234567891", "Berta", "Pérez", Gender.M, 678945122, "b.perezpe.2018@alumnos.urjc.es", dateService.stringToDate("21/03/2000"), "12331231B", "b.perezpe", "b.perezpe", "Berta?", "Berta", imageService.uploadProfileImage("/static/1234567891.jpg"), Belt.VAz, "Madrid Sur I", 50, null, Set.of(Role.C)));
        userRepository.save(new User("1234567892", "Ismael", "González", Gender.H, 689523154, "i.gonzalezs.2018@alumnos.urjc.es", dateService.stringToDate("15/04/2000"), "12345534P", "i.gonzalezs", "i.gonzalezs", "Ismael?", "Ismael", imageService.uploadProfileImage("/static/1234567892.jpg"), Belt.AzM, "Madrid Centro", 60, null, Set.of(Role.C)));
        User joseLuisReferee = new User("JU-1234567893", "José Luis", "Toledano", Gender.H, 678956320, "jl.toledano.2018@alumnos.urjc.es", dateService.stringToDate("18/09/2000"), "00412323W", "jl.toledano.2018", "jl.toledano.2018", "JoseLuis?", "JoseLuis", imageService.uploadProfileImage("/static/JU-1234567893.jpg"), Belt.N5, "Madrid Sur III", 65, RefereeRange.E, Set.of(Role.R));
        userRepository.save(joseLuisReferee);
        userRepository.save(new User("JU-1234567985", "Serafín", "Miguélez", Gender.H, 689758489, "juding.noreply@gmail.com", dateService.stringToDate("23/11/2000"), "08723423Y", "ser.miguelez", "ser.miguelez", "Serafin?", "Serafin", imageService.uploadProfileImage("/static/1234567890.jpg"), Belt.N10, "Madrid Norte", 95, RefereeRange.S, Set.of(Role.R)));

        List<Post> postList = postService.findAll();
        for(Post p : postList){
            postService.updatingInfoPost(p.setAuthor(graham));
        }

        List<Competition> competitionList = competitionService.findAll();
        for (Competition c : competitionList){
            competitionService.updatingInfoCompetition(c.setReferee(joseLuisReferee));
        }
    }
}
