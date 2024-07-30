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
@Table(name = "Admins")
public class Admin {
    @Id
    private int accountId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "accountId")
    private Account account;

}
