package id.indocyber.trollmarket.dtos.shipment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShipmentFormDto {
    private final Integer id;
    @NotBlank
    @NotNull
    private final String name;
    @NotNull
    private final Double price;
    private final Boolean isService;
}
