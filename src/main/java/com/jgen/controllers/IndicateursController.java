package com.jgen.controllers;

import com.jgen.models.PlanificationIndicateurs;
import com.jgen.models.SuiviIndicateurs;
import com.jgen.repositories.ObjectifsSpecifiquesRepo;
import com.jgen.repositories.PlanificationIndicateursRepo;
import com.jgen.repositories.ProjetcsRepo;
import com.jgen.repositories.SuiviIndicateursRepo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/indicateurs")
public class IndicateursController {


    private final PlanificationIndicateursRepo indicateursRepo;
    private final SuiviIndicateursRepo suiviIndicateursRepo;
    private final ObjectifsSpecifiquesRepo ob_sp_repo;
    private final ProjetcsRepo projetcsRepo;

    public IndicateursController(PlanificationIndicateursRepo indicateursRepo, SuiviIndicateursRepo suiviIndicateursRepo, ObjectifsSpecifiquesRepo ob_sp_repo, ProjetcsRepo projetcsRepo) {
        this.indicateursRepo = indicateursRepo;
        this.suiviIndicateursRepo = suiviIndicateursRepo;
        this.ob_sp_repo = ob_sp_repo;
        this.projetcsRepo = projetcsRepo;
    }

    @GetMapping("/planification")
    public ModelAndView accueil(HttpSession session){
        ModelAndView model = new ModelAndView("indicateurs/planification");
        List<PlanificationIndicateurs> listIndicateurs = indicateursRepo.findAllByProjet((String)session.getAttribute("projet"));
        for (PlanificationIndicateurs planif:listIndicateurs) {
            if(planif.getOwner() == Integer.parseInt((String)session.getAttribute("owner")))
                planif.setAuth(true);
        }
        PlanificationIndicateurs planificationIndicateurs = new PlanificationIndicateurs();
        model.addObject("listIndicateurs", listIndicateurs);
        model.addObject("new_indicateur", planificationIndicateurs);
        model.addObject("con", session.getAttribute("con"));
        return model;
    }

    @PostMapping("planification/ajout")
    public String save(HttpSession session, @ModelAttribute PlanificationIndicateurs planificationIndicateurs) {
        planificationIndicateurs.setProjet((String)session.getAttribute("projet"));
        planificationIndicateurs.setOwner(Integer.parseInt((String)session.getAttribute("owner")));
        indicateursRepo.save(planificationIndicateurs);

        SuiviIndicateurs suiviIndicateur = new SuiviIndicateurs();
        suiviIndicateur.setPlanification(planificationIndicateurs);
        suiviIndicateursRepo.save(suiviIndicateur);

        return "redirect:/indicateurs/planification";
    }

    @PostMapping("/planification/modification")
    public String update_planif(HttpSession session, @RequestParam int code, @RequestParam String indicateur,
                               @RequestParam String description, @RequestParam String reference,
                               @RequestParam String cible, @RequestParam String source){
        PlanificationIndicateurs planificationIndicateurs = new PlanificationIndicateurs();
        planificationIndicateurs.setId(code);
        planificationIndicateurs.setIndicateur(indicateur);
        planificationIndicateurs.setDescription(description);
        planificationIndicateurs.setCible(cible);
        planificationIndicateurs.setReference(reference);
        planificationIndicateurs.setSourceVerification(source);
        planificationIndicateurs.setProjet((String)session.getAttribute("projet"));
        planificationIndicateurs.setOwner(Integer.parseInt((String)session.getAttribute("owner")));

        indicateursRepo.save(planificationIndicateurs);
        return "redirect:/indicateurs/planification";
    }

    @GetMapping("planification/suppression")
    public String delete(@RequestParam(value = "id") int id) {
        suiviIndicateursRepo.delete(suiviIndicateursRepo.findSuiviByidPlanif(id));
        indicateursRepo.deleteById(id);
        return "redirect:/indicateurs/planification";
    }


    @GetMapping("/suivi")
    public ModelAndView s_accueil(HttpSession session){
        ModelAndView model = new ModelAndView("indicateurs/suivi");
        List<SuiviIndicateurs> listSuivi = suiviIndicateursRepo.findAllSuiviByProjet((String)session.getAttribute(("projet")));
        for (SuiviIndicateurs suivi:listSuivi) {
            if(suivi.getPlanification().getOwner() == Integer.parseInt((String)session.getAttribute("owner")))
                suivi.setAuth(true);
        }
        model.addObject("listSuivi", listSuivi);
        model.addObject("con", session.getAttribute("con"));
        return model;
    }

    @PostMapping("/suivi/modification")
    public String update_suivi(@RequestParam int code, @RequestParam int planif,
                               @RequestParam String atteinte, @RequestParam String commentaire ){
        SuiviIndicateurs suiviIndicateurs = new SuiviIndicateurs();
        suiviIndicateurs.setId(code);
        suiviIndicateurs.setPlanification(indicateursRepo.findById(planif));
        suiviIndicateurs.setAtteinte(atteinte);
        suiviIndicateurs.setCommentaire(commentaire);
        suiviIndicateursRepo.save(suiviIndicateurs);
        return "redirect:/indicateurs/suivi";
    }
}
