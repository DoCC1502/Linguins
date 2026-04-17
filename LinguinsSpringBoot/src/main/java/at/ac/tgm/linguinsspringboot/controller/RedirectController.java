package at.ac.tgm.linguinsspringboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RedirectController {

    @GetMapping({
            "/{path:[^\\.]*}",
            "/*/{path:[^\\.]*}",
            "/*/*/{path:[^\\.]*}"
    })
    public String forward() {
        return "forward:/index.html";
    }

}