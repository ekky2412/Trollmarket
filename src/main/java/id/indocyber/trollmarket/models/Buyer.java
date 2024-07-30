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
@Table(name = "Buyers")
public class Buyer {
    @Id
    private int accountId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "accountId")
    private Account account;

    @Column(nullable = false)
    private String name;

    @Column(name = "Address",nullable = false)
    private String address;
    @Column(name = "Balance",nullable = false)
    private Double balance;

    @OneToMany(mappedBy = "buyer")
    private List<Cart> carts;

    @OneToMany(mappedBy = "buyer")
    private List<Order> orders;
}
