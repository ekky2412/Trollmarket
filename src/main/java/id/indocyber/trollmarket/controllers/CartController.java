package id.indocyber.trollmarket.controllers;

import id.indocyber.trollmarket.services.CartService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cart")
public class CartController {
    public CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("")
    public ModelAndView index(Authentication authentication) {
        System.out.println(cartService.get(authentication.getName()));
        return new ModelAndView("cart/index")
                .addObject("carts", cartService.get(authentication.getName()));
    }

    @PostMapping("/delete/{product}/{shipment}/{buyer}")
    public String delete(@PathVariable Integer product, @PathVariable Integer shipment, @PathVariable Integer buyer){
        cartService.delete(product,shipment,buyer);
        return "redirect:/cart";
    }

    // TODO
    // Add Purchase All
    @PostMapping("/purchaseAll")
    public String purchaseAll(Authentication authentication, Model model) {
        Boolean isCartEmpty = cartService.isCartEmpty(authentication.getName());
        if (isCartEmpty) {
            model.addAttribute("error", "Keranjang Masih Kosong!");
            model.addAttribute("carts", cartService.get(authentication.getName()));
            return "cart/index";
        }

        Boolean isEnoughBalance = cartService.isEnoughBalance(authentication.getName());
        if (!isEnoughBalance) {
            model.addAttribute("error", "Uang tidak mencukupi");
            model.addAttribute("carts", cartService.get(authentication.getName()));
            return "cart/index";
        }

        cartService.purchaseAll(authentication.getName());
        return "redirect:/profile";

    }


}
