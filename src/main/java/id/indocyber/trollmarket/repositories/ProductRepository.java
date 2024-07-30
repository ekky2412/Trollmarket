package id.indocyber.trollmarket.repositories;

import id.indocyber.trollmarket.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("""
            SELECT p from Product p
            WHERE (:name IS NULL
                OR p.productName LIKE %:name%)
            AND (:category IS NULL
                OR p.category LIKE %:category%)
            AND (:description IS NULL
                OR p.description LIKE %:description%)
            AND p.seller.account.username != :username
            AND p.isDiscontinue = false
            """)
    public Page<Product> findAllProductExceptUsernameOf(
            Pageable pageable,
            @Param("username") String username,
            @Param("name") String name,
            @Param("category") String category,
            @Param("description") String description
    );
    @Query("""
            SELECT p from Product p
            WHERE p.seller.account.username = :username
            """)
    public List<Product> findAllProductOfUsername(
            @Param("username") String username);
}
