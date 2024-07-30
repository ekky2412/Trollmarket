package id.indocyber.trollmarket.dtos.merchandise;

import lombok.Builder;
import lombok.Data;

import java.text.NumberFormat;
@Data
@Builder
public class MerchandiseFieldIndexDto {
    private Integer id;
    private String productName;
    private String categoryName;
    private Boolean discontinue;
    private Integer totalOrders;

    public String getDiscontinueString(){
        if(discontinue) return "Yes";
        else return "No";
    }
}
