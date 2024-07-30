package id.indocyber.trollmarket.dtos.shop;

import lombok.Builder;
import lombok.Data;

import java.text.NumberFormat;
import java.util.Locale;

@Data
@Builder
public class ShopDetailDto {
    private final Integer id;
    private final String name;
    private final String category;
    private final String description;
    private final Double price;
    private final String seller;

    public String getPriceFormatted(){
        if(price == null) return null;

        Locale locale = new Locale("id", "ID");
        return NumberFormat.getCurrencyInstance(locale).format(price);
    }
}
