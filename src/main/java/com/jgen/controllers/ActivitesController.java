package com.jgen.controllers;

import com.jgen.models.*;
import com.jgen.repositories.ObjectifsSpecifiquesRepo;
import com.jgen.repositories.PlanificationActivitesRepo;
import com.jgen.repositories.ProjetcsRepo;
import com.jgen.repositories.SuiviActivitesRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/activites")
public class ActivitesController {

    private final PlanificationActivitesRepo activitesRepo;
    private final SuiviActivitesRepo suiviActivitesRepo;
    private final ProjetcsRepo projetcsRepo;
    private final ObjectifsSpecifiquesRepo ob_sp_repo;

    private final ObjectifsSpecifiquesRepo objectifsRepo;

    public ActivitesController(PlanificationActivitesRepo activitesRepo, SuiviActivitesRepo suiviActivitesRepo, ProjetcsRepo projetcsRepo, ObjectifsSpecifiquesRepo ob_sp_repo, ObjectifsSpecifiquesRepo objectifsRepo) {
        this.activitesRepo = activitesRepo;
        this.suiviActivitesRepo = suiviActivitesRepo;
        this.projetcsRepo = projetcsRepo;
        this.ob_sp_repo = ob_sp_repo;
        this.objectifsRepo = objectifsRepo;
    }

    @GetMapping("/planification")
    public ModelAndView accueil(HttpSession session){
        ModelAndView model = new ModelAndView("activites/planification");
        PlanificationActivites new_activite = new PlanificationActivites();
        List<PlanificationActivites> listActivites = activitesRepo.findAllByProjet((String)session.getAttribute("projet"));
        for (PlanificationActivites planif:listActivites) {
            if(planif.getOwner() == Integer.parseInt((String)session.getAttribute("owner")))
                planif.setAuth(true);
        }
        List<ObjectifsSpecifiques>  listObjectifs = objectifsRepo.findAllByProjet((String)session.getAttribute("projet"));

        model.addObject("listActivites", listActivites);
        model.addObject("listObjectifs", listObjectifs);
        model.addObject("new_activite", new_activite);
        model.addObject("con", session.getAttribute("con"));
        return model;
    }

    @PostMapping("planification/ajout")
    public String save(HttpSession session, @ModelAttribute PlanificationActivites planificationActivites) {
        planificationActivites.setProjet((String)session.getAttribute("projet"));
        planificationActivites.setObjectifSpecifique(objectifsRepo.findById(planificationActivites.getIdOS()));
        planificationActivites.setOwner(Integer.parseInt((String)session.getAttribute("owner")));
        activitesRepo.save(planificationActivites);

        SuiviActivites suiviActivites = new SuiviActivites();
        suiviActivites.setStatut("Non démarrée");
        suiviActivites.setAtteinte(0.0);
        suiviActivites.setPlanification(planificationActivites);
        suiviActivitesRepo.save(suiviActivites);

        return "redirect:/activites/planification";
    }

    @PostMapping("/planification/modification")
    public String update_planif(HttpSession session, @RequestParam int code, @RequestParam int idOS, @RequestParam String activite,
                                @RequestParam String responsable, @RequestParam String resultatAttendu,
                                @RequestParam String lieu, @RequestParam String dateDebut, @RequestParam double budget,
                                @RequestParam String dateFin, @RequestParam String cible){
        PlanificationActivites planificationActivites = new PlanificationActivites();
        planificationActivites.setId(code);
        planificationActivites.setResponsable(responsable);
        planificationActivites.setResultatAttendu(resultatAttendu);
        planificationActivites.setActivite(activite);
        planificationActivites.setLieu(lieu);
        planificationActivites.setDateDebut(dateDebut);
        planificationActivites.setDateFin(dateFin);
        planificationActivites.setBudget(budget);
        planificationActivites.setObjectifSpecifique(objectifsRepo.findById(idOS));
        planificationActivites.setProjet((String)session.getAttribute("projet"));
        planificationActivites.setOwner(Integer.parseInt((String)session.getAttribute("owner")));
        planificationActivites.setCible(cible);

        activitesRepo.save(planificationActivites);
        return "redirect:/activites/planification";
    }

    @GetMapping("planification/suppression")
    public String delete(@RequestParam(value = "id") int id) {
        suiviActivitesRepo.delete(suiviActivitesRepo.findSuiviByidPlanif(id));
        activitesRepo.deleteById(id);
        return "redirect:/activites/planification";
    }

    @GetMapping("/suivi")
    public ModelAndView s_accueil(HttpSession session){
        ModelAndView model = new ModelAndView("activites/suivi");
        List<SuiviActivites> listSuivi = suiviActivitesRepo.findAllSuiviByProjet((String)session.getAttribute(("projet")));
        model.addObject("listSuivi", listSuivi);
        for (SuiviActivites suivi:listSuivi) {
            if(suivi.getPlanification().getOwner() == Integer.parseInt((String)session.getAttribute("owner")))
                suivi.setAuth(true);
        }
        model.addObject("objectifs", ob_sp_repo.findAllByProjet((String)session.getAttribute("projet")));
        model.addObject("con", session.getAttribute("con"));
        return model;
    }

    @PostMapping("/suivi/modification")
    public String update_suivi(HttpSession session, @RequestParam int code, @RequestParam int planif, @RequestParam String resultatObtenu,
                                @RequestParam String dateDebut, @RequestParam String dateFin, @RequestParam double atteinte,
                                @RequestParam double decaissement, @RequestParam String statut, @RequestParam String commentaire){
        SuiviActivites suiviActivites = new SuiviActivites();
        suiviActivites.setId(code);
        suiviActivites.setPlanification(activitesRepo.findById(planif));
        suiviActivites.setResultatObtenu(resultatObtenu);
        suiviActivites.setDateDebut(dateDebut);
        suiviActivites.setDateFin(dateFin);
        suiviActivites.setDecaissement(decaissement);
        suiviActivites.setStatut(statut);
        suiviActivites.setAtteinte(atteinte);
        suiviActivites.setCommentaire(commentaire);

        suiviActivitesRepo.save(suiviActivites);
        return "redirect:/activites/suivi";
    }
}
