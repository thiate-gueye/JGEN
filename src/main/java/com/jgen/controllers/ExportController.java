package com.jgen.controllers;

import com.jgen.models.*;
import com.jgen.repositories.*;
import com.jgen.services.ExcelUtil;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/export/excel")
public class ExportController {

    private final PlanificationIndicateursRepo indicateursRepo;
    private final PlanificationActivitesRepo activitesRepo;
    private final PlanificationFormationsRepo formationsRepo;
    private final SuiviIndicateursRepo suiviIndicateursRepo;
    private final SuiviActivitesRepo suiviActivitesRepo;
    private final SuiviFormationsRepo suiviFormationsRepo;

    public ExportController(PlanificationIndicateursRepo indicateursRepo, PlanificationActivitesRepo activitesRepo, PlanificationFormationsRepo formationsRepo, SuiviIndicateursRepo suiviIndicateursRepo, SuiviActivitesRepo suiviActivitesRepo, SuiviFormationsRepo suiviFormationsRepo) {
        this.indicateursRepo = indicateursRepo;
        this.activitesRepo = activitesRepo;
        this.formationsRepo = formationsRepo;
        this.suiviIndicateursRepo = suiviIndicateursRepo;
        this.suiviActivitesRepo = suiviActivitesRepo;
        this.suiviFormationsRepo = suiviFormationsRepo;
    }

    //Planification et suivi des indicateurs

    //Planification
    @GetMapping("/indicateurs/planification")
    public ResponseEntity<InputStreamResource> planif_indicateur(HttpSession session) {
        List<PlanificationIndicateurs> indicateurs = indicateursRepo.findAllByProjet((String)session.getAttribute("projet"));
        List<com.jgen.dto.PlanificationIndicateurs> planif_indicateurs = new ArrayList<>();
        for(PlanificationIndicateurs indicateur : indicateurs){
            planif_indicateurs.add(indicateur.transformer());
        }

        try {
            byte[] excelData = ExcelUtil.createExcelFile(planif_indicateurs, com.jgen.dto.PlanificationIndicateurs.class);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(excelData);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=planification_indicateur.xlsx");

            return new ResponseEntity<>(new InputStreamResource(inputStream), headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Suivi
    @GetMapping("/indicateurs/suivi")
    public ResponseEntity<InputStreamResource> suivi_indicateur(HttpSession session) {
        List<SuiviIndicateurs> s_indicateurs = suiviIndicateursRepo.findAllSuiviByProjet((String)session.getAttribute(("projet")));
        List<com.jgen.dto.SuiviIndicateurs> suivi_indicateurs = new ArrayList<>();
        for (SuiviIndicateurs suivi : s_indicateurs){
            suivi_indicateurs.add(suivi.transformer());
        }
        try {
            byte[] excelData = ExcelUtil.createExcelFile(suivi_indicateurs, com.jgen.dto.SuiviIndicateurs.class);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(excelData);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=suivi_indicateur.xlsx");

            return new ResponseEntity<>(new InputStreamResource(inputStream), headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //Planification et suivi des activites

    //Planification
    @GetMapping("/activites/planification")
    public ResponseEntity<InputStreamResource> planif_activite(HttpSession session) {
        List<PlanificationActivites> activites = activitesRepo.findAllByProjet((String)session.getAttribute("projet"));
        List<com.jgen.dto.PlanificationActivites> planif_activites = new ArrayList<>();
        for(PlanificationActivites activite : activites){
            planif_activites.add(activite.transformer());
        }
        try {
            byte[] excelData = ExcelUtil.createExcelFile(planif_activites, com.jgen.dto.PlanificationActivites.class);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(excelData);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=planification_activite.xlsx");

            return new ResponseEntity<>(new InputStreamResource(inputStream), headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Suivi
    @GetMapping("/activites/suivi")
    public ResponseEntity<InputStreamResource> suivi_activite(HttpSession session) {
        List<SuiviActivites> s_activite = suiviActivitesRepo.findAllSuiviByProjet((String)session.getAttribute(("projet")));
        List<com.jgen.dto.SuiviActivites> suivi_activites = new ArrayList<>();
        for (SuiviActivites suivi : s_activite){
            suivi_activites.add(suivi.transformer());
        }
        try {
            byte[] excelData = ExcelUtil.createExcelFile(suivi_activites, com.jgen.dto.SuiviActivites.class);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(excelData);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=suivi_activite.xlsx");

            return new ResponseEntity<>(new InputStreamResource(inputStream), headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //Planification et suivi des formations

    //Planification
    @GetMapping("/formations/planification")
    public ResponseEntity<InputStreamResource> planif_formation(HttpSession session) {
        List<PlanificationFormations> formations = formationsRepo.findAllByProjet((String)session.getAttribute("projet"));
        List<com.jgen.dto.PlanificationFormations> planif_formations = new ArrayList<>();
        for(PlanificationFormations formation : formations){
            planif_formations.add(formation.transformer());
        }
        try {
            byte[] excelData = ExcelUtil.createExcelFile(planif_formations, com.jgen.dto.PlanificationFormations.class);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(excelData);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=planification_formation.xlsx");

            return new ResponseEntity<>(new InputStreamResource(inputStream), headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Suivi
    @GetMapping("/formations/suivi")
    public ResponseEntity<InputStreamResource> suivi_formation(HttpSession session) {
        List<SuiviFormations> s_formations = suiviFormationsRepo.findAllSuiviByProjet((String)session.getAttribute(("projet")));
        List<com.jgen.dto.SuiviFormations> suivi_formations = new ArrayList<>();
        for (SuiviFormations suivi : s_formations){
            suivi_formations.add(suivi.transformer());
        }
        try {
            byte[] excelData = ExcelUtil.createExcelFile(suivi_formations, com.jgen.dto.SuiviFormations.class);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(excelData);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=suivi_formation.xlsx");

            return new ResponseEntity<>(new InputStreamResource(inputStream), headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
