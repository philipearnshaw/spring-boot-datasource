package springbootdatasource.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        final Optional<Competition> optionalCompetition = competitionRepository.findByName("unknown-name");
        
        assertFalse("Competition is present", optionalCompetition.isPresent());
    }
    
    @Test
    public void testFindByName_ShouldReturnCompetition() {
        final Optional<Competition> optionalCompetition = competitionRepository.findByName(COMPETITION_NAME);
        
        assertTrue("Competition is not present", optionalCompetition.isPresent());
        assertEquals("Competition should be called " + COMPETITION_NAME, COMPETITION_NAME, optionalCompetition.get().getName());
    }
}
