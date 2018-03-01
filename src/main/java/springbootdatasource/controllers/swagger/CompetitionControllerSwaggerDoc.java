package springbootdatasource.controllers.swagger;

import org.springframework.web.bind.annotation.PathVariable;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springbootdatasource.model.Competition;

@Api(description = "Controller for accessing competition information")
public interface CompetitionControllerSwaggerDoc {

    @ApiOperation(value = "Find a competition by ID",
            notes = "Find a competition by the passed ID",
            response = Competition.class)
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "Invalid competition ID passed such as none numeric."),
            @ApiResponse(code = 404, message = "Competition cannot be found using the passed ID.")
    })
    public Competition getCompetitionById(@ApiParam(value = "Competition ID as number", required = true) @PathVariable("competitionId") final String competitionId);
}
