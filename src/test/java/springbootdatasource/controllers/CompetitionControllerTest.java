package springbootdatasource.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
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
        
        // given
        final Set<Competition> competitions = new HashSet<>();
        competitions.add(buildCompetition());
        competitions.add(buildCompetition());
        
        given(competitionService.findAllCompetitions()).willReturn(competitions);
        
        // when
        response = mvc.perform(
                get(CompetitionController.COMPETITION_ROOT_URI)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();
        
        // then
        then(competitionService).should(times(1)).findAllCompetitions();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(competitionsJson.write(competitions).getJson());
    }

    /**
     * getCompetitionById() tests
     */
    @Test
    public void testCompetitionById() throws Exception {
        
        // given
        given(competitionService.findByCompetitionId(anyLong())).willReturn(Optional.of(buildCompetition()));
 
        // when
        response = mvc.perform(
                get(getCompetitionRootUriWithId(), KNOWN_COMPETITION_ID)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();
       
        // then
        then(competitionService).should(times(1)).findByCompetitionId(KNOWN_COMPETITION_ID);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(competitionJson.write(buildCompetition()).getJson());
    }
    
    @Test
    public void testCompetitionById_ShouldThrowNotFoundException() throws Exception {

        // given
        given(competitionService.findByCompetitionId(anyLong())).willReturn(Optional.empty());
        
        // when
        response = mvc.perform(
                get(getCompetitionRootUriWithId(), UNKNOWN_COMPETITION_ID)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();
        
        // then
        then(competitionService).should(times(1)).findByCompetitionId(UNKNOWN_COMPETITION_ID);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
    
    @Test
    public void testCompetitionById_ShouldThrowBadRequestException() throws Exception {
        
        // when
        response = mvc.perform(
                get(getCompetitionRootUriWithId(), "f")
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();
        
        // then
        then(competitionService).should(never()).findByCompetitionId(anyLong());
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
    
    /**
     * postCompetition() tests
     */
    @Test
    public void testPostCompetition() throws Exception {
        
        // given
        final Competition postCompetition = buildCompetition();
        postCompetition.setCompetitionId(null);
        
        given(competitionService.saveCompetition(any(Competition.class))).willReturn(buildCompetition());
        
        // when
        response = mvc.perform(
                post(CompetitionController.COMPETITION_ROOT_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(competitionJson.write(postCompetition).getJson()))
            .andReturn().getResponse();
        
        // then
        then(competitionService).should(times(1)).saveCompetition(any(Competition.class));
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(competitionJson.write(buildCompetition()).getJson());
    }

    @Test
    public void testPostCompetition_ShouldGiveBadRequestAsIdSet() throws Exception {
        
        // when
        response = mvc.perform(
                post(CompetitionController.COMPETITION_ROOT_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(competitionJson.write(buildCompetition()).getJson()))
            .andReturn().getResponse();
        
        // then
        then(competitionService).should(never()).saveCompetition(any(Competition.class));
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
    
    /**
     * putCompetitionById() tests
     */
    @Test
    public void testPutCompetitionById() throws Exception {
        
        // given
        Competition putViewCompetition = buildCompetition();
        given(competitionService.saveCompetition(any(Competition.class))).willReturn(buildCompetition());
        
        // when
        response = mvc.perform(
                put(getCompetitionRootUriWithId(), KNOWN_COMPETITION_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(competitionJson.write(buildCompetition()).getJson()))
            .andReturn().getResponse();
        
        // then
        then(competitionService).should(times(1)).saveCompetition(any(Competition.class));
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(competitionJson.write(putViewCompetition).getJson());
    }
    
    @Test
    public void testPutCompetitionById_ShouldGiveBadRequestForDifferentIdInBodyAndResource() throws Exception {
        
        // when
        response = mvc.perform(
                put(getCompetitionRootUriWithId(), "5")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(competitionJson.write(buildCompetition()).getJson()))
            .andReturn().getResponse();
        
        // then
        then(competitionService).should(never()).saveCompetition(any(Competition.class));
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
    
    @Test
    public void testPutCompetitionById_ShouldGiveBadRequestForAlphaId() throws Exception {
        
        // when
        response = mvc.perform(
                put(getCompetitionRootUriWithId(), "a")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();
        
        // then
        then(competitionService).should(never()).saveCompetition(any(Competition.class));
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
    
    /**
     * deleteCompetitionById() Tests
     */
    @Test
    public void testDeleteCompetitionById() throws Exception {
        
        // given
        given(competitionService.findByCompetitionId(KNOWN_COMPETITION_ID)).willReturn(Optional.of(buildCompetition()));
        
        // when
        response = mvc.perform(
                delete(getCompetitionRootUriWithId(), KNOWN_COMPETITION_ID)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();
        
        // then
        then(competitionService).should(times(1)).deleteById(KNOWN_COMPETITION_ID);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(competitionJson.write(buildCompetition()).getJson());
    }
    
    @Test
    public void testDeleteCompetitionById_ShouldGiveBadRequest() throws Exception {

        // when
        response = mvc.perform(
                delete(getCompetitionRootUriWithId(), "a")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

        // then
        then(competitionService).should(never()).deleteById(anyLong());
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
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
    
    private String getCompetitionRootUriWithId() {
        return CompetitionController.COMPETITION_ROOT_URI + "/{competitionId}";
    }
}
