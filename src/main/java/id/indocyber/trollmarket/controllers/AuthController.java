package id.indocyber.trollmarket.controllers;

import id.indocyber.trollmarket.dtos.auth.AuthRegisterDto;
import id.indocyber.trollmarket.services.AuthService;
import id.indocyber.trollmarket.utils.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false) Boolean error){
        if(error == null) error = false;
        return new ModelAndView("/account/auth-login-basic")
                .addObject("error", error);
    }

    @GetMapping("/register/seller")
    public ModelAndView registerAsSeller(){
        return new ModelAndView("account/auth-register-basic")
                .addObject("dto", AuthRegisterDto.builder()
                        .role("SELLER")
                        .build());
    }

    @GetMapping("/register/buyer")
    public ModelAndView registerAsBuyer(){
        return new ModelAndView("account/auth-register-basic")
                .addObject("dto", AuthRegisterDto.builder()
                        .role("BUYER")
                        .build());
    }

    @PostMapping("/register")
    public String registerAcc(@Valid @ModelAttribute("dto") AuthRegisterDto dto,
                              BindingResult bindingResult,
                              Model model){
        System.out.println(dto.toString());
        if(bindingResult.hasErrors()){
            return "account/auth-register-basic";
        }
        authService.register(dto);
        return "redirect:/login";
    }

    @GetMapping("/index")
    public String redirectAfterLogin(Authentication auth){
        var firstRole = auth.getAuthorities().iterator().next().toString();
        return "redirect:/home";
    }

    @GetMapping("")
    public String redirectToChooseRole(){
        return "redirect:/role";
    }

    @GetMapping("/role")
    public ModelAndView changeRole(Authentication auth){
        var role = auth.getAuthorities();
        return new ModelAndView("change-role/index")
                .addObject("roles", role);
    }

    @PostMapping("/role")
    public String changeRoleConfirm(String role, Authentication authentication){
        SecurityUtil.updateAuthorities(authentication.getName(), List.of(role));
        return "redirect:/home";
    }
}
