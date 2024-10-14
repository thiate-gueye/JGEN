package com.jgen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SuiviActivites {
    private String objectifSpecifique;
    private String activite;
    private String cible;
    private double tauxRealisation;
    private String resultatAttendu;
    private String resultatObtenu;
    private double budget;
    private double decaissement;
    private String tauxDecaissement;
    private String responsable;
    private String lieu;
    private String dateDebutPrevue;
    private String dateDebutReelle;
    private String dateFinPrevue;
    private String dateFinReelle;
    private String statut;
    private String commentaire;
}
