package com.jgen.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.NumberFormat;
import java.util.Locale;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class PlanificationFormations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Transient
    private int idOS;
    private String thematique;
    private int participant;
    private int nbJour;
    private double budget;
    private String sourceFinancement;
    private String lieu;
    private String projet;
    private int owner;
    @Transient
    private Boolean auth = false;

    @ManyToOne
    private ObjectifsSpecifiques objectifSpecifique;

    public String formatNombre(double nombre){
        NumberFormat format = NumberFormat.getNumberInstance(Locale.FRENCH);
        return format.format(nombre);
    }

    public com.jgen.dto.PlanificationFormations transformer(){
        com.jgen.dto.PlanificationFormations formation = new com.jgen.dto.PlanificationFormations();
        formation.setProjet(this.getProjet());
        formation.setObjectifSpecifique(this.getObjectifSpecifique().getLibelle());
        formation.setThematique(this.getThematique());
        formation.setParticipant(this.getParticipant());
        formation.setNbJour(this.getNbJour());
        formation.setBudget(this.getBudget());
        formation.setSourceFinancement(this.getSourceFinancement());
        formation.setLieu(this.getLieu());

        return formation;
    }
}
