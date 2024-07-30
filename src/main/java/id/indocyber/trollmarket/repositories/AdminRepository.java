package id.indocyber.trollmarket.repositories;

import id.indocyber.trollmarket.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    @Query("""
            SELECT a FROM Admin a
            WHERE a.account.username = :username
            """)
    public Optional<Admin> findByAccountUsername(@Param("username") String username);
}
