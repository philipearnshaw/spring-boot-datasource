package springbootdatasource.services;

import java.util.Set;

import springbootdatasource.model.Team;

public interface TeamService {
    
    Set<Team> findAllTeams();

}
