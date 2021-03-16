package es.dawgroup2.juding.competitions;

import es.dawgroup2.juding.auxTypes.attendances.Attendance;
import es.dawgroup2.juding.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.Timestamp;

@Component
public class CompetitionDataLoader {
    @Autowired
    CompetitionRepository competitionRepository;

    @PostConstruct
    public void competitionLoader() throws IOException {
        competitionRepository.save(new Competition("Copa Platano","nah",45,50, new Timestamp(953596800),new Timestamp(953596800),null, Attendance.C,"/static/ey.jpg"));
        competitionRepository.save(new Competition("Campeonato Champiñon", "nah", 45, 50, new Timestamp(953596800), new Timestamp(953596800), null, Attendance.N,"/static/ey.jpg"));
        competitionRepository.save(new Competition("Campeonato Placeholder", "Buenos dias", 80, 100, new Timestamp(953596800), new Timestamp(953596800), null, Attendance.R,"/static/ey.jpg"));

    }
}
