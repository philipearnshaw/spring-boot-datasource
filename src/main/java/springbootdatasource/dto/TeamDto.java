package springbootdatasource.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TeamDto {
    private Long teamId;
    private String isInternational;
    private String name;
    private String nickname;
    private String shortName;
}
