package com.jgen.controllers;

import com.jgen.models.*;
import com.jgen.repositories.ObjectifsSpecifiquesRepo;
import com.jgen.repositories.PlanificationFormationsRepo;
import com.jgen.repositories.ProjetcsRepo;
import com.jgen.repositories.SuiviFormationsRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/formations")
public class FormationsController {

    private final PlanificationFormationsRepo formationsRepo;
    private final SuiviFormationsRepo suiviFormationsRepo;
    private final ObjectifsSpecifiquesRepo ob_sp_repo;
    private final ProjetcsRepo projetcsRepo;

    private final ObjectifsSpecifiquesRepo objectifsRepo;

    public FormationsController(PlanificationFormationsRepo formationsRepo, SuiviFormationsRepo suiviFormationsRepo, ObjectifsSpecifiquesRepo ob_sp_repo, ProjetcsRepo projetcsRepo, ObjectifsSpecifiquesRepo objectifsRepo) {
        this.formationsRepo = formationsRepo;
        this.suiviFormationsRepo = suiviFormationsRepo;
        this.ob_sp_repo = ob_sp_repo;
        this.projetcsRepo = projetcsRepo;
        this.objectifsRepo = objectifsRepo;
    }

    // PLANIFICATION

    @GetMapping("/planification")
    public ModelAndView accueil(HttpSession session){
        ModelAndView model = new ModelAndView("formations/planification");
        List<PlanificationFormations> listFormations = formationsRepo.findAllByProjet((String)session.getAttribute("projet"));
        for (PlanificationFormations planif:listFormations) {
            if(planif.getOwner() == Integer.parseInt((String)session.getAttribute("owner")))
                planif.setAuth(true);
        }
        PlanificationFormations planificationFormations = new PlanificationFormations();
        List<ObjectifsSpecifiques>  listObjectifs = objectifsRepo.findAllByProjet((String)session.getAttribute("projet"));

        model.addObject("listObjectifs", listObjectifs);
        model.addObject("listFormations", listFormations);
        model.addObject("new_formation", planificationFormations);
        model.addObject("con", session.getAttribute("con"));
        return model;
    }

    @PostMapping("planification/ajout")
    public String save(HttpSession session, @ModelAttribute PlanificationFormations planificationFormations) {
        planificationFormations.setProjet((String)session.getAttribute("projet"));
        planificationFormations.setObjectifSpecifique(objectifsRepo.findById(planificationFormations.getIdOS()));
        planificationFormations.setOwner(Integer.parseInt((String)session.getAttribute("owner")));
        formationsRepo.save(planificationFormations);

        SuiviFormations suiviFormations = new SuiviFormations();
        suiviFormations.setId(planificationFormations.getId());
        suiviFormations.setPlanification(planificationFormations);
        suiviFormationsRepo.save(suiviFormations);

        return "redirect:/formations/planification";
    }

    @PostMapping("planification/modification")
    public String update(HttpSession session, @RequestParam int id, @RequestParam String thematique, @RequestParam int participant,
                         @RequestParam double budget, @RequestParam int nbJour, @RequestParam String sourceFinancement,
                         @RequestParam String lieu, @RequestParam int idOS) {
        PlanificationFormations planificationFormations = new PlanificationFormations();
        planificationFormations.setId(id);
        planificationFormations.setProjet((String)session.getAttribute("projet"));
        planificationFormations.setObjectifSpecifique(objectifsRepo.findById(idOS));
        planificationFormations.setThematique(thematique);
        planificationFormations.setSourceFinancement(sourceFinancement);
        planificationFormations.setParticipant(participant);
        planificationFormations.setBudget(budget);
        planificationFormations.setNbJour(nbJour);
        planificationFormations.setLieu(lieu);
        planificationFormations.setOwner(Integer.parseInt((String)session.getAttribute("owner")));

        formationsRepo.save(planificationFormations);
        return "redirect:/formations/planification";
    }

    @GetMapping("planification/suppression")
    public String delete(@RequestParam(value = "id") int id) {
        suiviFormationsRepo.delete(suiviFormationsRepo.findSuiviByidPlanif(id));
        formationsRepo.deleteById(id);
        return "redirect:/formations/planification";
    }

    // SUIVI

    @GetMapping("/suivi")
    public ModelAndView s_accueil(HttpSession session){
        ModelAndView model = new ModelAndView("formations/suivi");
        List<SuiviFormations> listSuivi = suiviFormationsRepo.findAllSuiviByProjet((String)session.getAttribute("projet"));
        model.addObject("listSuivi", listSuivi);
        for (SuiviFormations suivi:listSuivi) {
            if(suivi.getPlanification().getOwner() == Integer.parseInt((String)session.getAttribute("owner")))
                suivi.setAuth(true);
        }
        model.addObject("con", session.getAttribute("con"));
        return model;
    }

    @PostMapping("suivi/modification")
    public String update_suivi(@RequestParam int id, @RequestParam int codePlanif, @RequestParam int homme, @RequestParam int femme,
                               @RequestParam int t1, @RequestParam int t2, @RequestParam int t3,@RequestParam int t4,
                               @RequestParam int hdp_homme, @RequestParam int hdp_femme,
                               @RequestParam int hdp_t1, @RequestParam int hdp_t2, @RequestParam int hdp_t3,@RequestParam int hdp_t4,
                               @RequestParam double decaissement, @RequestParam String outil, @RequestParam String commentaire) {
        SuiviFormations suiviFormations = new SuiviFormations();
        suiviFormations.setId(id);
        suiviFormations.setDecaissement(decaissement);
        suiviFormations.setT1(t1);
        suiviFormations.setT2(t2);
        suiviFormations.setT3(t3);
        suiviFormations.setT4(t4);
        suiviFormations.setEcart((float) ((decaissement) / formationsRepo.getById(codePlanif).getBudget())*100);
        suiviFormations.setHdp_homme(hdp_homme);
        suiviFormations.setHdp_femme(hdp_femme);
        suiviFormations.setHomme(homme);
        suiviFormations.setFemme(femme);
        suiviFormations.setHdp_t1(hdp_t1);
        suiviFormations.setHdp_t2(hdp_t2);
        suiviFormations.setHdp_t3(hdp_t3);
        suiviFormations.setHdp_t4(hdp_t4);
        suiviFormations.setCommentaire(commentaire);
        suiviFormations.setOutil(outil);
        suiviFormations.setPlanification(formationsRepo.getById(codePlanif));

        suiviFormationsRepo.save(suiviFormations);
        return "redirect:/formations/suivi";
    }
}
