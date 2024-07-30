package id.indocyber.trollmarket.repositories;

import id.indocyber.trollmarket.models.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShipperRepository extends JpaRepository<Shipper, Integer> {

    @Query("""
            SELECT s FROM Shipper s
            WHERE s.isService = true
            """)
    public List<Shipper> getAllActiveShippers();
}
