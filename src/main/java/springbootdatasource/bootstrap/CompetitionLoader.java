package springbootdatasource.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import springbootdatasource.model.Competition;
import springbootdatasource.repositories.CompetitionRepository;

@RequiredArgsConstructor
@Component
@Profile("default")
public class CompetitionLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final CompetitionRepository competitionRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
        init();
    }

    private void init() {
        competitionRepository.save(new Competition("competition-name-1", "short-name-1", "100"));
        competitionRepository.save(new Competition("competition-name-2", "short-name-2", "200")); 
        competitionRepository.save(new Competition("competition-name-3", "short-name-3", "300"));
    }
}
