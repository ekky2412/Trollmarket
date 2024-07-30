package id.indocyber.trollmarket.models;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "Cart")
public class Cart {
    @EmbeddedId
    private CartId id;

    @ManyToOne
    @MapsId("buyerId")
    @JoinColumn(name = "buyerId", nullable = false)
    private Buyer buyer;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    @ManyToOne
    @MapsId("shipperId")
    @JoinColumn(name = "shipperId", nullable = false)
    private Shipper shipper;

    @Column(nullable = false)
    private Integer quantity;

}
