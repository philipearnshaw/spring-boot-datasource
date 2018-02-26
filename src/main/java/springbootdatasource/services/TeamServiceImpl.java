package springbootdatasource.services;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import springbootdatasource.model.Team;
import springbootdatasource.repositories.TeamRepository;

@RequiredArgsConstructor
@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    
    @Override
    public Set<Team> findAllTeams() {
        return StreamSupport.stream(teamRepository.findAll().spliterator(), false)
        .collect(Collectors.toSet());
    }

}
