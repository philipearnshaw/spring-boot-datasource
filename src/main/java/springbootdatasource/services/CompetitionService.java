package springbootdatasource.services;

import java.util.Optional;
import java.util.Set;

import springbootdatasource.model.Competition;

public interface CompetitionService {

	public Set<Competition> findAllCompetitions();
	public Optional<Competition> findCompetition(final Long competitionId);
}
