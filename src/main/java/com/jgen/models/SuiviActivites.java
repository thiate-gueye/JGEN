package com.jgen.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class SuiviActivites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String resultatObtenu;
    private String dateDebut, dateFin;
    private double atteinte;
    private double decaissement;
    private String statut, commentaire;
    @Transient
    private Boolean auth = false;
    @ManyToOne
    private PlanificationActivites planification;

    public String getDecPurcent(){
        try {
            DecimalFormat df = new DecimalFormat("#.##");
            return df.format((this.decaissement / this.planification.getBudget()) * 100).replace(',', '.');
        }catch (Exception e){
            return "0.0";
        }
    }

    public String formatDecaissement(){
        NumberFormat format = NumberFormat.getNumberInstance(Locale.FRENCH);
        return format.format(this.decaissement);
    }

    public com.jgen.dto.SuiviActivites transformer(){
        com.jgen.dto.SuiviActivites activites = new com.jgen.dto.SuiviActivites();
        activites.setObjectifSpecifique(this.planification.getObjectifSpecifique().getLibelle());
        activites.setActivite(this.planification.getActivite());
        activites.setCible(this.planification.getCible());
        activites.setTauxRealisation(this.getAtteinte());
        activites.setResultatAttendu(this.planification.getResultatAttendu());
        activites.setResultatObtenu(this.getResultatObtenu());
        activites.setBudget(this.planification.getBudget());
        activites.setDecaissement(this.getDecaissement());
        activites.setTauxDecaissement(this.getDecPurcent());
        activites.setResponsable(this.planification.getResponsable());
        activites.setLieu(this.planification.getLieu());
        activites.setDateDebutPrevue(this.planification.getDateDebut());
        activites.setDateDebutReelle(this.getDateDebut());
        activites.setDateFinPrevue(this.planification.getDateFin());
        activites.setDateFinReelle(this.getDateFin());
        activites.setStatut(this.getStatut());
        activites.setCommentaire(this.getCommentaire());

        return activites;
    }

    public String getColorRealisation() {

        if (this.atteinte >= 100)
            return "bg-success text-white";
        else if (this.atteinte < 100 && this.atteinte >= 50)
            return "bg-warning text-white";
        else
            return "bg-danger text-white";
    }

    public String getColorDecaissement() {

        if (this.decaissement > this.planification.getBudget())
            return "bg-warning text-white";
        else if (this.decaissement == this.planification.getBudget())
            return "bg-success  text-white";
        else
            return "bg-danger text-white";
    }
}
