package id.indocyber.trollmarket.repositories;

import id.indocyber.trollmarket.models.Cart;
import id.indocyber.trollmarket.models.CartId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, CartId> {
    @Query("""
            SELECT c from Cart c
            WHERE c.buyer.account.username = :username
            """)
    public List<Cart> findAllCartOfUsername(
            @Param("username") String username);

    @Query("""
            SELECT COUNT(*) from Cart c
            WHERE c.buyer.account.username = :username
            """)
    public Integer getCountOfCartProductsOfUsername(@Param("username") String username);

    @Query("""
            SELECT c from Cart c
            WHERE c.buyer.account.username = :username
            AND c.product.id = :productId
            AND c.shipper.id = :shipperId
            """)
    public Optional<Cart> findCartByBuyerProductAndShipper(
            @Param("username") String username,
            @Param("productId") Integer productId,
            @Param("shipperId") Integer shipperId
    );
}
