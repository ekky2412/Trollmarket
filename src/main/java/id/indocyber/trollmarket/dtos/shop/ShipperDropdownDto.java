package id.indocyber.trollmarket.dtos.shop;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShipperDropdownDto {
    private Integer id;
    private String name;
}
