package com.jgen.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.DecimalFormat;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SuiviIndicateurs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String atteinte;
    private String commentaire;
    @Transient
    private Boolean auth = false;
    @ManyToOne
    private PlanificationIndicateurs planification;

    public String getEcart(){
        if(this.atteinte == null)
            return "0.0";
        else{
            try{
                DecimalFormat df = new DecimalFormat("#.##");
                float atteinte = Float.parseFloat(this.atteinte), cible = Float.parseFloat(this.planification.getCible());
                return df.format((atteinte / cible) * 100);
            }catch (Exception e){
                if(this.atteinte.equals(this.planification.getCible()))
                    return "100.0";
                else
                    return "0.0";
            }
        }
    }

    public String getColor(){
        if(this.atteinte == null)
            return "bg-danger text-white";
        else{
            try{
                float atteinte = Float.parseFloat(this.atteinte), cible = Float.parseFloat(this.planification.getCible());
                double value = (atteinte / cible) * 100;
                if(value >= 100)
                    return "bg-success text-white";
                else if(value<100 && value>=50)
                    return "bg-warning text-white";
                else
                    return "bg-danger text-white";
            }catch (Exception e){
                if(this.atteinte.equals(this.planification.getCible()))
                    return "bg-success text-white";
                else
                    return "bg-danger text-white";
            }
        }
    }

    public com.jgen.dto.SuiviIndicateurs transformer(){
        com.jgen.dto.SuiviIndicateurs suivi = new com.jgen.dto.SuiviIndicateurs();
        suivi.setIndicateur(this.planification.getIndicateur());
        suivi.setDescription(this.planification.getDescription());
        suivi.setReference(this.planification.getReference());
        suivi.setCible(this.planification.getCible());
        suivi.setAtteinte(this.atteinte);
        suivi.setTaux(this.getEcart());
        suivi.setSourceVerification(this.planification.getSourceVerification());
        suivi.setCommentaire(this.commentaire);

        return suivi;
    }
}

