package id.indocyber.trollmarket.services;

import id.indocyber.trollmarket.dtos.auth.AuthRegisterDto;
import id.indocyber.trollmarket.models.Account;
import id.indocyber.trollmarket.models.Buyer;
import id.indocyber.trollmarket.models.MyAccountDetail;
import id.indocyber.trollmarket.models.Seller;
import id.indocyber.trollmarket.repositories.AccountRepository;
import id.indocyber.trollmarket.repositories.AdminRepository;
import id.indocyber.trollmarket.repositories.BuyerRepository;
import id.indocyber.trollmarket.repositories.SellerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final SellerRepository sellerRepository;
    private final BuyerRepository buyerRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AccountRepository accountRepository, SellerRepository sellerRepository, BuyerRepository buyerRepository, AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.sellerRepository = sellerRepository;
        this.buyerRepository = buyerRepository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var account = accountRepository.findByAccountUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Username not found!")
        );

        return MyAccountDetail.builder()
                .account(account)
                .adminRepository(adminRepository)
                .buyerRepository(buyerRepository)
                .sellerRepository(sellerRepository)
                .build();
    }

    public void register(AuthRegisterDto dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("Password did not match!");
        }

        var hashedPassword = passwordEncoder.encode(dto.getPassword());

        var account = accountRepository.save(Account.builder()
                .username(dto.getUsername())
                .password(hashedPassword)
                .build());

        System.out.println(account);

        switch (dto.getRole()){
            case "SELLER":
                sellerRepository.save(Seller.builder()
                        .account(account)
                        .name(dto.getName())
                        .address(dto.getAddress())
                        .balance(0.0)
                        .build());
                break;
            case "BUYER":
                buyerRepository.save(Buyer.builder()
                        .account(account)
                        .name(dto.getName())
                        .address(dto.getAddress())
                        .balance(0.0)
                        .build());
        }
    }

    public boolean isUsernameExist(String username){
        return accountRepository.findByAccountUsername(username).isPresent();
    }
}
