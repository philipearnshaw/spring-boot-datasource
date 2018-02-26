package springbootdatasource.controllers;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import springbootdatasource.dto.TeamDto;
import springbootdatasource.mapper.TeamMapper;
import springbootdatasource.services.TeamService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/handball/teams")
public class TeamController {
    
    private final TeamService teamService;
    private final TeamMapper teamMapper;

    @GetMapping
    ResponseEntity<Set<TeamDto>> getAllTeams() {
        final Set<TeamDto> teamDtos = teamService.findAllTeams().stream()
                .map(teamMapper::teamToTeamDto)
                .collect(Collectors.toSet());
        
        return new ResponseEntity<Set<TeamDto>>(teamDtos, HttpStatus.OK);
    }
}
