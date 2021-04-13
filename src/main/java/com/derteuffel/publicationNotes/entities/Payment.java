package com.derteuffel.publicationNotes.entities;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "payment")
public class Payment implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String Description;
    private String numPaiement;
    private Double montantDollar;
    private String periode;
    private Double montantFranc;
    private int tauxDuJour;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm")
    private Date addedDate = new Date();
    private String code;

    @ManyToOne
    private UserInfo userInfo;

    public Payment() {
    }

    public Payment(String description, Double montantDollar, Double montantFranc, int tauxDuJour, Date addedDate) {
        Description = description;
        this.montantDollar = montantDollar;
        this.montantFranc = montantFranc;
        this.tauxDuJour = tauxDuJour;
        this.addedDate = addedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Double getMontantDollar() {
        return montantDollar;
    }

    public void setMontantDollar(Double montantDollar) {
        this.montantDollar = montantDollar;
    }

    public Double getMontantFranc() {
        return montantFranc;
    }

    public void setMontantFranc(Double montantFranc) {
        this.montantFranc = montantFranc;
    }

    public int getTauxDuJour() {
        return tauxDuJour;
    }

    public void setTauxDuJour(int tauxDuJour) {
        this.tauxDuJour = tauxDuJour;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public String getNumPaiement() {
        return numPaiement;
    }

    public void setNumPaiement(String numPaiement) {
        this.numPaiement = numPaiement;
    }
}
