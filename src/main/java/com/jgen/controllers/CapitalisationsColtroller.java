package com.jgen.controllers;

import com.jgen.models.Capitalisations;
import com.jgen.models.InfoCapitalisations;
import com.jgen.models.ObjectifsSpecifiques;
import com.jgen.repositories.CapitalisationsRepo;
import com.jgen.repositories.InfoCapitalisationsRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/capitalisations")
public class CapitalisationsColtroller {
    private final CapitalisationsRepo capitalisationsRepo;
    private final InfoCapitalisationsRepo infoCapitalisationsRepo;

    public CapitalisationsColtroller(CapitalisationsRepo capitalisationsRepo, InfoCapitalisationsRepo infoCapitalisationsRepo) {
        this.capitalisationsRepo = capitalisationsRepo;
        this.infoCapitalisationsRepo = infoCapitalisationsRepo;
    }

    @GetMapping("/")
    public ModelAndView accueil(HttpSession session){
        ModelAndView model = new ModelAndView("capitalisations/capitalisation");
        Capitalisations capitalisation = new Capitalisations();
        List<Capitalisations> listCapitalisations = capitalisationsRepo.findAllByProjet((String)session.getAttribute("projet"));
        model.addObject("new_capitalisation", capitalisation);
        model.addObject("listCapitalisations", listCapitalisations);
        model.addObject("con", session.getAttribute("con"));
        return model;
    }

    @PostMapping("/ajout")
    public String save( @RequestParam("contraintes[]") List<String> contraintes,  @RequestParam("reussites[]") List<String> reussites,
                        @RequestParam("lecons[]") List<String> lecons,  @RequestParam("actions[]") List<String> actions,
                       @RequestParam String thematique, HttpSession session){

        Capitalisations capitalisations = new Capitalisations();
        List<InfoCapitalisations> infos = new ArrayList<>();

        capitalisations.setThematique(thematique);
        capitalisations.setProjet((String)session.getAttribute("projet"));

        InfoCapitalisations infoCapitalisations;
        for (String contrainte : contraintes) {
            infoCapitalisations = new InfoCapitalisations();
            infoCapitalisations.setLibelle(contrainte);
            infoCapitalisations.setType("Contrainte");
            infoCapitalisations.setCapitalisations(capitalisations);
            infos.add(infoCapitalisations);
        }
        for (String reussite : reussites) {
            infoCapitalisations = new InfoCapitalisations();
            infoCapitalisations.setLibelle(reussite);
            infoCapitalisations.setType("Reussite");
            infoCapitalisations.setCapitalisations(capitalisations);
            infos.add(infoCapitalisations);
        }
        for (String lecon : lecons) {
            infoCapitalisations = new InfoCapitalisations();
            infoCapitalisations.setLibelle(lecon);
            infoCapitalisations.setType("Lecon");
            infoCapitalisations.setCapitalisations(capitalisations);
            infos.add(infoCapitalisations);
        }
        for (String action : actions) {
            infoCapitalisations = new InfoCapitalisations();
            infoCapitalisations.setLibelle(action);
            infoCapitalisations.setType("Action");
            infoCapitalisations.setCapitalisations(capitalisations);
            infos.add(infoCapitalisations);
        }

        capitalisations.setInfoCapitalisations(infos);
        capitalisationsRepo.save(capitalisations);

        return "redirect:/capitalisations/";
    }

    @PostMapping("/modification")
    public String update( @RequestParam("contraintes[]") List<String> contraintes,  @RequestParam("reussites[]") List<String> reussites,
                        @RequestParam("lecons[]") List<String> lecons,  @RequestParam("actions[]") List<String> actions,
                        @RequestParam int id){

        Capitalisations cap = capitalisationsRepo.findById(id);

        List<InfoCapitalisations> l_contraintes = cap.getInfoByType("Contrainte");
        infoCapitalisationsRepo.saveAll(getList(contraintes, l_contraintes, "Contrainte", cap));

        List<InfoCapitalisations> l_reussites = cap.getInfoByType("Reussite");
        infoCapitalisationsRepo.saveAll(getList(reussites, l_reussites, "Reussite", cap));

        List<InfoCapitalisations> l_lecons = cap.getInfoByType("Lecon");
        infoCapitalisationsRepo.saveAll(getList(lecons, l_lecons, "Lecon", cap));

        List<InfoCapitalisations> l_actions = cap.getInfoByType("Action");
        infoCapitalisationsRepo.saveAll(getList(actions, l_actions, "Action", cap));


    return "redirect:/capitalisations/";
    }

    @GetMapping("/suppression")
    public String delete(@RequestParam(value = "id") int id) {
        Capitalisations cap = capitalisationsRepo.findById(id);

        infoCapitalisationsRepo.deleteAll(cap.getInfoCapitalisations());
        capitalisationsRepo.delete(cap);

        return "redirect:/capitalisations/";
    }

    private List<InfoCapitalisations> getList(List<String> l1, List<InfoCapitalisations> l2, String type, Capitalisations cap){

    List<InfoCapitalisations> listes = new ArrayList<>();
        int i=0, nl = l1.size(), no = l2.size();
        if(nl == no){
            for(i=0; i<nl; i++){
                l2.get(i).setLibelle(l1.get(i));
                l2.get(i).setType(type);
                l2.get(i).setCapitalisations(cap);
                listes.add(l2.get(i));
            }
        }
        if(no < nl){
            InfoCapitalisations info;
            while (i < no){
                l2.get(i).setLibelle(l1.get(i));
                l2.get(i).setType(type);
                l2.get(i).setCapitalisations(cap);
                listes.add(l2.get(i));
                i++;
            }
            for (i=no; i<nl; i++){
                info = new InfoCapitalisations();
                info.setLibelle(l1.get(i));
                info.setType(type);
                info.setCapitalisations(cap);
                listes.add(info);
            }
        }
        return listes;
    }
}
