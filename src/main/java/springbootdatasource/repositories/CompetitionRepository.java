package springbootdatasource.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import springbootdatasource.model.Competition;

public interface CompetitionRepository extends CrudRepository<Competition, Long> {
    Optional<Competition> findByName(String name);
}
