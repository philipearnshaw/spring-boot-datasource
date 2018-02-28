package springbootdatasource.mapper;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import springbootdatasource.model.Competition;


@Mapper
public interface CompetitionMapper {

    CompetitionMapper INSTANCE = Mappers.getMapper(CompetitionMapper.class);

    Set<Competition> iterableCompetitionsToSetCompetitions(Iterable<Competition> competitions);
}
