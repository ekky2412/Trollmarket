package id.indocyber.trollmarket.dtos.auth;

import id.indocyber.trollmarket.dtos.PasswordComparable;
import id.indocyber.trollmarket.validators.annotations.ComparePassword;
import id.indocyber.trollmarket.validators.annotations.Username;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
@ComparePassword(message = "Password must match!")
public class AuthRegisterDto implements PasswordComparable {
    @NotNull
    @NotBlank
    @Username
    private final String username;
    @NotNull
    @NotBlank
    private final String password;
    @NotNull
    @NotBlank
    private final String confirmPassword;
    private final String role;
    @NotNull
    @NotBlank
    private final String name;
    @NotNull
    @NotBlank
    private final String address;
}
