package springbootdatasource.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(schema = "HANDBALL", name = "COMPETITION")
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long competitionId;

    private String name;

    public Competition() {
    }

    public Competition(String name) {
        this.name = name;
    }
}
