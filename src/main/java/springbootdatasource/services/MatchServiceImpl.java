package springbootdatasource.services;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import springbootdatasource.model.Match;
import springbootdatasource.repositories.MatchRepository;

@RequiredArgsConstructor
@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    
    @Override
    public Set<Match> findAllMatches() {
        return StreamSupport.stream(matchRepository.findAll().spliterator(), false)
        .collect(Collectors.toSet());
    }
}
