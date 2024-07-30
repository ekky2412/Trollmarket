package id.indocyber.trollmarket.dtos.shop;

import lombok.Builder;
import lombok.Data;

import java.text.NumberFormat;
import java.util.Locale;

@Data
@Builder
public class ShopFieldIndexDto {
    private Integer id;
    private String productName;
    private Double price;

    public String priceFormatted(){
        Locale locale = new Locale("id", "ID");
        return NumberFormat.getCurrencyInstance(locale).format(price);
    }
}
