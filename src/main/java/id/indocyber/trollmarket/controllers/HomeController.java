package id.indocyber.trollmarket.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/home")
public class HomeController {
    @GetMapping("")
    public ModelAndView index(Authentication authentication) {
        System.out.println(authentication.getAuthorities());
        System.out.println(authentication.getName());
        return new ModelAndView("home/home-index");
    }
}
