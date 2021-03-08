package es.dawgroup2.juding.competitions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;

@Component
public class CompetitionDataLoader {
    @Autowired
    CompetitionRepository competitionRepository;

    @PostConstruct
    public void competitionLoader{
        competitionRepository.save(new Competition("1234567890123", "Campeonato Champiñon", "nah","45","50",new Timestamp(953596800),new Timestamp(953596800),"JU-1234567890","1"));
    }
}
