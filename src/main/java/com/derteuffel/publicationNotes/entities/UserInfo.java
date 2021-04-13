package com.derteuffel.publicationNotes.entities;

import com.derteuffel.publicationNotes.enums.Niveau;
import com.derteuffel.publicationNotes.enums.Sexe;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user_info")
@Data
public class UserInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private Niveau niveau;
    private String email;
    private String telephone;
    private String birthDate;
    private String adressePhysique;
    private String institution;
    private String numIdentite;
    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private Sexe sexe;
    private String matricule;
    private Boolean account;
    private Boolean minervalles;
    private Boolean enrollementMiSession;
    private Boolean enrollementPremiereSession;
    private Boolean enrollementDeuxiemeSession;

    @ManyToOne
    private Options options;

    public UserInfo() {
    }

    public UserInfo(String name, String firstName, String lastName, Niveau niveau, String email, String telephone,
                    String adressePhysique, String institution, String birthDate, String numIdentite, Sexe sexe, String matricule) {
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.niveau = niveau;
        this.email = email;
        this.telephone = telephone;
        this.adressePhysique = adressePhysique;
        this.institution = institution;
        this.birthDate = birthDate;
        this.numIdentite = numIdentite;
        this.sexe = sexe;
        this.matricule = matricule;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdressePhysique() {
        return adressePhysique;
    }

    public void setAdressePhysique(String adressePhysique) {
        this.adressePhysique = adressePhysique;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getNumIdentite() {
        return numIdentite;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setNumIdentite(String numIdentite) {
        this.numIdentite = numIdentite;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Boolean getAccount() {
        return account;
    }

    public void setAccount(Boolean account) {
        this.account = account;
    }

    public Boolean getMinervalles() {
        return minervalles;
    }

    public void setMinervalles(Boolean minervalles) {
        this.minervalles = minervalles;
    }

    public Boolean getEnrollementMiSession() {
        return enrollementMiSession;
    }

    public void setEnrollementMiSession(Boolean enrollementMiSession) {
        this.enrollementMiSession = enrollementMiSession;
    }

    public Boolean getEnrollementPremiereSession() {
        return enrollementPremiereSession;
    }

    public void setEnrollementPremiereSession(Boolean enrollementPremiereSession) {
        this.enrollementPremiereSession = enrollementPremiereSession;
    }

    public Boolean getEnrollementDeuxiemeSession() {
        return enrollementDeuxiemeSession;
    }

    public void setEnrollementDeuxiemeSession(Boolean enrollementDeuxiemeSession) {
        this.enrollementDeuxiemeSession = enrollementDeuxiemeSession;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }
}
