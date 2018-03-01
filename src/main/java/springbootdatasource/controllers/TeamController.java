package springbootdatasource.controllers;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import springbootdatasource.controllers.swagger.TeamControllerSwaggerDoc;
import springbootdatasource.dto.TeamDto;
import springbootdatasource.mapper.TeamMapper;
import springbootdatasource.services.TeamService;

@RequiredArgsConstructor
@RestController
@RequestMapping(TeamController.TEAM_ROOT_URI)
public class TeamController implements TeamControllerSwaggerDoc {
    
    public static final String TEAM_ROOT_URI = "/handball/teams";
    
    private final TeamService teamService;
    private final TeamMapper teamMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    public Set<TeamDto> getAllTeams() {
        return teamMapper.teamsToTeamDtos(teamService.findAllTeams());
    }
}
