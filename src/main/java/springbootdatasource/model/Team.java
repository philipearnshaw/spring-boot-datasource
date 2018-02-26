package springbootdatasource.model;

import java.io.Serializable;
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

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "TEAM", schema = "HANDBALL")
public class Team implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="TEAM_ID", nullable = false)
	private Long teamId;

	@Column(name="CR_DATE", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar createDate;

	@Column(name="IS_INTERNATIONAL", nullable = false)
	private String isInternational = "N";

	@Column(name="LAST_UPDATED", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar lastUpdated;

	@Column(name="NAME", nullable = false)
	private String name;

	@Column(name="NICKNAME")
	private String nickname;

	@Column(name="SHORT_NAME")
	private String shortName;
	
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