package springbootdatasource.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class CompetitionControllerTest {
    @Mock
    private CompetitionController competitionController;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(competitionController).build();
    }

    @Test
    public void testGetAllCompetitions() throws Exception {
        mockMvc.perform(get("/handball/competitions")).andExpect(status().isOk());
    }

    @Test
    public void testGetCompetition() throws Exception {
        mockMvc.perform(get("/handball/competitions/5")).andExpect(status().isOk());
    }
}
