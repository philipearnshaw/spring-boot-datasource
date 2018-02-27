package springbootdatasource.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "MATCH", schema = "HANDBALL")
public class Match implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOME_TEAM_ID", nullable = false)
    private Team homeTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AWAY_TEAM_ID", nullable = false)
    private Team awayTeam;

    @Column(name = "MATCH_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar matchDate;

    @Column(name = "CR_DATE", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createDate;

    @Column(name = "LAST_UPDATED", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar lastUpdated;

    @PrePersist
    public void prePersist() {
        createDate = Calendar.getInstance();
        lastUpdated = Calendar.getInstance();
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdated = Calendar.getInstance();
    }

    public Match(final Team homeTeam, final Team awayTeam, final Calendar matchDate) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.matchDate = matchDate;
    }
}