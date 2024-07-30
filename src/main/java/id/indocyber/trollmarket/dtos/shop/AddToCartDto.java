package id.indocyber.trollmarket.dtos.shop;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class AddToCartDto {
    @NotNull
    private Integer productId;
    private Integer buyerId;
    @NotNull
    @Min(1)
    private Integer quantity;
    @NotNull
    private Integer shipperId;
}
