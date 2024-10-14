package com.jgen.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"thematique", "projet"})})
public class Capitalisations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String thematique;
    private String projet;

    @OneToMany(mappedBy = "capitalisations", cascade = CascadeType.ALL, orphanRemoval = true)
    List<InfoCapitalisations> infoCapitalisations;

    public List<InfoCapitalisations> getInfoByType(String type){
        List<InfoCapitalisations> infosOfType = new ArrayList<>();

        for(InfoCapitalisations info : infoCapitalisations) {
            if(info.getType().equals(type)) {
                infosOfType.add(info);
            }
        }

        return infosOfType;
    }
}
