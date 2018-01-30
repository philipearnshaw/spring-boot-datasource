package springbootdatasource.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import springbootdatasource.model.Competition;
import springbootdatasource.services.CompetitionService;

public class CompetitionControllerTest {

    final ObjectMapper objectMapper = new ObjectMapper();
    
    @Mock
    private CompetitionService competitionService;
    
    private MockMvc mockMvc;
    private CompetitionController competitionController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        competitionController = new CompetitionController(competitionService);
        
        mockMvc = MockMvcBuilders.standaloneSetup(competitionController).setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    /**
     * GetAllCompetitions tests
     */
    @Test
    public void testGetAllCompetitions() throws Exception {
        mockMvc.perform(get("/handball/competitions")).andExpect(status().isOk());
        
        verify(competitionService, times(1)).findAllCompetitions();
    }

    /**
     * GetCompetition tests
     */
    @Test
    public void testGetCompetition() throws Exception {
        
        Optional<Competition> optionalCompetition = Optional.of(new Competition());
        
        when(competitionService.findCompetition(Mockito.anyLong())).thenReturn(optionalCompetition);
 
        mockMvc.perform(get("/handball/competitions/1")).andExpect(status().isOk());
        verify(competitionService, times(1)).findCompetition(anyLong());
    }
    
    @Test
    public void testGetCompetition_ShouldThrowNotFoundException() throws Exception {

        when(competitionService.findCompetition(anyLong())).thenReturn(Optional.empty());
        
        mockMvc.perform(get("/handball/competitions/5"))
            .andExpect(status().isNotFound());
        
        verify(competitionService, times(1)).findCompetition(anyLong());
    }
    
    @Test
    public void testGetCompetition_ShouldThrowBadRequestException() throws Exception {
        
        mockMvc.perform(get("/handball/competitions/f"))
            .andExpect(status().isBadRequest());
        
        verify(competitionService, never()).findCompetition(anyLong());
    }
    
    /**
     * PostCompetition tests
     */
    @Test
    public void testPostCompetition() throws Exception {
        
        Competition competition = new Competition();
        competition.setCompetitionId(1L);
        competition.setName("competition-name");
        
        when(competitionService.saveCompetition(any(Competition.class))).thenReturn(new Competition());
        
        mockMvc.perform(post("/handball/competitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(competition))
            )
            .andExpect(status().isCreated());
        
        verify(competitionService, times(1)).saveCompetition(any(Competition.class));
    }

    
    /**
     * PutCompetition tests
     */
    @Test
    public void testPutCompetition() throws Exception {
        
        Competition competition = new Competition();
        competition.setCompetitionId(1L);
        competition.setName("competition-name");
        
        when(competitionService.saveCompetition(any(Competition.class))).thenReturn(new Competition());
        
        mockMvc.perform(put("/handball/competitions/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(competition))
            )
            .andExpect(status().isOk());
        
        verify(competitionService, times(1)).saveCompetition(any(Competition.class));
    }
    
    @Test
    public void testPutCompetition_ShouldGiveBadRequest() throws Exception {
        
        Competition competition = new Competition();
        competition.setCompetitionId(1L);
        competition.setName("competition-name");
        
        when(competitionService.saveCompetition(any(Competition.class))).thenReturn(new Competition());
        
        mockMvc.perform(put("/handball/competitions/a")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(competition))
            )
            .andExpect(status().isBadRequest());
    }
}
