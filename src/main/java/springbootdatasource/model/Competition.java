package springbootdatasource.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import lombok.NoArgsConstructor;
import springbootdatasource.model.profiles.CompetitionProfile;

@NoArgsConstructor
@Data
@JsonInclude(Include.NON_NULL)
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
    private String shortName;
    
    @NotEmpty
    @JsonView(CompetitionProfile.DetailView.class)
    private String shortCode; 
    
    @Column(name = "CR_DATE", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createDate;
    
    @Column(name = "LAST_UPDATED", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar lastUpdated;

    public Competition(String name, String shortName, String shortCode) {
        this.name = name;
        this.shortName = shortName;
        this.shortCode = shortCode;
    }
    
    @PrePersist
    public void prePersist() {
        createDate = Calendar.getInstance();
        lastUpdated = Calendar.getInstance();
    }
    
    @PreUpdate
    public void preUpdate() {
        lastUpdated = Calendar.getInstance();
    }
}
