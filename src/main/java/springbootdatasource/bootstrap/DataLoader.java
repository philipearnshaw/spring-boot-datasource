package springbootdatasource.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import springbootdatasource.model.Competition;
import springbootdatasource.model.Team;
import springbootdatasource.repositories.CompetitionRepository;
import springbootdatasource.repositories.TeamRepository;

@RequiredArgsConstructor
@Component
@Profile("default")
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final CompetitionRepository competitionRepository;
    private final TeamRepository teamRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
        loadCompetitionData();
        loadTeamData();
    }

    private void loadCompetitionData() {
        competitionRepository.save(new Competition("competition-name-1", "short-name-1", "100"));
        competitionRepository.save(new Competition("competition-name-2", "short-name-2", "200")); 
        competitionRepository.save(new Competition("competition-name-3", "short-name-3", "300"));
    }
    
    private void loadTeamData() {
        teamRepository.save(new Team("Y", "team-name-1", "team-nickname-1", "team-short-name-1"));
        teamRepository.save(new Team("N", "team-name-2", "team-nickname-2", "team-short-name-2"));
        teamRepository.save(new Team("Y", "team-name-3", "team-nickname-3", "team-short-name-3"));
    }
}
