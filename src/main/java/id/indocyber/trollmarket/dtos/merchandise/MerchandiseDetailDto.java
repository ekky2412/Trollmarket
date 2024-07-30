package id.indocyber.trollmarket.dtos.merchandise;

import lombok.Builder;
import lombok.Data;

import java.text.NumberFormat;
import java.util.Locale;

@Data
@Builder
public class MerchandiseDetailDto {
    private final Integer id;
    private final String name;
    private final String category;
    private final String description;
    private final Double price;
    private final Boolean discontinue;

    public String getDiscontinueString(){
        if(discontinue == null) return null;

        if(discontinue) return "Yes";
        else return "No";
    }

    public String getPriceFormatted(){
        if(price == null) return null;

        Locale locale = new Locale("id", "ID");
        return NumberFormat.getCurrencyInstance(locale).format(price);
    }
}
