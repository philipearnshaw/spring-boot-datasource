package springbootdatasource.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import springbootdatasource.model.Competition;
import springbootdatasource.services.CompetitionService;

@RunWith(SpringRunner.class)
@WebMvcTest(CompetitionController.class)
public class CompetitionControllerTest {

    private static final long KNOWN_COMPETITION_ID = 1L;
    private static final long UNKNOWN_COMPETITION_ID = 99L;
    
    @MockBean                   // Tell Spring to inject the mock not the real bean.
    private CompetitionService competitionService;
    
    @Autowired
    private MockMvc mvc;
    
    private JacksonTester<Competition> competitionJson;
    private JacksonTester<Set<Competition>> competitionsJson;
    private MockHttpServletResponse response;

    @Before
    public void setUp() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(Include.NON_NULL); // ignore null values
        
        JacksonTester.initFields(this, objectMapper);
    }

    /**
     * getAllCompetitions() tests
     */
    @Test
    public void testGetAllCompetitions() throws Exception {
        final Set<Competition> competitions = new HashSet<>();
        competitions.add(buildCompetition());
        competitions.add(buildCompetition());
        when(competitionService.findAllCompetitions()).thenReturn(competitions);
        
        response = mvc.perform(
                get("/handball/competitions")
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();
        
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(competitionsJson.write(competitions).getJson());
        verify(competitionService).findAllCompetitions();
    }

    /**
     * getCompetitionById() tests
     */
    @Test
    public void testCompetitionById() throws Exception {
        
        when(competitionService.findByCompetitionId(anyLong())).thenReturn(Optional.of(buildCompetition()));
 
        response = mvc.perform(
                get("/handball/competitions/{competitionId}", KNOWN_COMPETITION_ID)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();
       
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(competitionJson.write(buildCompetition()).getJson());
        verify(competitionService).findByCompetitionId(KNOWN_COMPETITION_ID);
    }
    
    @Test
    public void testCompetitionById_ShouldThrowNotFoundException() throws Exception {

        when(competitionService.findByCompetitionId(anyLong())).thenReturn(Optional.empty());
        
        response = mvc.perform(
                get("/handball/competitions/{competitionId}", UNKNOWN_COMPETITION_ID)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();
        
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        verify(competitionService).findByCompetitionId(UNKNOWN_COMPETITION_ID);
    }
    
    @Test
    public void testCompetitionById_ShouldThrowBadRequestException() throws Exception {
        
        response = mvc.perform(
                get("/handball/competitions/{competitionId}", "f")
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();
        
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        verify(competitionService, never()).findByCompetitionId(anyLong());
    }
    
    /**
     * postCompetition() tests
     */
    @Test
    public void testPostCompetition() throws Exception {
        
        final Competition postCompetition = buildCompetition();
        postCompetition.setCompetitionId(null);
        
        when(competitionService.saveCompetition(any(Competition.class))).thenReturn(buildCompetition());
        
        response = mvc.perform(
                post("/handball/competitions")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(competitionJson.write(postCompetition).getJson()))
            .andReturn().getResponse();
        
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(competitionJson.write(buildCompetition()).getJson());
        verify(competitionService).saveCompetition(any(Competition.class));
    }

    @Test
    public void testPostCompetition_ShouldGiveBadRequestAsIdSet() throws Exception {
        
        response = mvc.perform(
                post("/handball/competitions")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(competitionJson.write(buildCompetition()).getJson()))
            .andReturn().getResponse();
        
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        verify(competitionService, never()).saveCompetition(any(Competition.class));
    }
    
    /**
     * putCompetitionById() tests
     */
    @Test
    public void testPutCompetitionById() throws Exception {
        Competition putViewCompetition = buildCompetition();

        when(competitionService.saveCompetition(any(Competition.class))).thenReturn(buildCompetition());
        
        response = mvc.perform(
                put("/handball/competitions/{competitionId}", KNOWN_COMPETITION_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(competitionJson.write(buildCompetition()).getJson()))
            .andReturn().getResponse();
        
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(competitionJson.write(putViewCompetition).getJson());
        verify(competitionService).saveCompetition(any(Competition.class));
    }
    
    @Test
    public void testPutCompetitionById_ShouldGiveBadRequest() throws Exception {
        
        response = mvc.perform(
                put("/handball/competitions/{competitionId}", "a")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();
        
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        verify(competitionService, never()).saveCompetition(any(Competition.class));
    }
    
    /**
     * deleteCompetitionById() Tests
     */
    @Test
    public void testDeleteCompetitionById() throws Exception {
        
        when(competitionService.findByCompetitionId(KNOWN_COMPETITION_ID)).thenReturn(Optional.of(buildCompetition()));
        
        response = mvc.perform(
                delete("/handball/competitions/{competitionId}", KNOWN_COMPETITION_ID)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();
        
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(competitionJson.write(buildCompetition()).getJson());
        
        verify(competitionService).deleteById(KNOWN_COMPETITION_ID);
    }
    
    @Test
    public void testDeleteCompetitionById_ShouldGiveBadRequest() throws Exception {

        response = mvc.perform(
                delete("/handball/competitions/{competitionId}", "a")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        verify(competitionService, never()).deleteById(anyLong());
    }
    
    /**
     * Helper methods.
     */
    private Competition buildCompetition() {
        final Competition competition = new Competition();
        competition.setCompetitionId(KNOWN_COMPETITION_ID);
        competition.setName("competition-name");
        competition.setShortName("competition-short-name");
        competition.setShortCode("competition-short-code");
        
        return competition;
    }
}
