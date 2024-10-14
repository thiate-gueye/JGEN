package com.jgen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlanificationActivites {
    private String projet;
    private String objectifSpecifique;
    private String activite;
    private String cible;
    private String resultatAttendu;
    private double budget;
    private String responsable;
    private String lieu;
    private String dateDebut;
    private String dateFin;
}
