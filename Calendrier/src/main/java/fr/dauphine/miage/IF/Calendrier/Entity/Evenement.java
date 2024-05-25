package fr.dauphine.miage.IF.Calendrier.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalTime;


@Entity
public class Evenement {

    @Id
    @GeneratedValue
    @JsonProperty("id")
    @Column(name="id")
    private int id;

    @JsonProperty("site")
    @Column(name="site")
    private String site;

    @JsonProperty("sport")
    @Column(name="sport")
    private String sport;

    @JsonProperty("date")
    @Column(name="date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-dd-MM")
    private LocalDate date;

    @JsonProperty("heure")
    @Column(name="heure")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime time;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
