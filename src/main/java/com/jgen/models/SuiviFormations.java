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
public class SuiviFormations {
    @Id
    private  int id;
    @Transient
    private int codePlanif;
    private int homme, femme;
    private int t1, t2, t3, t4;
    private int hdp_homme, hdp_femme, hdp_t1, hdp_t2, hdp_t3, hdp_t4;
    private double decaissement;
    private float ecart;
    private String outil, commentaire;
    @Transient
    private Boolean auth = false;
    @ManyToOne
    private PlanificationFormations planification;

    public int totalBeneficiaire(){
        return this.homme + this.femme;
    }

    public String formatNombre(double nombre){
        NumberFormat format = NumberFormat.getNumberInstance(Locale.FRENCH);
        return format.format(nombre);
    }

    public com.jgen.dto.SuiviFormations transformer(){
        com.jgen.dto.SuiviFormations formations = new com.jgen.dto.SuiviFormations();

        formations.setObjectifSpecifique(this.planification.getObjectifSpecifique().getLibelle());
        formations.setThematique(this.planification.getThematique());
        formations.setNombreJour(this.planification.getNbJour());
        formations.setHomme(this.getHomme());
        formations.setFemme(this.getFemme());
        formations.setMoins_15ans(this.getT1());
        formations.setDe_16_25ans(this.getT2());
        formations.setDe_26_40ans(this.getT3());
        formations.setPlus_de_40ans(this.getT4());
        formations.setHomme_vivant_avec_handicape(this.getHdp_homme());
        formations.setFemme_vivant_avec_handicape(this.getHdp_femme());
        formations.setMoins_15ans_vivant_avec_handicape(this.getHdp_t1());
        formations.setDe_16_25ans_vivant_avec_handicape(this.getHdp_t2());
        formations.setDe_26_40ans_vivant_avec_handicape(this.getHdp_t3());
        formations.setPlus_de_40ans_vivant_avec_handicape(this.getHdp_t4());
        formations.setBudget(this.planification.getBudget());
        formations.setDecaissement(this.getDecaissement());
        formations.setTauxDecaissement(this.getEcart());
        formations.setOutilFormation(this.getOutil());
        formations.setCommentaire(this.getCommentaire());

        return formations;
    }

    public String getColorDecaissement() {

        if (this.decaissement > this.planification.getBudget())
            return "bg-warning text-white";
        else if (this.decaissement == this.planification.getBudget())
            return "bg-success text-white";
        else
            return "bg-danger text-white";
    }
}
