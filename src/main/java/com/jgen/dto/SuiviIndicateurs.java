package com.jgen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SuiviIndicateurs {
    private String indicateur;
    private String description;
    private String reference;
    private String cible;
    private String atteinte;
    private String taux;
    private String sourceVerification;
    private String commentaire;
}
