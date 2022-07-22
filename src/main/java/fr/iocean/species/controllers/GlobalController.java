package fr.iocean.species.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Dans ce controlleur seront mis les autres appels qui ne sont pas liés à
 * une entité en particulier
 */
@Controller
public class GlobalController {
    
    @GetMapping("/error")
    public String getErrorPage() {
        return "error";
    }
}