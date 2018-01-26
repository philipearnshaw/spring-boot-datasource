package springbootdatasource.repositories;

import static org.junit.Assert.assertEquals;

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

    @Autowired
    CompetitionRepository competitionRepository;
    
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testFindByName() {
        Optional<Competition> competition = competitionRepository.findByName("competition-name-1");
        assertEquals("competition-name-1", competition.get().getName());
    }
}
