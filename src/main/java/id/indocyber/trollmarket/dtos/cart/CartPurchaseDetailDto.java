package id.indocyber.trollmarket.dtos.cart;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CartPurchaseDetailDto {
    private Integer productId;
    private Integer shipperId;
}
