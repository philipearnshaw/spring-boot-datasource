package springbootdatasource.repositories;

import org.springframework.data.repository.CrudRepository;

import springbootdatasource.model.Competition;

public interface CompetitionRepository extends CrudRepository<Competition, Long> {

}
