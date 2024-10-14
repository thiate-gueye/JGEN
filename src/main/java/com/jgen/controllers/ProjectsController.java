package com.jgen.controllers;

import com.jgen.models.*;
import com.jgen.repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectsController {
    private final ProjetcsRepo projetcsRepo;
    private final ObjectifsSpecifiquesRepo ob_sp_repo;
    private final SuiviIndicateursRepo s_ind_repo;
    private final SuiviActivitesRepo s_act_repo;
    private final SuiviFormationsRepo s_form_repo;
    private final PlanificationIndicateursRepo p_ind_repo;
    private final PlanificationActivitesRepo p_act_repo;
    private final PlanificationFormationsRepo p_form_repo;
    private final CapitalisationsRepo cap_repo;
    private final InfoCapitalisationsRepo inf_cap_repo;



    public ProjectsController(ProjetcsRepo projetcsRepo, ObjectifsSpecifiquesRepo ob_sp_repo, SuiviIndicateursRepo s_ind_repo,
                              SuiviActivitesRepo s_act_repo, SuiviFormationsRepo s_form_repo,
                              PlanificationIndicateursRepo p_ind_repo, PlanificationActivitesRepo p_act_repo,
                              PlanificationFormationsRepo p_form_repo, CapitalisationsRepo cap_repo, InfoCapitalisationsRepo inf_cap_repo) {
        this.projetcsRepo = projetcsRepo;
        this.ob_sp_repo = ob_sp_repo;
        this.s_ind_repo = s_ind_repo;
        this.s_act_repo = s_act_repo;
        this.s_form_repo = s_form_repo;
        this.p_ind_repo = p_ind_repo;
        this.p_act_repo = p_act_repo;
        this.p_form_repo = p_form_repo;
        this.cap_repo = cap_repo;
        this.inf_cap_repo = inf_cap_repo;
    }

    @GetMapping("/")
    public ModelAndView projects(HttpSession session, @RequestParam("req") String req){
        ModelAndView model = new ModelAndView("projects/projects");
        List<Object[]> indicateurs = s_ind_repo.findIndicateurs(req);
        List<IndicateurDashbord> indicateurDashbords = new ArrayList<>();
        List<FormationDashboard> formationDashboards = new ArrayList<>();
        List<ActiviteDashboard> activiteDashboards = new ArrayList<>();

        float sum = 0;
        IndicateurDashbord ind;
        for(Object[] obj : indicateurs){
            ind = new IndicateurDashbord();
            ind.setIndicateur((String) obj[0]);
            ind.setReference((String) obj[1]);
            ind.setCible((String) obj[2]);
            ind.setAtteinte((String) obj[3]);
            sum += Float.parseFloat(ind.getTaux());
            indicateurDashbords.add(ind);
        }

        List<Object[]> activites = s_act_repo.findActivites(req);
        float sum_act = 0, sum_budg = 0;
        ActiviteDashboard act;
        for(Object[] obj : activites){
            act = new ActiviteDashboard();
            act.setActivite((String) obj[0]);
            act.setCible((String) obj[1]);
            act.setAtteinte((Double) obj[2]);
            act.setBudget((Double) obj[3]);
            act.setDecaissement((Double) obj[4]);

            sum_act += act.getAtteinte();
            sum_budg += Float.parseFloat(act.getDecPurcent());
            activiteDashboards.add(act);
        }

        List<Object[]> formations = s_form_repo.findFormations(req);
        FormationDashboard form;
        for(Object[] obj : formations){
            form = new FormationDashboard();
            form.setThematique((String) obj[0]);
            form.setBudget((double) obj[1]);
            form.setDecaissement((double) obj[2]);

            formationDashboards.add(form);

            sum_budg += (form.getDecaissement() / form.getBudget()) * 100;
        }

        model.addObject("projet", projetcsRepo.findByNom(req));
        model.addObject("formations", formationDashboards);
        model.addObject("page","projects");
        model.addObject("objectifs", ob_sp_repo.findAllByProjet(req));
        if(indicateurs.isEmpty())
            model.addObject("taux_efficacite",0.0);
        else
            model.addObject("taux_efficacite",new DecimalFormat("#.00").format(sum/indicateurs.size()).replace(',','.'));
        if(activites.isEmpty()){
            model.addObject("taux_exe_phy",0.0);
            model.addObject("taux_exe_budg",0.0);
        }else{
            model.addObject("taux_exe_phy",new DecimalFormat("#.00").format(sum_act/activites.size()).replace(',','.'));
            model.addObject("taux_exe_budg",new DecimalFormat("#.00").format(sum_budg/(activites.size() + formations.size())).replace(',','.'));
        }
        model.addObject("activites",activiteDashboards);
        model.addObject("indicateurs",indicateurDashbords);
        session.setAttribute("projet", req);
        return model;
    }

    @PostMapping("/ajout")
    public String ajout(HttpSession session, @ModelAttribute Projects projects, @RequestParam("objectifs[]") List<String> objectifs){
        projetcsRepo.save(projects);
        ObjectifsSpecifiques obj;
        for (String objectif : objectifs) {
            obj = new ObjectifsSpecifiques();
            obj.setLibelle(objectif);
            obj.setProjet(projects.getNom());
            ob_sp_repo.save(obj);
        }
        session.setAttribute("projet", projects.getNom());
        return "redirect:/projects/?req="+projects.getNom();
    }

    @PostMapping("/modification")
    public String modification(HttpSession session, @RequestParam int id, @RequestParam String nom, @RequestParam String bailleur,
                               @RequestParam String objectifGeneral, @RequestParam String cible, @RequestParam String description,
                               @RequestParam("objectifs[]") List<String> objectifs){

        Projects projects = new Projects();
        projects.setNom(nom);
        projects.setId(id);
        projects.setBailleur(bailleur);
        projects.setCible(cible);
        projects.setObjectifGeneral(objectifGeneral);
        projects.setDescription(description);

        projetcsRepo.save(projects);

        List<ObjectifsSpecifiques> listes = ob_sp_repo.findAllByProjet(nom);
        int i=0, nl = listes.size(), no = objectifs.size();
        if(nl == no){
            for(i=0; i<nl; i++){
                listes.get(i).setLibelle(objectifs.get(i));
            }
        }
        if(no > nl){
            ObjectifsSpecifiques obj;
            while (i < nl){
                listes.get(i).setLibelle(objectifs.get(i));
                i++;
            }
            for (i=nl; i<no; i++){
                obj = new ObjectifsSpecifiques();
                obj.setLibelle(objectifs.get(i));
                obj.setProjet(nom);
                listes.add(obj);
            }
        }
        ob_sp_repo.saveAll(listes);
        session.setAttribute("projet", nom);

        return "redirect:/projects/?req="+nom;
    }

    @GetMapping("/suppression")
    public String delete(HttpSession session, @RequestParam(value = "id") int id) {

        String nom = (String)session.getAttribute("projet");

        List<Capitalisations>  capitalisations = cap_repo.findAllByProjet(nom);

        for(Capitalisations cap : capitalisations){
            inf_cap_repo.deleteAll(cap.getInfoCapitalisations());
            cap_repo.delete(cap);
        }
        s_act_repo.deleteSuiviByProjet(nom);
        s_ind_repo.deleteSuiviByProjet(nom);
        s_form_repo.deleteSuiviByProjet(nom);

        p_act_repo.deleteByProjet(nom);
        p_ind_repo.deleteByProjet(nom);
        p_form_repo.deleteByProjet(nom);

        ob_sp_repo.deleteByProjet(nom);
        projetcsRepo.deleteById(id);
        return "redirect:/";
    }

}
