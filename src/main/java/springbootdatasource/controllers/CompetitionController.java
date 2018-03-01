package springbootdatasource.controllers;

import static springbootdatasource.validators.EntityValidator.requireNonNull;

import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.RequiredArgsConstructor;
import springbootdatasource.controllers.swagger.CompetitionControllerSwaggerDoc;
import springbootdatasource.exception.BadRequestException;
import springbootdatasource.model.Competition;
import springbootdatasource.model.profiles.CompetitionProfile;
import springbootdatasource.services.CompetitionService;

@RequiredArgsConstructor
@RestController
@RequestMapping(CompetitionController.COMPETITION_ROOT_URI)
public class CompetitionController implements CompetitionControllerSwaggerDoc {
    
    public static final String COMPETITION_ROOT_URI = "/handball/competitions";

    private final CompetitionService competitionService;

    @JsonView(CompetitionProfile.DetailView.class)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<Competition> getAllCompetitions() {
        return competitionService.findAllCompetitions();
    }

    @JsonView(CompetitionProfile.DetailView.class)
    @GetMapping("/{competitionId}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public Competition getCompetitionById(@PathVariable("competitionId") final String competitionId) {
        return requireNonNull(competitionService.findByCompetitionId(Long.valueOf(competitionId)), competitionId).get();
    }
        
    @JsonView(CompetitionProfile.DetailView.class)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Competition postCompetition(@Valid @RequestBody final Competition competition) {
        requireNullCompetitionId(competition);
        return competitionService.saveCompetition(competition);
    }
    
    @JsonView(CompetitionProfile.DetailView.class)
    @PutMapping("/{competitionId}")
    @ResponseStatus(HttpStatus.OK)
    public Competition putCompetitionById(@PathVariable("competitionId") final String competitionId, @Valid @RequestBody Competition competition) {
        requireBodyIdToBeNullOrSameAsResourceId(competitionId, competition);
        competition.setCompetitionId(Long.valueOf(competitionId));
        return competitionService.saveCompetition(competition);
    }
    
    @JsonView(CompetitionProfile.DetailView.class)
    @DeleteMapping("/{competitionId}")
    @ResponseStatus(HttpStatus.OK)
    public Competition deleteCompetitionById(@PathVariable String competitionId){
        final Optional<Competition> competition = requireNonNull(competitionService.findByCompetitionId(Long.valueOf(competitionId)), competitionId);
        competitionService.deleteById(Long.valueOf(competitionId));
        return competition.get();
    }
    
    /**
     * Helper methods
     */
    private void requireNullCompetitionId(final Competition competition) {
        if (competition.getCompetitionId() != null) {
            throw new BadRequestException("Competition id should not be set for a POST request");
        }
    }
    
    private void requireBodyIdToBeNullOrSameAsResourceId(final String competitionId, Competition competition) {
        if (competition.getCompetitionId() != null && !competition.getCompetitionId().equals(Long.valueOf(competitionId))) {
            throw new BadRequestException("Competition id is set in request body and does not match the resource id");
        }
    }
}