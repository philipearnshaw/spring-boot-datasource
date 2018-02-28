package springbootdatasource.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import springbootdatasource.model.Competition;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompetitionRepositoryIT {

    private static final String COMPETITION_NAME = "name";
    
    @Autowired
    CompetitionRepository competitionRepository;
    
    @Before
    public void setUp() throws Exception {
        final Competition competition = new Competition(COMPETITION_NAME, "shortName", "shortCode");
        competitionRepository.save(competition);
    }

    @Test
    public void testFindByName_ShouldNotReturnCompetition() {
        
        // when
        final Optional<Competition> optionalCompetition = competitionRepository.findByName("unknown-name");
        
        // then
        assertThat(optionalCompetition.isPresent()).isFalse();
    }
    
    @Test
    public void testFindByName_ShouldReturnCompetition() {
        
        // when
        final Optional<Competition> optionalCompetition = competitionRepository.findByName(COMPETITION_NAME);
        
        // then
        assertThat(optionalCompetition.isPresent()).isTrue();
        assertThat(optionalCompetition.get().getName()).isEqualTo(COMPETITION_NAME);
    }
}
