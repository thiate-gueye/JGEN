package com.jgen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SuiviFormations {
    private String objectifSpecifique;
    private String thematique;
    private int nombreJour;
    private int homme;
    private int femme;
    private int homme_vivant_avec_handicape;
    private int femme_vivant_avec_handicape;
    private int moins_15ans_vivant_avec_handicape;
    private int de_16_25ans_vivant_avec_handicape;
    private int de_26_40ans_vivant_avec_handicape;
    private int plus_de_40ans_vivant_avec_handicape;
    private int moins_15ans;
    private int de_16_25ans;
    private int de_26_40ans;
    private int plus_de_40ans;
    private double budget;
    private double decaissement;
    private float tauxDecaissement;
    private String outilFormation;
    private String commentaire;
}
