package springbootdatasource.services;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import springbootdatasource.mapper.CompetitionMapper;
import springbootdatasource.model.Competition;
import springbootdatasource.repositories.CompetitionRepository;

@RequiredArgsConstructor
@Service
public class CompetitionServiceImpl implements CompetitionService {

    private final CompetitionRepository competitionRepository;

    @Override
    public Set<Competition> findAllCompetitions() {
        return CompetitionMapper.INSTANCE.iterableCompetitionsToSetCompetitions(competitionRepository.findAll());
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