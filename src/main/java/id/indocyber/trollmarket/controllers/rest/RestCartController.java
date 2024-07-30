package id.indocyber.trollmarket.controllers.rest;

import id.indocyber.trollmarket.dtos.cart.CartPurchaseDto;
import id.indocyber.trollmarket.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class RestCartController {

    private CartService cartService;

    public RestCartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("purchaseAll")
    public ResponseEntity<Boolean> purchaseAll(@RequestBody CartPurchaseDto dto){
        Boolean isCartEmpty = cartService.isCartEmpty(dto.getUsername());
        if (isCartEmpty) {
            return ResponseEntity.badRequest().body(false);
        }

        Boolean isEnoughBalance = cartService.isEnoughBalance(dto.getUsername());
        if (!isEnoughBalance) {
            return ResponseEntity.badRequest().body(false);
        }

        cartService.purchaseAll(dto);
        return ResponseEntity.ok(true);

    }
}
