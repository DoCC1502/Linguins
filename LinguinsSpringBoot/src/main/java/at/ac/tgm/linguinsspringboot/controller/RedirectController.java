package at.ac.tgm.linguinsspringboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    @GetMapping(value = "/{path:[^\\.]*}")
    public String redirect() {
        // Leitet alle Pfade ohne Punkt (also keine Dateien) an die index.html weiter
        return "forward:/index.html";
    }
}