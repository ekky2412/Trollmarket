package id.indocyber.trollmarket.models;

import id.indocyber.trollmarket.repositories.AdminRepository;
import id.indocyber.trollmarket.repositories.BuyerRepository;
import id.indocyber.trollmarket.repositories.SellerRepository;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
public class MyAccountDetail implements UserDetails {
    private Account account;
    private SellerRepository sellerRepository;
    private BuyerRepository buyerRepository;
    private AdminRepository adminRepository;

    public MyAccountDetail(Account account, SellerRepository sellerRepository, BuyerRepository buyerRepository, AdminRepository adminRepository) {
        this.account = account;
        this.sellerRepository = sellerRepository;
        this.buyerRepository = buyerRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();

        if(adminRepository.findByAccountUsername(account.getUsername()).isPresent()){
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
            System.out.println("ADMIN");
        }

        if(sellerRepository.findByAccountUsername(account.getUsername()).isPresent()){
            authorities.add(new SimpleGrantedAuthority("SELLER"));
            System.out.println("SELLER");
        }

        if(buyerRepository.findByAccountUsername(account.getUsername()).isPresent()){
            authorities.add(new SimpleGrantedAuthority("BUYER"));
            System.out.println("BUYER");
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.account.getPassword();
    }

    @Override
    public String getUsername() {
        return this.account.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
