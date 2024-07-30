package id.indocyber.trollmarket.models;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartId implements Serializable {
    private Integer buyerId;
    private Integer productId;
    private Integer shipperId;
}
