package springbootdatasource.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
        final Set<Competition> competitions = new HashSet<>();
        competitionRepository.findAll().forEach(competitions::add);
        return competitions;
    }

    @Override
    public Optional<Competition> findCompetition(final Long competitionId) {
        return competitionRepository.findById(competitionId);
    }

    @Override
    public Competition saveCompetition(final Competition competition) {
        return competitionRepository.save(competition);
    }
}