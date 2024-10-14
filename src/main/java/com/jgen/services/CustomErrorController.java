package com.jgen.services;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        // Redirige vers la page d'accès refusé pour les erreurs 403 et 405
        return "redirect:/access-denied";
    }

    @GetMapping("/access-denied")
    public ModelAndView denied(){
        return new ModelAndView("access_denied");
    }

}