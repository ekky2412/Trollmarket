package id.indocyber.trollmarket.controllers;

import id.indocyber.trollmarket.dtos.merchandise.MerchandiseDetailDto;
import id.indocyber.trollmarket.dtos.merchandise.MerchandiseFormDto;
import id.indocyber.trollmarket.services.MerchandiseService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/merchandise")
public class MerchandiseController {
    private MerchandiseService merchandiseService;

    public MerchandiseController(MerchandiseService merchandiseService) {
        this.merchandiseService = merchandiseService;
    }

    @GetMapping("")
    public ModelAndView index(Authentication authentication) {
        return new ModelAndView("merchandise/index")
                .addObject("product", MerchandiseDetailDto.builder().build())
                .addObject("products", merchandiseService.get(authentication.getName()));
    }

    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable Integer id, Authentication authentication) {
        return new ModelAndView("merchandise/index")
                .addObject("product", merchandiseService.getDetail(id))
                .addObject("products", merchandiseService.get(authentication.getName()));
    }
    // TODO
    // Add New Product, Edit, Info, Discontinue
    @GetMapping("/add")
    public ModelAndView add(Authentication authentication) {
        return new ModelAndView("merchandise/form")
                .addObject("product", MerchandiseFormDto.builder().build());
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Integer id, Authentication authentication) {
        return new ModelAndView("merchandise/form")
                .addObject("product", merchandiseService.get(id));
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Integer id, Authentication authentication) {
        Integer orderCount = merchandiseService.getOrderCountByProduct(id);
        if (orderCount > 0) {
            return new ModelAndView("merchandise/delete-rejected")
                    .addObject("orderCount", orderCount);
        }
        return new ModelAndView("merchandise/delete-confirmation")
                .addObject("product", merchandiseService.get(id));
    }

    @PostMapping("/delete/confirm")
    public String deleteConfirm(MerchandiseFormDto dto) {
        merchandiseService.delete(dto);
        return "redirect:/merchandise";
    }

    @PostMapping("/upsert")
    public String upsert(@Valid @ModelAttribute("product") MerchandiseFormDto dto,
                         BindingResult bindingResult,
                         Model model,
                         Authentication authentication){
        if(bindingResult.hasErrors()){
            return "merchandise/form";
        }
        merchandiseService.upsert(dto, authentication.getName());
        return "redirect:/merchandise";
    }

    @GetMapping("/discontinue/{id}")
    public ModelAndView discontinueView(@PathVariable Integer id, Authentication authentication) {
        return new ModelAndView("merchandise/discontinue-confirmation")
                .addObject("product", merchandiseService.get(id));
    }

    @PostMapping("/discontinue/{id}")
    public String discontinueProduct(@PathVariable Integer id){
        merchandiseService.discontinueProduct(id);
        return "redirect:/merchandise";
    }
}
