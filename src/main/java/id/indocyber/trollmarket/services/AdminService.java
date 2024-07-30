package id.indocyber.trollmarket.services;

import id.indocyber.trollmarket.dtos.admin.AdminRegisterDto;
import id.indocyber.trollmarket.models.Account;
import id.indocyber.trollmarket.models.Admin;
import id.indocyber.trollmarket.repositories.AccountRepository;
import id.indocyber.trollmarket.repositories.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private AdminRepository adminRepository;
    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository, AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(AdminRegisterDto dto){
        if(!dto.getPassword().equals(dto.getConfirmPassword())){
            throw new IllegalArgumentException("Password tidak cocok");
        }

        var hashedPassword = passwordEncoder.encode(dto.getPassword());

        var account = accountRepository.save(Account.builder()
                .username(dto.getUsername())
                .password(hashedPassword)
                .build());

        adminRepository.save(Admin.builder()
                        .account(account)
                        .build()
        );
    }
}
