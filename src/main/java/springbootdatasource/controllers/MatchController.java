package springbootdatasource.controllers;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import springbootdatasource.dto.MatchDto;
import springbootdatasource.mapper.MatchMapper;
import springbootdatasource.services.MatchService;

@RequiredArgsConstructor
@RestController
@RequestMapping(MatchController.MATCH_ROOT_URI)
public class MatchController {
    
    public static final String MATCH_ROOT_URI = "/handball/matches";
    
    private final MatchService matchService;
    private final MatchMapper matchMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<MatchDto> getAllMatches() {
        return matchMapper.matchesToMatchDtos(matchService.findAllMatches());
    }
}
