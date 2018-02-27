package springbootdatasource.controllers;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import springbootdatasource.dto.MatchDto;
import springbootdatasource.mapper.MatchMapper;
import springbootdatasource.services.MatchService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/handball/matches")
public class MatchController {
    
    private final MatchService matchService;
    private final MatchMapper matchMapper;

    @GetMapping
    ResponseEntity<Set<MatchDto>> getAllTeams() {
        return new ResponseEntity<Set<MatchDto>>(
                matchMapper.matchesToMatchDtos(matchService.findAllMatches()), HttpStatus.OK
                );
    }
}
