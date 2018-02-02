package springbootdatasource.services;

import java.util.Optional;
import java.util.Set;

import springbootdatasource.model.Competition;

public interface CompetitionService {

	public Set<Competition> findAllCompetitions();
	public Optional<Competition> findByCompetitionId(final Long competitionId);
	public Competition saveCompetition(final Competition competition);
    public void deleteById(final Long competitionId);
}
