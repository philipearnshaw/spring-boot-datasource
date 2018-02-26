package springbootdatasource.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import springbootdatasource.dto.TeamDto;
import springbootdatasource.model.Team;

public class TeamMapperTest {

    private static final long TEAM_ID = 5L;
    private static final String IS_INTERNATIONAL = "Y";
    private static final String SHORT_NAME = "short-name";
    private static final String NAME = "name";
    private static final String NICKNAME = "nickname";

    @Test
    public void testTeamToTeamDto() {
        // given
        final Team team = new Team();
        team.setIsInternational(IS_INTERNATIONAL);
        team.setName(NAME);
        team.setNickname(NICKNAME);
        team.setShortName(SHORT_NAME);
        team.setTeamId(TEAM_ID);
        
        // when
        final TeamDto teamDto = TeamMapper.INSTANCE.teamToTeamDto(team);
        
        // then
        assertThat(teamDto).isNotNull();
        assertThat(teamDto.getIsInternational()).isEqualTo(IS_INTERNATIONAL);
        assertThat(teamDto.getName()).isEqualTo(NAME);
        assertThat(teamDto.getNickname()).isEqualTo(NICKNAME);
        assertThat(teamDto.getShortName()).isEqualTo(SHORT_NAME);
        assertThat(teamDto.getTeamId()).isEqualTo(TEAM_ID);
    }

    @Test
    public void testTeamDtoToTeam() {
        // given
        final TeamDto teamDto = new TeamDto();
        teamDto.setIsInternational(IS_INTERNATIONAL);
        teamDto.setName(NAME);
        teamDto.setNickname(NICKNAME);
        teamDto.setShortName(SHORT_NAME);
        teamDto.setTeamId(TEAM_ID);
        
        // when
        final Team team = TeamMapper.INSTANCE.teamDtoToTeam(teamDto);
        
        // then
        assertThat(team).isNotNull();
        assertThat(team.getIsInternational()).isEqualTo(IS_INTERNATIONAL);
        assertThat(team.getName()).isEqualTo(NAME);
        assertThat(team.getNickname()).isEqualTo(NICKNAME);
        assertThat(team.getShortName()).isEqualTo(SHORT_NAME);
        assertThat(team.getTeamId()).isEqualTo(TEAM_ID);
        assertThat(team.getCreateDate()).isNull();
        assertThat(team.getLastUpdated()).isNull();
    }
}
