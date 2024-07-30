package id.indocyber.trollmarket.dtos.shop;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShopGetFilterDto {
    private Integer pageNumber;
    private Integer pageSize;
    private String name;
    private String category;
    private String description;
}
