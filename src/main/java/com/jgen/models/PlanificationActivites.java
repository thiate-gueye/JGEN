package com.jgen.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.NumberFormat;
import java.util.Locale;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class PlanificationActivites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Transient
    private int idOS;
    private String activite;
    private String responsable;
    private String resultatAttendu;
    private String lieu;
    private double budget;
    private String projet;
    private String cible;
    private String dateDebut, dateFin;
    private int owner;
    @Transient
    private Boolean auth = false;

    @ManyToOne
    private ObjectifsSpecifiques objectifSpecifique;

    public String formatBudget(){
        NumberFormat format = NumberFormat.getNumberInstance(Locale.FRENCH);
        return format.format(this.budget);
    }

    public com.jgen.dto.PlanificationActivites transformer(){
        com.jgen.dto.PlanificationActivites activites = new com.jgen.dto.PlanificationActivites();
        activites.setActivite(this.activite);
        activites.setResponsable(this.responsable);
        activites.setResultatAttendu(this.resultatAttendu);
        activites.setLieu(this.lieu);
        activites.setBudget(this.budget);
        activites.setProjet(this.projet);
        activites.setCible(this.cible);
        activites.setDateDebut(this.dateDebut);
        activites.setDateFin(this.dateFin);
        activites.setObjectifSpecifique(this.getObjectifSpecifique().getLibelle());

        return activites;
    }
}


