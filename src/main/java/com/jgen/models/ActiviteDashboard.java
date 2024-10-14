package com.jgen.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ActiviteDashboard {

    private String activite;
    private String cible;
    private double atteinte;
    private double budget;
    private double decaissement;

    public String getDecPurcent(){
        try {
            DecimalFormat df = new DecimalFormat("#.##");
            return df.format((this.decaissement / this.budget) * 100).replace(',', '.');
        }catch (Exception e){
            return "0.0";
        }
    }

    public String formatNumber(double num){
        NumberFormat format = NumberFormat.getNumberInstance(Locale.FRENCH);
        return format.format(num);
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

        if (this.decaissement > this.budget)
            return "bg-success text-white";
        else if (this.decaissement == this.budget)
            return "bg-warning text-white";
        else
            return "bg-danger text-white";
    }
}
