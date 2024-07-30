package id.indocyber.trollmarket.dtos.merchandise;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MerchandiseFormDto {
    private final Integer id;
    @NotNull
    @NotBlank
    private final String name;
    @NotNull
    @NotBlank
    private final String category;
    private final String description;
    @NotNull
    private final Double price;
    private final Boolean isDiscontinue;
}
