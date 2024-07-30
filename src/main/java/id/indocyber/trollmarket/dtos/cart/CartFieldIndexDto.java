package id.indocyber.trollmarket.dtos.cart;

import id.indocyber.trollmarket.models.CartId;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.text.NumberFormat;
import java.util.Locale;

@Data
@Builder
@ToString
public class CartFieldIndexDto {
    private CartId id;
    private String product;
    private Integer quantity;
    private String shipment;
    private String seller;
    private Double totalPrice;

    public String getTotalPriceFormatted(){
        Locale locale = new Locale("id", "ID");
        return NumberFormat.getCurrencyInstance(locale).format(totalPrice);
    }

}
