package id.indocyber.trollmarket.controllers;

import id.indocyber.trollmarket.dtos.admin.AdminRegisterDto;
import id.indocyber.trollmarket.services.AdminService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @GetMapping("")
    public ModelAndView index(Authentication authentication) {
        System.out.println(authentication.getAuthorities());
        System.out.println(authentication.getName());
        return new ModelAndView("admin/index")
                .addObject("dto", AdminRegisterDto.builder().build());
    }

    @PostMapping("")
    public String register(@Valid @ModelAttribute("dto") AdminRegisterDto dto,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/index";
        }
        adminService.register(dto);
        dto.setMessage("Admin Berhasil Dibuat!");
        return "admin/index";
    }
}
