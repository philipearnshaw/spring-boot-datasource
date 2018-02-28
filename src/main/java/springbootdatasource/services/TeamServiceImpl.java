package springbootdatasource.services;

import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import springbootdatasource.mapper.TeamMapper;
import springbootdatasource.model.Team;
import springbootdatasource.repositories.TeamRepository;

@RequiredArgsConstructor
@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    
    @Override
    public Set<Team> findAllTeams() {
        return TeamMapper.INSTANCE.iterableTeamsToSetTeams(teamRepository.findAll());
    }
}
