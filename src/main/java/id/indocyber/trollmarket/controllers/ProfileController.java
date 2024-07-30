package id.indocyber.trollmarket.controllers;

import id.indocyber.trollmarket.dtos.admin.AdminRegisterDto;
import id.indocyber.trollmarket.dtos.profile.AddBalanceDto;
import id.indocyber.trollmarket.services.ProfileService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("")
    public ModelAndView index(Authentication authentication) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("BUYER"))) {
            return new ModelAndView("profile/index")
                    .addObject("profile", profileService.getBuyer(authentication.getName()))
                    .addObject("username", authentication.getName())
                    .addObject("dto", AddBalanceDto.builder().build())
                    .addObject("orders", profileService.getOrderByBuyer(authentication.getName()));
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("SELLER"))){
            return new ModelAndView("profile/index")
                    .addObject("profile", profileService.getSeller(authentication.getName()))
                    .addObject("username", authentication.getName())
                    .addObject("dto", AddBalanceDto.builder().build())
                    .addObject("orders", profileService.getOrderBySeller(authentication.getName()));
        } else {
            return new ModelAndView("home/home-index");
        }
    }

    @PostMapping("/addBalance")
    public String register(@Valid @ModelAttribute("dto") AddBalanceDto dto,
                           BindingResult bindingResult,
                           Model model,
                           Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "profile/index";
        }
        profileService.addBalance(dto);
        dto.setMessage("Uang Berhasil Ditambahkan!");
        model.addAttribute("profile", profileService.getBuyer(authentication.getName()));
        model.addAttribute("username", authentication.getName());
        model.addAttribute("orders", profileService.getOrderByBuyer(authentication.getName()));
        return "profile/index";
    }
}
