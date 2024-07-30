package id.indocyber.trollmarket.controllers;

import id.indocyber.trollmarket.dtos.shop.AddToCartDto;
import id.indocyber.trollmarket.dtos.shop.ShopDetailDto;
import id.indocyber.trollmarket.dtos.shop.ShopGetFilterDto;
import id.indocyber.trollmarket.services.ShopService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/shop")
public class ShopController {
    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("")
    public ModelAndView index(ShopGetFilterDto dto, Authentication authentication){
        return new ModelAndView("shop/index")
                .addObject("searchDto", dto)
                .addObject("dto", AddToCartDto.builder().build())
                .addObject("shippers", shopService.getShippers())
                .addObject("product", ShopDetailDto.builder().build())
                .addObject("products", shopService.get(dto, authentication.getName()));
    }
    // TODO
    // Tambahkan Add to cart

    @GetMapping("/detail/{id}")
    public ModelAndView index(@PathVariable Integer id, ShopGetFilterDto dto, Authentication authentication){
        return new ModelAndView("shop/index")
                .addObject("searchDto", dto)
                .addObject("dto", AddToCartDto.builder().build())
                .addObject("shippers", shopService.getShippers())
                .addObject("products", shopService.get(dto, authentication.getName()))
                .addObject("product", shopService.getDetail(id));
    }

    @PostMapping("/addToCart")
    public String addToCart(@Valid @ModelAttribute("dto") AddToCartDto dto,
                            BindingResult bindingResult,
                            Model model,
                            Authentication authentication){
        System.out.println(dto);
        if(bindingResult.hasErrors()){
            model.addAttribute("error", "Ada Masalah Ketika Memasukkan Data!");
            model.addAttribute("shippers", shopService.getShippers());
            model.addAttribute("product", ShopDetailDto.builder().build());
            model.addAttribute("products", shopService.get(ShopGetFilterDto.builder().build() ,authentication.getName()));
            return "shop/index";
        }

        shopService.addToCart(dto, authentication.getName());

        return "redirect:/cart";
    }
}
