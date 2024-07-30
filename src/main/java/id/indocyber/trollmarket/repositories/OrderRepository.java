package id.indocyber.trollmarket.repositories;

import id.indocyber.trollmarket.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("""
            SELECT o FROM Order o
            WHERE o.buyer.account.username = :username
            """)
    public List<Order> getOrderByBuyerUsername(@Param("username") String username);

    @Query("""
            SELECT o FROM Order o
            WHERE o.product.seller.account.username = :username
            """)
    public List<Order> getOrderBySellerUsername(@Param("username") String username);

    @Query("""
            SELECT o FROM Order o
            WHERE (:seller IS NULL
                OR o.product.seller.account.id = :seller)
            AND (:buyer IS NULL
                OR o.buyer.account.id = :buyer)
            """)
    public Page<Order> findAll(
            Pageable pageable,
            @Param("seller") Integer checkedSeller,
            @Param("buyer") Integer checkedBuyer);

    @Query("""
            SELECT COUNT(*) FROM Order o
            WHERE o.product.id = :id
            """)
    public Integer getOrderCountByProduct(@Param("id") Integer id);

    @Query("""
            SELECT COUNT(*) FROM Order o
            WHERE o.shipper.id = :id
            """)
    public Integer getOrderCountByShipper(@Param("id") Integer id);
}
