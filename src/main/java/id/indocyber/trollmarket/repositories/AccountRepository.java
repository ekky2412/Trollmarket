package id.indocyber.trollmarket.repositories;

import id.indocyber.trollmarket.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query("""
            SELECT a FROM Account a
            WHERE a.username = :username
            """)
    public Optional<Account> findByAccountUsername(@Param("username") String username);
}
