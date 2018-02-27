package springbootdatasource.services;

import java.util.Set;

import springbootdatasource.model.Match;

public interface MatchService {
    Set<Match> findAllMatches();
}
