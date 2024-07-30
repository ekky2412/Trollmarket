package id.indocyber.trollmarket.dtos.history;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BuyerFormDto {
    private Integer buyerId;
    private String buyerName;
}