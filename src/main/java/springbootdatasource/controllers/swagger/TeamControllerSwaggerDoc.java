package springbootdatasource.controllers.swagger;

import java.util.Set;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springbootdatasource.dto.TeamDto;

@Api(description = "Controller for accessing team information")
public interface TeamControllerSwaggerDoc {

    @ApiOperation(value = "Finds all teams available",
            notes = "Finds all teams available",
            response = TeamDto.class,
            responseContainer = "Set")
    public Set<TeamDto> getAllTeams();
}
