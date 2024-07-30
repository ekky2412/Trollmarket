package id.indocyber.trollmarket.dtos.shipment;

import lombok.Builder;
import lombok.Data;

import java.text.NumberFormat;
import java.util.Locale;

@Data
@Builder
public class ShipmentFieldIndexDto {
    private Integer id;
    private String shipperName;
    private Double price;
    private Boolean isService;
    private Integer totalOrders;

    public String getIsService(){
        if(isService) return "Yes";
        else return "No";
    }

    public String getPriceFormatted(){
        Locale locale = new Locale("id", "ID");
        return NumberFormat.getCurrencyInstance(locale).format(price);
    }
}
