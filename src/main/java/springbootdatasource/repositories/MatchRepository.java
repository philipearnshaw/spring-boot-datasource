package springbootdatasource.repositories;

import org.springframework.data.repository.CrudRepository;

import springbootdatasource.model.Match;

public interface MatchRepository extends CrudRepository<Match, Long> {
}
