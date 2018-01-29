package springbootdatasource.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import springbootdatasource.model.Competition;
import springbootdatasource.services.CompetitionService;

public class CompetitionControllerTest {

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

    @Test
    public void testGetAllCompetitions() throws Exception {
        mockMvc.perform(get("/handball/competitions")).andExpect(status().isOk());
        
        verify(competitionService, times(1)).findAllCompetitions();
    }

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
}
