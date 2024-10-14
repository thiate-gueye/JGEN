package com.jgen.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IndicateurDashbord {
    private String indicateur;
    private String reference;
    private String cible;
    private String atteinte;

    public String getTaux(){
        if(this.atteinte == null)
            return "0.0";
        else{
            try{
                DecimalFormat df = new DecimalFormat("#.##");
                float atteinte = Float.parseFloat(this.atteinte), cible = Float.parseFloat(this.getCible());
                return df.format((atteinte / cible) * 100);
            }catch (Exception e){
                if(this.atteinte.equals(this.getCible()))
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
                float atteinte = Float.parseFloat(this.atteinte), cible = Float.parseFloat(this.cible);
                double value = (atteinte / cible) * 100;
                if(value >= 100)
                    return "bg-success text-white";
                else if(value<100 && value>=50)
                    return "bg-warning text-white";
                else
                    return "bg-danger text-white";
            }catch (Exception e){
                if(this.atteinte.equals(this.cible))
                    return "bg-success text-white";
                else
                    return "bg-danger text-white";
            }
        }
    }
}
