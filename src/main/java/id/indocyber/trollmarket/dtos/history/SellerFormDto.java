package id.indocyber.trollmarket.dtos.history;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SellerFormDto {
    private Integer sellerId;
    private String sellerName;
}
