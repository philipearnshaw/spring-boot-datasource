package springbootdatasource.controllers;

import static springbootdatasource.validators.EntityValidator.requireNonNull;

import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.RequiredArgsConstructor;
import springbootdatasource.exception.BadRequestException;
import springbootdatasource.model.Competition;
import springbootdatasource.model.profiles.CompetitionProfile;
import springbootdatasource.services.CompetitionService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/handball/competitions")
public class CompetitionController {

    private final CompetitionService competitionService;

    @JsonView(CompetitionProfile.DetailView.class)
    @GetMapping
    public ResponseEntity<Set<Competition>> getAllCompetitions() {
        return new ResponseEntity<Set<Competition>>(competitionService.findAllCompetitions(), HttpStatus.OK);
    }

    @JsonView(CompetitionProfile.DetailView.class)
    @GetMapping("/{competitionId}")
    public ResponseEntity<Competition> getCompetitionById(@PathVariable("competitionId") final String competitionId) {
        final Optional<Competition> competition = requireNonNull(competitionService.findByCompetitionId(Long.valueOf(competitionId)), competitionId);
        return new ResponseEntity<Competition>(competition.get(), HttpStatus.OK);
    }
        
    @JsonView(CompetitionProfile.DetailView.class)
    @PostMapping
    public ResponseEntity<Competition> postCompetition(@Valid @RequestBody final Competition competition) {
        if (competition.getCompetitionId() != null) {
            throw new BadRequestException("Competition id should not be set for a POST request");
        }
        return new ResponseEntity<Competition>(competitionService.saveCompetition(competition), HttpStatus.CREATED);
    }
    
    @JsonView(CompetitionProfile.DetailView.class)
    @PutMapping("/{competitionId}")
    public ResponseEntity<Competition> putCompetitionById(@PathVariable("competitionId") final String competitionId, @Valid @RequestBody Competition competition) {
        competition.setCompetitionId(Long.valueOf(competitionId));
        return new ResponseEntity<Competition>(competitionService.saveCompetition(competition), HttpStatus.OK);
    }
    
    @JsonView(CompetitionProfile.DetailView.class)
    @DeleteMapping("/{competitionId}")
    public ResponseEntity<Competition> deleteCompetitionById(@PathVariable String competitionId){
        final Optional<Competition> competition = requireNonNull(competitionService.findByCompetitionId(Long.valueOf(competitionId)), competitionId);
        competitionService.deleteById(Long.valueOf(competitionId));
        return new ResponseEntity<Competition>(competition.get(), HttpStatus.OK);
    }
}