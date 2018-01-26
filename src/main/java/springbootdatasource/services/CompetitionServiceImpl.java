package springbootdatasource.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.Data;
import springbootdatasource.model.Competition;
import springbootdatasource.repositories.CompetitionRepository;

@Data
@Service
public class CompetitionServiceImpl implements CompetitionService {

    private final CompetitionRepository competitionRepository;

    public Set<Competition> findAllCompetitions() {
        final Set<Competition> competitions = new HashSet<>();
        competitionRepository.findAll().forEach(competitions::add);
        return competitions;
    }

    public Optional<Competition> getCompetition(final Long competitionId) {
        return competitionRepository.findById(competitionId);
    }
}