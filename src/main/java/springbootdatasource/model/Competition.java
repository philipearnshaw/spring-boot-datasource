package springbootdatasource.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import lombok.NoArgsConstructor;
import springbootdatasource.model.profiles.CompetitionProfile;

@NoArgsConstructor
@Data
@Entity
@Table(schema = "HANDBALL", name = "COMPETITION")
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(CompetitionProfile.SummaryView.class)
    private Long competitionId;

    @NotEmpty
    @JsonView(CompetitionProfile.SummaryView.class)
    private String name;
    
    @NotEmpty
    @JsonView(CompetitionProfile.DetailView.class)
    private String owner;
    
    @PositiveOrZero
    private Integer budget;  // Only shown when no view selected on controller endpoint.

    public Competition(String name, String owner, Integer budget) {
        this.name = name;
        this.owner = owner;
        this.budget = budget;
    }
}
