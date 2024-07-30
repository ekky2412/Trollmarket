package id.indocyber.trollmarket.dtos.cart;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartPurchaseDto {
    private String username;
    private List<CartPurchaseDetailDto> dto;
}
