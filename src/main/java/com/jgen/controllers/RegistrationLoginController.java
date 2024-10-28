package com.jgen.controllers;

import com.jgen.models.Projects;
import com.jgen.models.Users;
import com.jgen.repositories.ProjetcsRepo;
import com.jgen.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class RegistrationLoginController {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ProjetcsRepo projetcsRepo;

    @GetMapping("/")
    public ModelAndView accueil_con(){
        ModelAndView model = new ModelAndView("connexion");
        Users user = new Users();
        model.addObject("user", user);
        return model;
    }

    @PostMapping("/login")
    public void login(@ModelAttribute Users users, HttpSession session){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword()));
            session.setAttribute("email", users.getUsername());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @GetMapping("/verifactif")
    public String verifActifUser(HttpSession session){
        if(usersRepository.isActif((String)session.getAttribute("email")))
           return "redirect:/home";
        else
            return "redirect:/?actif=false";
    }

    @GetMapping("/changestatus")
    public ResponseEntity<Void> updateStatus(@RequestParam boolean actif, @RequestParam String username) {
        usersRepository.changeStatus(actif, username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/home")
    public ModelAndView accueil(HttpSession session){
        ModelAndView model = new ModelAndView("index");

        session.setAttribute("access", usersRepository.getRoleUser((String)session.getAttribute("email")));
        session.setAttribute("owner", usersRepository.getIdUser((String)session.getAttribute("email")));
        Projects projects = new Projects();
        model.addObject("new_project", projects);
        model.addObject("listProjects", projetcsRepo.findAll());
        model.addObject("con", session.getAttribute("con"));
        return model;
    }

    @GetMapping("/administer")
    public ModelAndView admin_user(HttpSession session){
        ModelAndView model = new ModelAndView("users");
        Users users = new Users();
        List<Users> usersList = usersRepository.getAllUsers((String)session.getAttribute("email"));
        model.addObject("usersList", usersList);
        model.addObject("users", users);
        return model;
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute Users users){
        users.setPassword(passwordEncoder.encode("Passer123"));
        users.setRole("USER");
        usersRepository.save(users);
        return "redirect:/administer";
    }

    @GetMapping("/user/modification")
    public ModelAndView modif(HttpSession session){
        ModelAndView model = new ModelAndView("registration");
        Users users = usersRepository.findByUsername((String)session.getAttribute("email"));
        model.addObject("users", users);
        return model;
    }

    @PostMapping("/user/registration")
    public String modif_user(@ModelAttribute Users users, HttpSession session, @RequestParam String confirm){
        if(!users.getPassword().equals(confirm)){
            return "redirect:/user/modification/?error=true";
        }
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.setRole((String)session.getAttribute("access"));
        users.setActif(true);
        usersRepository.save(users);
        return "redirect:/";
    }

    @GetMapping("/users/suppression")
    public String delete(@RequestParam(value = "id") int id) {
        usersRepository.deleteById(id);
        return "redirect:/administer";
    }

    @PostMapping("/users/changerole")
    public String update(@RequestParam String username, @RequestParam String role ){
        usersRepository.changeRole(role, username);
        return "redirect:/administer";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("access");
        session.removeAttribute("email");
        session.removeAttribute("projet");
        session.invalidate();
        return "redirect:/";
    }
}
