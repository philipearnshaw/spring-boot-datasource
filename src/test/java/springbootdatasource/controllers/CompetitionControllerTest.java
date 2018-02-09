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
 
        mockMvc.perform(get("/handball/competitions/1")).andExpect(status().isOk());
        verify(competitionService, times(1)).findByCompetitionId(anyLong());
    }
    
    @Test
    public void testCompetitionById_ShouldThrowNotFoundException() throws Exception {

        when(competitionService.findByCompetitionId(anyLong())).thenReturn(Optional.empty());
        
        mockMvc.perform(get("/handball/competitions/5"))
            .andExpect(status().isNotFound());
        
        verify(competitionService, times(1)).findByCompetitionId(anyLong());
    }
    
    @Test
    public void testCompetitionById_ShouldThrowBadRequestException() throws Exception {
        
        mockMvc.perform(get("/handball/competitions/f"))
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
        
        mockMvc.perform(put("/handball/competitions/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(optionalCompetition.get()))
            )
            .andExpect(status().isOk());
        
        verify(competitionService, times(1)).saveCompetition(any(Competition.class));
    }
    
    @Test
    public void testPutCompetitionById_ShouldGiveBadRequest() throws Exception {
        
        mockMvc.perform(put("/handball/competitions/a")
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
        
        when(competitionService.findByCompetitionId(1L)).thenReturn(optionalCompetition);
        
        mockMvc.perform(delete("/handball/competitions/1"))
            .andExpect(status().isOk());
        
        verify(competitionService, times(1)).deleteById(anyLong());
    }

    
    @Test
    public void testDeleteCompetitionById_ShouldGiveBadRequest() throws Exception {
        
        mockMvc.perform(delete("/handball/competitions/a")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
        
        verify(competitionService, never()).deleteById(anyLong());
    }
    
    /**
     * Helper methods.
     */
    private Optional<Competition> buildCompetitionAsOptional() {
        final Competition competition = new Competition();
        competition.setCompetitionId(1L);
        competition.setName("competition-name");
        competition.setOwner("competition-owner");
        competition.setBudget(50);
        
        final Optional<Competition> optionalCompetition = Optional.of(competition);
        return optionalCompetition;
    }
}
