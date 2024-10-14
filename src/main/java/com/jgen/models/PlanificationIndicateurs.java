package com.jgen.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class PlanificationIndicateurs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String indicateur;
    private String description;
    private String reference;
    private String cible;
    private String sourceVerification;
    private String projet;
    private int owner;
    @Transient
    private Boolean auth = false;

    public com.jgen.dto.PlanificationIndicateurs transformer(){
        com.jgen.dto.PlanificationIndicateurs indicateurs = new com.jgen.dto.PlanificationIndicateurs();
        indicateurs.setIndicateur(this.getIndicateur());
        indicateurs.setDescription(this.getDescription());
        indicateurs.setReference(this.getReference());
        indicateurs.setCible(this.getCible());
        indicateurs.setSourceVerification(this.getSourceVerification());
        indicateurs.setProjet(this.getProjet());

        return indicateurs;
    }
}
