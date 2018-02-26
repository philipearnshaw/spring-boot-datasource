package springbootdatasource.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import springbootdatasource.dto.TeamDto;
import springbootdatasource.model.Team;

@Mapper
public interface TeamMapper {

    TeamMapper INSTANCE = Mappers.getMapper(TeamMapper.class);
    
    TeamDto teamToTeamDto(Team team);
    Team teamDtoToTeam(TeamDto teamDto);
}
