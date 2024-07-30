package id.indocyber.trollmarket.repositories;

import id.indocyber.trollmarket.models.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Integer> {
    @Query("""
            SELECT s FROM Seller s
            WHERE s.account.username = :username
            """)
    public Optional<Seller> findByAccountUsername(@Param("username") String username);
}
