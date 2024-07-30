package id.indocyber.trollmarket.dtos.profile;

import lombok.Builder;
import lombok.Data;

import java.text.NumberFormat;
import java.util.Locale;

@Data
@Builder
public class BuyerFieldIndexDto {
    private String name;
    private String role;
    private String address;
    private Double balance;

    public String getBalanceFormatted(){
        return NumberFormat.getCurrencyInstance(
                new Locale("id", "ID"))
                .format(balance);
    }
}
