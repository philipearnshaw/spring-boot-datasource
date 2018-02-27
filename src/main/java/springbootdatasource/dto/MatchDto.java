package springbootdatasource.dto;

import java.util.Calendar;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MatchDto {
    private Long matchId;
    private TeamDto homeTeam;
    private TeamDto awayTeam;
    private Calendar matchDate;
}