package id.indocyber.trollmarket.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "Shippers")
public class Shipper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String shipperName;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Boolean isService;

    @OneToMany(mappedBy = "shipper")
    private List<Order> orders;
}
