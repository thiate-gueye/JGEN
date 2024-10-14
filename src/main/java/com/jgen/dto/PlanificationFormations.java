package com.jgen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanificationFormations {
    private String projet;
    private String objectifSpecifique;
    private String thematique;
    private int participant;
    private int nbJour;
    private double budget;
    private String sourceFinancement;
    private String lieu;
}
