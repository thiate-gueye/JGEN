package com.jgen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlanificationIndicateurs {
    private String projet;
    private String indicateur;
    private String description;
    private String cible;
    private String reference;
    private String sourceVerification;
}
