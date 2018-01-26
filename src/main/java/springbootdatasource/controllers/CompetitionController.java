package springbootdatasource.controllers;

import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import springbootdatasource.model.Competition;
import springbootdatasource.services.CompetitionService;

@Data
@RestController
@RequestMapping("/handball/competitions")
public class CompetitionController {

    private final CompetitionService competitionService;

    @RequestMapping
    public ResponseEntity<Set<Competition>> getAllCompetitions() {
        return new ResponseEntity<Set<Competition>>(competitionService.findAllCompetitions(), null, HttpStatus.OK);
    }

    @RequestMapping("/{competitionId}")
    public ResponseEntity<Competition> getCompetition(@PathVariable("competitionId") final Long competitionId) {
        final Optional<Competition> competition = competitionService.getCompetition(competitionId);
        return new ResponseEntity<Competition>(competition.get(), null, HttpStatus.OK);
    }
}