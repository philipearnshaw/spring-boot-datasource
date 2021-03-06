package springbootdatasource.mapper;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import springbootdatasource.dto.TeamDto;
import springbootdatasource.model.Team;

@Mapper
public interface TeamMapper {

    TeamMapper INSTANCE = Mappers.getMapper(TeamMapper.class);
    
    TeamDto teamToTeamDto(Team team);
    Team teamDtoToTeam(TeamDto teamDto);
    
    Set<TeamDto> teamsToTeamDtos(Set<Team> teams);
    Set<Team> iterableTeamsToSetTeams(Iterable<Team> teams);
}
