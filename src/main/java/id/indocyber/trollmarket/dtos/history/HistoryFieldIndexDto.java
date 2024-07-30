package id.indocyber.trollmarket.dtos.history;

import lombok.Builder;
import lombok.Data;

import java.text.NumberFormat;
import java.time.LocalDate;

@Data
@Builder
public class HistoryFieldIndexDto {
    private LocalDate date;
    private String seller;
    private String buyer;
    private String product;
    private Integer quantity;
    private String shipment;
    private Double totalPrice;
    public String totalPriceFormatted(){
        return NumberFormat.getNumberInstance().format(totalPrice);
    }
}
