package springbootdatasource.controllers;

import static springbootdatasource.validators.EntityValidator.requireNonNull;

import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import springbootdatasource.model.Competition;
import springbootdatasource.services.CompetitionService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/handball/competitions")
public class CompetitionController {

    private final CompetitionService competitionService;

    @GetMapping
    public ResponseEntity<Set<Competition>> getAllCompetitions() {
        return new ResponseEntity<Set<Competition>>(competitionService.findAllCompetitions(), null, HttpStatus.OK);
    }

    @GetMapping("/{competitionId}")
    public ResponseEntity<Competition> getCompetition(@PathVariable("competitionId") final String competitionId) {
        final Optional<Competition> competition = requireNonNull(competitionService.findCompetition(Long.valueOf(competitionId)), competitionId);
        return new ResponseEntity<Competition>(competition.get(), null, HttpStatus.OK);
    }
}