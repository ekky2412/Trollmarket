package id.indocyber.trollmarket.dtos.profile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddBalanceDto {
    private String username;
    private Double balance;
    private String message;
}
