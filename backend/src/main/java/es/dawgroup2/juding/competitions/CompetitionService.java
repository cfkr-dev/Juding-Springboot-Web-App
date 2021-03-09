package es.dawgroup2.juding.competitions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompetitionService {

    @Autowired
    private CompetitionRepository competitionRepository;

    public void deleteById(String idCompetition) {
        competitionRepository.deleteById(Integer.parseInt(idCompetition));
    }

    public Competition findById(String idCompetition) {
        return competitionRepository.findById(Integer.parseInt(idCompetition)).orElseThrow();
    }

    public List<Competition> findAll() {
        return competitionRepository.findAll();
    }

    public void updatingInfoCompetition(Competition competition) {
        competitionRepository.findById(competition.getIdCompetition()).orElseThrow();
        competitionRepository.save(competition);
    }
    public void add(Competition competition){
        competitionRepository.save(competition);
    }
}
