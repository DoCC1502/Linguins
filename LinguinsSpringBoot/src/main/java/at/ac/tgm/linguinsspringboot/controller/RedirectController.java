package at.ac.tgm.linguinsspringboot.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RedirectController {

    /**
     * Dieses Mapping fängt nur Anfragen ab, die NICHT mit /api oder /ws beginnen.
     * Regex Erklärung:
     * (?!api|ws) -> "Negative Lookahead": Beginne NICHT mit api oder ws
     * [^\\.]* -> Erlaube keine Punkte (Dateiendungen wie .js/.css ignorieren)
     */
    @GetMapping(value = {
            "/{path:(?!api|ws)[^\\.]*}",
            "/{path1:(?!api|ws)[^\\.]*}/{path2:[^\\.]*}",
            "/{path1:(?!api|ws)[^\\.]*}/{path2:[^\\.]*}/{path3:[^\\.]*}"
    })
    public String forward() {
        // Wir leiten NUR zur index.html weiter, wenn die obigen Bedingungen erfüllt sind.
        return "forward:/index.html";
    }
}