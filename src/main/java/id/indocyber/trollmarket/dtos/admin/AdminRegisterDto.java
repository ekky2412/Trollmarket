package id.indocyber.trollmarket.dtos.admin;

import id.indocyber.trollmarket.dtos.PasswordComparable;
import id.indocyber.trollmarket.validators.annotations.ComparePassword;
import id.indocyber.trollmarket.validators.annotations.Username;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ComparePassword(message = "Password must match!")
public class AdminRegisterDto implements PasswordComparable {
    @NotBlank
    @NotNull
    @Username
    private String username;
    @NotNull
    @NotBlank
    private String password;
    @NotNull
    @NotBlank
    private String confirmPassword;
    private String message;
}
