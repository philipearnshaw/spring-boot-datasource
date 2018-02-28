package springbootdatasource.services;

import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import springbootdatasource.mapper.MatchMapper;
import springbootdatasource.model.Match;
import springbootdatasource.repositories.MatchRepository;

@RequiredArgsConstructor
@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    
    @Override
    public Set<Match> findAllMatches() {
        return MatchMapper.INSTANCE.iterableMatchesToSetMatches(matchRepository.findAll());
    }
}
