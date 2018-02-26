package springbootdatasource.services;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import springbootdatasource.model.Competition;
import springbootdatasource.repositories.CompetitionRepository;

@RequiredArgsConstructor
@Service
public class CompetitionServiceImpl implements CompetitionService {

    private final CompetitionRepository competitionRepository;

    @Override
    public Set<Competition> findAllCompetitions() {
        return StreamSupport.stream(competitionRepository.findAll().spliterator(), false)
        .collect(Collectors.toSet());
    }

    @Override
    public Optional<Competition> findByCompetitionId(final Long competitionId) {
        return competitionRepository.findById(competitionId);
    }

    @Override
    public Competition saveCompetition(final Competition competition) {
        return competitionRepository.save(competition);
    }
    
    @Override
    public void deleteById(final Long competitionId) {
        competitionRepository.deleteById(competitionId);
    }
}