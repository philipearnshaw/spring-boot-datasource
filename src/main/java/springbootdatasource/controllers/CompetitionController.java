package springbootdatasource.controllers;

import static springbootdatasource.validators.EntityValidator.requireNonNull;

import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import springbootdatasource.exception.BadRequestException;
import springbootdatasource.model.Competition;
import springbootdatasource.services.CompetitionService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/handball/competitions")
public class CompetitionController {

    private final CompetitionService competitionService;

    @GetMapping
    public ResponseEntity<Set<Competition>> getAllCompetitions() {
        log.debug("/handball/competitions request");
        return new ResponseEntity<Set<Competition>>(competitionService.findAllCompetitions(), HttpStatus.OK);
    }

    @GetMapping("/{competitionId}")
    public ResponseEntity<Competition> getCompetition(@PathVariable("competitionId") final String competitionId) {
        
        log.debug("/handball/competitions/" + competitionId +  " request");
        
        final Optional<Competition> competition = requireNonNull(competitionService.findCompetition(Long.valueOf(competitionId)), competitionId);
        return new ResponseEntity<Competition>(competition.get(), HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Competition> postCompetition(@RequestBody final Competition competition) {
        if (competition.getCompetitionId() != null) {
            throw new BadRequestException("Competition id should not be set for a POST request");
        }
        return new ResponseEntity<Competition>(competitionService.saveCompetition(competition), HttpStatus.CREATED);
    }
    
    @PutMapping("/{competitionId}")
    public ResponseEntity<Competition> putCompetition(@PathVariable("competitionId") final String competitionId, @RequestBody Competition competition) {
        competition.setCompetitionId(Long.valueOf(competitionId));
        return new ResponseEntity<Competition>(competitionService.saveCompetition(competition), HttpStatus.OK);
    }
}