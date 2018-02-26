package springbootdatasource.repositories;

import org.springframework.data.repository.CrudRepository;

import springbootdatasource.model.Team;

public interface TeamRepository extends CrudRepository<Team, Long> {
}
