package id.indocyber.trollmarket.repositories;

import id.indocyber.trollmarket.models.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BuyerRepository extends JpaRepository<Buyer, Integer> {
    @Query("""
            SELECT b FROM Buyer b
            WHERE b.account.username = :username
            """)
    public Optional<Buyer> findByAccountUsername(@Param("username") String username);
}
