package springbootdatasource.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import springbootdatasource.model.Competition;
import springbootdatasource.services.CompetitionService;

public class CompetitionControllerTest {

    private static final long KNOWN_COMPETITION_ID = 1L;
    private static final long UNKNOWN_COMPETITION_ID = 99L;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Optional<Competition> optionalCompetition;
    
    @Mock
    private CompetitionService competitionService;
    
    private MockMvc mockMvc;
    private CompetitionController competitionController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        optionalCompetition = buildCompetitionAsOptional();
        competitionController = new CompetitionController(competitionService);
        mockMvc = MockMvcBuilders.standaloneSetup(competitionController).setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    /**
     * getAllCompetitions() tests
     */
    @Test
    public void testGetAllCompetitions() throws Exception {
        mockMvc.perform(get("/handball/competitions")).andExpect(status().isOk());
        
        verify(competitionService, times(1)).findAllCompetitions();
    }

    /**
     * getCompetitionById() tests
     */
    @Test
    public void testCompetitionById() throws Exception {
        
        when(competitionService.findByCompetitionId(anyLong())).thenReturn(optionalCompetition);
 
        mockMvc.perform(get("/handball/competitions/{competitionId}", KNOWN_COMPETITION_ID)).andExpect(status().isOk());
        verify(competitionService, times(1)).findByCompetitionId(KNOWN_COMPETITION_ID);
    }
    
    @Test
    public void testCompetitionById_ShouldThrowNotFoundException() throws Exception {

        when(competitionService.findByCompetitionId(anyLong())).thenReturn(Optional.empty());
        
        mockMvc.perform(get("/handball/competitions/{competitionId}", UNKNOWN_COMPETITION_ID))
            .andExpect(status().isNotFound());
        
        verify(competitionService, times(1)).findByCompetitionId(UNKNOWN_COMPETITION_ID);
    }
    
    @Test
    public void testCompetitionById_ShouldThrowBadRequestException() throws Exception {
        
        mockMvc.perform(get("/handball/competitions/{competitionId}", "f"))
            .andExpect(status().isBadRequest());
        
        verify(competitionService, never()).findByCompetitionId(anyLong());
    }
    
    /**
     * postCompetition() tests
     */
    @Test
    public void testPostCompetition() throws Exception {
        
        final Competition competition = buildCompetitionAsOptional().get();
        competition.setCompetitionId(null);
        
        when(competitionService.saveCompetition(competition)).thenReturn(new Competition());
        
        mockMvc.perform(post("/handball/competitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(competition))
            )
            .andExpect(status().isCreated());
        
        verify(competitionService, times(1)).saveCompetition(any(Competition.class));
    }

    @Test
    public void testPostCompetition_ShouldGiveBadRequestAsIdSet() throws Exception {
        
        mockMvc.perform(post("/handball/competitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(optionalCompetition.get()))
            )
            .andExpect(status().isBadRequest());
        
        verify(competitionService, never()).saveCompetition(any(Competition.class));
    }
    
    /**
     * putCompetitionById() tests
     */
    @Test
    public void testPutCompetitionById() throws Exception {

        when(competitionService.saveCompetition(optionalCompetition.get())).thenReturn(optionalCompetition.get());
        
        mockMvc.perform(put("/handball/competitions/{competitionId}", KNOWN_COMPETITION_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(optionalCompetition.get()))
            )
            .andExpect(status().isOk());
        
        verify(competitionService, times(1)).saveCompetition(any(Competition.class));
    }
    
    @Test
    public void testPutCompetitionById_ShouldGiveBadRequest() throws Exception {
        
        mockMvc.perform(put("/handball/competitions/{competitionId}", "a")
            .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest());
        
        verify(competitionService, never()).saveCompetition(any(Competition.class));
    }
    
    /**
     * deleteCompetitionById() Tests
     */
    @Test
    public void testDeleteCompetitionById() throws Exception {
        
        when(competitionService.findByCompetitionId(KNOWN_COMPETITION_ID)).thenReturn(optionalCompetition);
        
        mockMvc.perform(delete("/handball/competitions/{competitionId}", KNOWN_COMPETITION_ID))
            .andExpect(status().isOk());
        
        verify(competitionService, times(1)).deleteById(KNOWN_COMPETITION_ID);
    }

    
    @Test
    public void testDeleteCompetitionById_ShouldGiveBadRequest() throws Exception {
        
        mockMvc.perform(delete("/handball/competitions/{competitionId}", "a")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
        
        verify(competitionService, never()).deleteById(anyLong());
    }
    
    /**
     * Helper methods.
     */
    private Optional<Competition> buildCompetitionAsOptional() {
        final Competition competition = new Competition();
        competition.setCompetitionId(KNOWN_COMPETITION_ID);
        competition.setName("competition-name");
        competition.setShortName("competition-short-name");
        competition.setShortCode("competition-short-code");
        
        final Optional<Competition> optionalCompetition = Optional.of(competition);
        return optionalCompetition;
    }
}
