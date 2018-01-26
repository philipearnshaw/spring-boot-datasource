package springbootdatasource.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import lombok.Data;
import springbootdatasource.model.Competition;
import springbootdatasource.repositories.CompetitionRepository;

@Data
@Component
@Profile("dev")
public class CompetitionLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final CompetitionRepository competitionRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
        init();
    }

    private void init() {
        competitionRepository.save(new Competition("competition-name-1"));
        competitionRepository.save(new Competition("competition-name-2"));
        competitionRepository.save(new Competition("competition-name-3"));
    }
}
