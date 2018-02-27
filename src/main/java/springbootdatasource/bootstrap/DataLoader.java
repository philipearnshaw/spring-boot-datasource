package springbootdatasource.bootstrap;

import java.util.Calendar;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import springbootdatasource.model.Competition;
import springbootdatasource.model.Match;
import springbootdatasource.model.Team;
import springbootdatasource.repositories.CompetitionRepository;
import springbootdatasource.repositories.MatchRepository;
import springbootdatasource.repositories.TeamRepository;

@RequiredArgsConstructor
@Component
@Profile("default")
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final CompetitionRepository competitionRepository;
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
        loadCompetitionData();
        loadMatchAndTeamData();
    }

    private void loadCompetitionData() {
        competitionRepository.save(new Competition("competition-name-1", "short-name-1", "100"));
        competitionRepository.save(new Competition("competition-name-2", "short-name-2", "200")); 
        competitionRepository.save(new Competition("competition-name-3", "short-name-3", "300"));
    }
    
    private void loadMatchAndTeamData() {
        Team homeTeam = teamRepository.save(new Team("N", "team-name-1", "team-nickname-1", "team-short-name-1"));
        Team awayTeam = teamRepository.save(new Team("N", "team-name-2", "team-nickname-2", "team-short-name-2"));
        matchRepository.save(new Match(homeTeam, awayTeam, Calendar.getInstance()));
        
        homeTeam = teamRepository.save(new Team("Y", "team-name-3", "team-nickname-3", "team-short-name-3"));
        awayTeam = teamRepository.save(new Team("Y", "team-name-4", "team-nickname-4", "team-short-name-4"));
        matchRepository.save(new Match(homeTeam, awayTeam, Calendar.getInstance()));
    }
}
