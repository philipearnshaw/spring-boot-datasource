package springbootdatasource.mapper;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import springbootdatasource.dto.MatchDto;
import springbootdatasource.model.Match;

@Mapper
public interface MatchMapper {

    MatchMapper INSTANCE = Mappers.getMapper(MatchMapper.class);
    
    MatchDto matchToMatchDto(Match match);
    Match matchDtoToMatch(MatchDto matchDto);
    
    Set<MatchDto> matchesToMatchDtos(Set<Match> matches);
}
