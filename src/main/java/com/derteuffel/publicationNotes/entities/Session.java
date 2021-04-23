package com.derteuffel.publicationNotes.entities;

import com.derteuffel.publicationNotes.enums.Niveau;
import com.derteuffel.publicationNotes.enums.Periode;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "session")
public class Session implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private Periode name;
    private String sessionDate;
    private Double percentage;
    private int uvLostNumber;
    private String decision;
    private int total;
    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private Niveau niveau;
    private Boolean active;
    @ManyToOne
    private UserInfo userInfo;

    public Session() {
    }

    public Session(Periode name, String sessionDate) {
        this.name = name;
        this.sessionDate = sessionDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Periode getName() {
        return name;
    }

    public void setName(Periode name) {
        this.name = name;
    }

    public String getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(String sessionDate) {
        this.sessionDate = sessionDate;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public int getUvLostNumber() {
        return uvLostNumber;
    }

    public void setUvLostNumber(int uvLostNumber) {
        this.uvLostNumber = uvLostNumber;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
